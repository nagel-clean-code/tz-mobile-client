package com.example.mobileclient.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.mobileclient.Constants
import com.example.mobileclient.Constants.Companion.FRAGMENT_AUTHORIZATION
import com.example.mobileclient.Constants.Companion.LOGIN_STEP_1_MODEL
import com.example.mobileclient.databinding.FragmentAuthorizationBinding
import com.example.mobileclient.presentation.contract.CustomAction
import com.example.mobileclient.presentation.contract.HasCustomActionToolbar
import com.example.mobileclient.presentation.models.state.SuccessResult
import com.example.mobileclient.presentation.models.state.takeSuccess
import com.example.mobileclient.presentation.viewmodels.AuthorizationViewModel
import com.example.mobileclient.presentation.viewmodels.ModelFactory


class AuthorizationFragment : BaseFragment(), HasCustomActionToolbar {

    private lateinit var binding: FragmentAuthorizationBinding
    private lateinit var viewModel: AuthorizationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentAuthorizationBinding.inflate(layoutInflater)
        viewModel = activity?.let {
            ViewModelProvider(
                it,
                ModelFactory()
            )[AuthorizationViewModel::class.java]
        }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        buttonListener()
        listenerResults()
        return binding.root
    }

    private fun buttonListener(){
        binding.button.setOnClickListener {
            viewModel.sendCode(binding.inputNumber.text.toString())
            if(viewModel.loadResultLiveData.value is SuccessResult)
                returnResult()
        }
    }

    private fun listenerResults(){
        viewModel.loadResultLiveData.observe(viewLifecycleOwner) { result ->
            renderResult(
                root = binding.root,
                result = result,
                onSuccessResult = {
                    binding.progressBar.visibility = View.GONE
                    binding.textError.visibility = View.GONE
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

    private fun returnResult(){
        parentFragmentManager.popBackStack()
        parentFragmentManager.setFragmentResult(
            FRAGMENT_AUTHORIZATION,
            bundleOf(LOGIN_STEP_1_MODEL to viewModel.loadResultLiveData.value?.takeSuccess())
        )
    }

    companion object {
        fun getNewInstance(): AuthorizationFragment {
            return AuthorizationFragment()
        }
    }

    override fun getCustomAction(): CustomAction = CustomAction(Constants.TYPE_ICON_CLOSE)
}