package com.example.mobileclient.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mobileclient.Constants.Companion.FRAGMENT_AUTHORIZATION
import com.example.mobileclient.Constants.Companion.NUMBER_PHONE
import com.example.mobileclient.databinding.FragmentAuthorizationBinding
import com.example.mobileclient.presentation.viewmodels.AuthorizationViewModel
import com.example.mobileclient.presentation.viewmodels.ConfirmationPhoneViewModel
import com.example.mobileclient.presentation.viewmodels.ModelFactory


class AuthorizationFragment : Fragment() {

    private lateinit var binding: FragmentAuthorizationBinding
    private val viewModel = activity?.let {
        ViewModelProvider(
            it,
            ModelFactory()
        ).get(AuthorizationViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentAuthorizationBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.button.setOnClickListener {
            parentFragmentManager.popBackStack()
            parentFragmentManager.setFragmentResult(
                FRAGMENT_AUTHORIZATION,
                bundleOf(NUMBER_PHONE to binding.inputNumber.text.toString())
            )
        }
        return binding.root
    }

    companion object {
        fun getNewInstance(): AuthorizationFragment {
            return AuthorizationFragment()
        }
    }


}