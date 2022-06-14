package com.example.mobileclient.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.mobileclient.Constants
import com.example.mobileclient.Constants.Companion.LOGIN_STEP_2_MODEL
import com.example.mobileclient.Constants.Companion.NUMBER_PHONE
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentConfirmationPhoneBinding
import com.example.mobileclient.presentation.contract.CustomAction
import com.example.mobileclient.presentation.contract.HasCustomActionToolbar
import com.example.mobileclient.presentation.models.state.takeSuccess
import com.example.mobileclient.presentation.viewmodels.ConfirmationPhoneViewModel
import com.example.mobileclient.presentation.viewmodels.ModelFactory

class ConfirmationPhoneFragment : BaseFragment(), HasCustomActionToolbar {
    private lateinit var binding: FragmentConfirmationPhoneBinding
    private lateinit var numberPhone: String
    private lateinit var viewModel: ConfirmationPhoneViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setRetainInstance(true)
        super.onCreate(savedInstanceState)
        binding = FragmentConfirmationPhoneBinding.inflate(layoutInflater)
        viewModel = activity?.let {
            ViewModelProvider(
                it,
                ModelFactory()
            )[ConfirmationPhoneViewModel::class.java]
        }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        numberPhone = arguments?.getString(NUMBER_PHONE)!!

        autoInputNumberPhone()
        processingInputCode()
        setupListenerResult()
        retryListeners()
        return binding.root
    }

    private fun retryListeners() {
        binding.text3.setOnClickListener {
            viewModel.retry(numberPhone)
        }

        viewModel.retryResult.observe(viewLifecycleOwner) {
            if (it.isNotBlank())
                binding.textError.text =
                    it //TODO желательно сделать счётчик на 4 секунды для скрытия
        }
    }

    private fun setupListenerResult() {
        viewModel.loadResultLiveData.observe(viewLifecycleOwner) { result ->
            renderResult(
                root = binding.root,
                result = result,
                onSuccessResult = {
                    binding.progressBar.visibility = View.GONE
                    binding.textError.visibility = View.GONE
                    returnResult()
                },
                onPending = {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.textError.visibility = View.GONE
                },
                onError = {
                    binding.progressBar.visibility = View.GONE
                    binding.textError.text = it.localizedMessage
                    binding.textError.visibility = View.VISIBLE
                }
            )
        }
    }

    private fun returnResult() {
        parentFragmentManager.popBackStack()
        parentFragmentManager.setFragmentResult(
            Constants.FRAGMENT_CONFIRMATION,
            bundleOf(LOGIN_STEP_2_MODEL to viewModel.loadResultLiveData.value?.takeSuccess())
        )
    }

    private fun autoInputNumberPhone() {
        binding.text2.text = getString(
            R.string.confirmation_phone_text_1,
            numberPhone
        )
    }

    private fun processingInputCode() {
        binding.inputNumber.addTextChangedListener {
            if (it?.length == 4) {
                viewModel.sending(numberPhone, it.toString())
            }
        }
    }

    companion object {
        fun getNewInstance(numberPhone: String) = ConfirmationPhoneFragment().apply {
            arguments = Bundle().apply {
                putString(NUMBER_PHONE, numberPhone)
            }
        }
    }

    override fun getCustomAction(): CustomAction = CustomAction(Constants.TYPE_ICON_GO_BACK)
}