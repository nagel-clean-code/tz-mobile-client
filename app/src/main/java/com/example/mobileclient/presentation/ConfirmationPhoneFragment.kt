package com.example.mobileclient.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mobileclient.R
import com.example.mobileclient.Constants.Companion.NUMBER_PHONE
import com.example.mobileclient.databinding.FragmentConfirmationPhoneBinding
import com.example.mobileclient.presentation.viewmodels.ConfirmationPhoneViewModel
import com.example.mobileclient.presentation.viewmodels.ModelFactory

class ConfirmationPhoneFragment : Fragment() {
    private lateinit var binding: FragmentConfirmationPhoneBinding
    private val viewModel = activity?.let {
        ViewModelProvider(
            it,
            ModelFactory()
        ).get(ConfirmationPhoneViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentConfirmationPhoneBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.text2.text = getString(
            R.string.confirmation_phone_text_1,
            arguments?.getString(NUMBER_PHONE)
        )
        return binding.root
    }

    companion object {
        fun getNewInstance(numberPhone: String) = ConfirmationPhoneFragment().apply {
            arguments = Bundle().apply {
                putString(NUMBER_PHONE, numberPhone)
            }
        }
    }

}