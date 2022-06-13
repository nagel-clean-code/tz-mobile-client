package com.example.mobileclient.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.forEach
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobileclient.R
import com.example.mobileclient.data.storage.models.CampaignsItem
import com.example.mobileclient.data.storage.models.ProductsItem
import com.example.mobileclient.databinding.ItemProductBinding
import com.example.mobileclient.databinding.ItemShopBinding

class SearchResultAdapter(
    private val campaigns: List<CampaignsItem?>?,
    private val products: List<ProductsItem?>?,
    private val listener: ListenerItem
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {

    private var currentPositionCompany: Int = 0
    private var currentPositionProduct: Int = 0

    override fun onClick(p0: View?) {
        if(p0?.tag is CampaignsItem){
            listener.onDisplayCampaign(p0.tag as CampaignsItem)
        }else{
            listener.onDisplayProduct(p0?.tag as ProductsItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return if (campaigns != null && currentPositionCompany < campaigns.size - 1) {
            val binding = ItemShopBinding.inflate(inflate, parent, false)
            binding.root.forEach { it.setOnClickListener(this@SearchResultAdapter) }
            ItemShopViewHolder(binding)
        } else {
            val binding = ItemProductBinding.inflate(inflate, parent, false)
            binding.root.forEach { it.setOnClickListener(this@SearchResultAdapter) }
            ItemProductViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (campaigns != null && currentPositionCompany < campaigns.size - 1) {
            holder as ItemShopViewHolder
            with(holder.binding) {
                //Первая компания
                pagerPlaceholderImageView1.tag = campaigns[currentPositionCompany]
                loadImage(
                    campaigns[currentPositionCompany]?.imageUrl,
                    pagerPlaceholderImageView1
                )

                nameCompanyTextView1.tag = campaigns[currentPositionCompany]
                nameCompanyTextView1.text = campaigns[currentPositionCompany]?.name

                cashbackTextView1.tag = campaigns[currentPositionCompany]
                cashbackTextView1.text = campaigns[currentPositionCompany]?.cashback
                currentPositionCompany++

                //Вторая компания
                pagerPlaceholderImageView2.tag = campaigns[currentPositionCompany]
                loadImage(
                    campaigns[currentPositionCompany]?.imageUrl,
                    pagerPlaceholderImageView2
                )

                nameCompanyTextView2.tag = campaigns[currentPositionCompany]
                nameCompanyTextView2.text = campaigns[currentPositionCompany]?.name

                cashbackTextView2.tag = campaigns[currentPositionCompany]
                cashbackTextView2.text = campaigns[currentPositionCompany]?.cashback
                currentPositionCompany++
            }
        } else if (products != null && currentPositionProduct < products.size - 1) {
            holder as ItemProductViewHolder
            with(holder.binding) {
                pagerPlaceholderImageView1.tag = products[currentPositionProduct]
                loadImage(
                    products[currentPositionProduct]?.imageUrls?.get(0),
                    pagerPlaceholderImageView1
                )

                imageCompanyView.tag = products[currentPositionProduct]
                loadImage(products[currentPositionProduct]?.campaignImageUrl, imageCompanyView)

                nameCompanyTextView1.tag = products[currentPositionProduct]
                nameCompanyTextView1.text = products[currentPositionProduct]?.name

                priceTextView1.tag = products[currentPositionProduct]
                priceTextView1.text = products[currentPositionProduct]?.price

                cashbackTextView1.tag = products[currentPositionProduct]
                cashbackTextView1.text = products[currentPositionProduct]?.cashback
            }
            currentPositionProduct++
        }
    }

    private fun loadImage(url: String?, image: ImageView) {
        Glide.with(image.context)
            .load(url)
            .placeholder(R.drawable.ic_baseline_image_24)
            .error(R.drawable.ic_baseline_broken_image_24)
            .into(image)
    }

    override fun getItemCount(): Int {
        var count = 0
        if (campaigns != null) {
            count += campaigns.size / 2
        }
        if (products != null) {
            count += products.size
        }
        return count - 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ItemShopViewHolder(
        val binding: ItemShopBinding,
    ) : RecyclerView.ViewHolder(binding.root)


    class ItemProductViewHolder(
        val binding: ItemProductBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    interface ListenerItem {
        fun onDisplayProduct(product: ProductsItem)
        fun onDisplayCampaign(campaign: CampaignsItem)
    }
}

