package com.example.mobileclient.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobileclient.R
import com.example.mobileclient.databinding.ItemImagePagerViewBinding

class ImageViewInformationFormPagerAdapter(
    private val imageUrls: List<String?>
) : RecyclerView.Adapter<ImageViewInformationFormPagerAdapter.PagerVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH {
        val inflate = LayoutInflater.from(parent.context)
        val binding = ItemImagePagerViewBinding.inflate(inflate, parent, false)
        return PagerVH(binding)
    }

    override fun getItemCount(): Int = imageUrls.size

    override fun onBindViewHolder(holder: PagerVH, position: Int) {
        with(holder.binding) {
            Glide.with(image.context)
                .load(imageUrls[position])
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(image)
        }
    }

    class PagerVH(val binding: ItemImagePagerViewBinding) : RecyclerView.ViewHolder(binding.root)
}
