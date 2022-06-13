package com.example.mobileclient.presentation

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mobileclient.Constants
import com.example.mobileclient.R
import com.example.mobileclient.data.storage.models.ActionsItem
import com.example.mobileclient.data.storage.models.CampaignsItem
import com.example.mobileclient.data.storage.models.ProductsItem
import com.example.mobileclient.databinding.FragmentInformationAboutElementBinding
import com.example.mobileclient.presentation.adapters.ImageViewInformationFormPagerAdapter
import com.example.mobileclient.presentation.contract.CustomAction
import com.example.mobileclient.presentation.contract.HasCustomActionToolbar
import com.example.mobileclient.presentation.viewmodels.InformationAboutElementViewModel
import com.example.mobileclient.presentation.viewmodels.ModelFactory
import com.google.android.material.tabs.TabLayoutMediator


class InformationAboutElementFragment : Fragment(), HasCustomActionToolbar {

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
        setupListenerExpandButton()
        return binding.root
    }

    private fun setupListenerExpandButton() {
        binding.expandButton.setOnClickListener {
            openListConditions()
        }
    }

    private fun openListConditions() {
        binding.expandButton.visibility = View.GONE
        if (campaignsItem != null) {
            displayListActions(campaignsItem!!.actions)
        }
        if (productsItem != null) {
            displayListActions(productsItem!!.actions)
        }
    }

    private fun displayListActions(actions:  List<ActionsItem?>?){
        var first = true
        var prevId2: Int = R.id.conditions_text_view1
        actions?.forEach {
            if (!first) {
                val editText1 = TextView(activity)
                editText1.id = View.generateViewId()
                val lPrams1 = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
                )
                lPrams1.addRule(RelativeLayout.ALIGN_PARENT_START)
                lPrams1.addRule(RelativeLayout.BELOW, prevId2)
                editText1.layoutParams = lPrams1
                editText1.text = it?.value
                editText1.textSize = 20F
                editText1.setTextColor(Color.RED)
                binding.relative.addView(editText1)

                val editText2 = TextView(activity)
                editText2.id = View.generateViewId()
                val lPrams2 = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
                )
                lPrams2.addRule(RelativeLayout.END_OF, editText1.id)
                lPrams2.addRule(RelativeLayout.BELOW, prevId2)
                lPrams2.marginStart = 5

                editText2.setTextColor(Color.BLACK)
                editText2.layoutParams = lPrams2
                editText2.text = it?.text
                editText2.textSize = 20F
                binding.relative.addView(editText2)
                prevId2 = editText2.id
            } else {
                first = false
            }
        }
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

    override fun getCustomAction(): CustomAction = CustomAction(Constants.TYPE_ICON_GO_BACK)
}