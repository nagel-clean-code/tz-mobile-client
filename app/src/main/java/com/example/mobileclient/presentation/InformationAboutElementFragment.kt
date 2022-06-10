package com.example.mobileclient.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mobileclient.Constants
import com.example.mobileclient.databinding.FragmentInformationAboutElementBinding
import com.example.mobileclient.presentation.adapters.ImageViewInformationFormPagerAdapter
import com.example.mobileclient.presentation.viewmodels.InformationAboutElementViewModel
import com.example.mobileclient.presentation.viewmodels.ModelFactory


class InformationAboutElementFragment : Fragment() {

    private lateinit var binding: FragmentInformationAboutElementBinding
    private lateinit var numberPhone: String
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
        numberPhone = arguments?.getString(Constants.NUMBER_PHONE)!!
        setupViewPager()
        return binding.root
    }

    private fun setupViewPager(){
//        binding.pagerPlaceholderImageView1.adapter = ImageViewInformationFormPagerAdapter() //FIXME
    }

    companion object {
        fun getNewInstance(numberPhone: String) = InformationAboutElementFragment().apply {
            arguments = Bundle().apply {
                putString(Constants.NUMBER_PHONE, numberPhone)  //FIXME
            }
        }
    }

}