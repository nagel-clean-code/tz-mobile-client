package com.example.mobileclient.presentation

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.mobileclient.Constants
import com.example.mobileclient.Constants.Companion.LOGIN_STEP_2_MODEL
import com.example.mobileclient.data.storage.models.LoginStep2Model
import com.example.mobileclient.data.storage.models.ResponseSearch
import com.example.mobileclient.databinding.FragmentSearchBinding
import com.example.mobileclient.presentation.contract.CustomAction
import com.example.mobileclient.presentation.contract.HasCustomActionToolbar
import com.example.mobileclient.presentation.contract.navigator
import com.example.mobileclient.presentation.models.state.takeSuccess
import com.example.mobileclient.presentation.viewmodels.ModelFactory
import com.example.mobileclient.presentation.viewmodels.SearchViewModel

class SearchFragment : BaseFragment(), HasCustomActionToolbar {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var resultAuthorization: LoginStep2Model

    override fun onCreate(savedInstanceState: Bundle?) {
        setRetainInstance(true)
        super.onCreate(savedInstanceState)
        binding = FragmentSearchBinding.inflate(layoutInflater)
        viewModel = activity?.let {
            ViewModelProvider(
                it,
                ModelFactory()
            )[SearchViewModel::class.java]
        }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        resultAuthorization = arguments?.get(LOGIN_STEP_2_MODEL) as LoginStep2Model
        textEditListenerFormSearch()
        setupListenerResult()
        return binding.root
    }
    private var showedResultFlag = true
    private fun setupListenerResult() {
        viewModel.loadResultLiveData.observe(viewLifecycleOwner) { result ->
            renderResult(
                root = binding.root,
                result = result,
                onSuccessResult = {
                    binding.progressBar.visibility = View.GONE
                    binding.textError.visibility = View.GONE
                    showedResultFlag = if(showedResultFlag) {
                        showResult()
                        false
                    }else{
                        true    //Если результат уже был показан, то можно показывать снова
                    }
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

    override fun onPause() {
        super.onPause()
        viewModel.loadResultLiveData.removeObservers(viewLifecycleOwner)
    }

    private fun showResult() =
        navigator().showFragmentSearchResult(viewModel.loadResultLiveData.value?.takeSuccess() as ResponseSearch)

    private fun textEditListenerFormSearch() {
        binding.searchEditText.setOnEditorActionListener { _, _, keyEvent ->
            if (keyEvent == null || (keyEvent.action == KeyEvent.ACTION_DOWN)) {
                viewModel.searchByString(binding.searchEditText.text.toString())
                return@setOnEditorActionListener false
            } else {
                return@setOnEditorActionListener true
            }
        }
    }

    companion object {
        fun getNewInstance(modelResult: LoginStep2Model) = SearchFragment().apply {
            arguments = Bundle().apply {
                putParcelable(LOGIN_STEP_2_MODEL, modelResult)
            }
        }
    }

    override fun getCustomAction(): CustomAction = CustomAction(Constants.TYPE_ICON_GO_BACK)
}