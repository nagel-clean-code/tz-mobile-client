package com.example.mobileclient.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mobileclient.Constants
import com.example.mobileclient.R
import com.example.mobileclient.data.storage.models.CampaignsItem
import com.example.mobileclient.data.storage.models.ProductsItem
import com.example.mobileclient.databinding.FragmentInformationAboutElementBinding
import com.example.mobileclient.presentation.adapters.ImageViewInformationFormPagerAdapter
import com.example.mobileclient.presentation.viewmodels.InformationAboutElementViewModel
import com.example.mobileclient.presentation.viewmodels.ModelFactory
import com.google.android.material.tabs.TabLayoutMediator


class InformationAboutElementFragment : Fragment() {

    private lateinit var binding: FragmentInformationAboutElementBinding
    private var productsItem: ProductsItem? = null
    private var campaignsItem: CampaignsItem? = null
    private lateinit var viewModel: InformationAboutElementViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentInformationAboutElementBinding.inflate(layoutInflater)
        viewModel = activity?.let {
            ViewModelProvider(
                it,
                ModelFactory()
            )[InformationAboutElementViewModel::class.java]
        }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        campaignsItem = arguments?.getParcelable(Constants.CAMPAIGNS_ITEM)
        productsItem = arguments?.getParcelable(Constants.PRODUCTS_ITEM)

        if (campaignsItem != null) {
            viewCompany()
        }
        if (productsItem != null) {
            viewProduct()
        }
        return binding.root
    }

    private fun viewCompany() {
        binding.pagerPlaceholderImageView1.adapter =
            ImageViewInformationFormPagerAdapter(listOf(campaignsItem?.imageUrl))
        binding.tabLayout.visibility = View.GONE
        with(binding) {
            descriptionTextView.text = campaignsItem!!.name
            imageCompanyView.visibility = View.GONE
            priceTextView.visibility = View.GONE
            cashbackTextView1.text = campaignsItem!!.cashback
            timeCashbackAccrualTextView1.text = campaignsItem!!.paymentTime
            conditionsTextView1.text = campaignsItem!!.actions?.get(0)?.text ?: ""
        }
    }

    private fun viewProduct() {
        binding.pagerPlaceholderImageView1.adapter =
            productsItem!!.imageUrls?.let { ImageViewInformationFormPagerAdapter(it) }
        TabLayoutMediator(binding.tabLayout, binding.pagerPlaceholderImageView1) { tab, position ->
            //Some implementation
        }.attach()
        with(binding) {
            descriptionTextView.text = productsItem!!.name
            Glide.with(imageCompanyView.context)
                .load(productsItem!!.campaignImageUrl)
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(imageCompanyView)
            priceTextView.text = productsItem!!.price
            cashbackTextView1.text = productsItem!!.cashback
            timeCashbackAccrualTextView1.text = productsItem!!.paymentTime
            conditionsTextView1.text = productsItem!!.actions?.get(0)?.text ?: ""
        }
    }

    companion object {
        fun getNewInstance(campaignsItem: CampaignsItem) = InformationAboutElementFragment().apply {
            arguments = Bundle().apply {
                putParcelable(Constants.CAMPAIGNS_ITEM, campaignsItem)
            }
        }

        fun getNewInstance(productsItem: ProductsItem) = InformationAboutElementFragment().apply {
            arguments = Bundle().apply {
                putParcelable(Constants.PRODUCTS_ITEM, productsItem)
            }
        }
    }

}