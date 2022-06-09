package com.example.mobileclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.mobileclient.databinding.FragmentConfirmationPhoneBinding
import com.example.mobileclient.databinding.FragmentInformationAboutElementBinding
import com.example.mobileclient.presentation.ConfirmationPhoneFragment
import com.example.mobileclient.presentation.viewmodels.ConfirmationPhoneViewModel
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

        return binding.root
    }

    companion object {
        fun getNewInstance(numberPhone: String) = InformationAboutElementFragment().apply {
            arguments = Bundle().apply {
                putString(Constants.NUMBER_PHONE, numberPhone)  //FIXME
            }
        }
    }

}