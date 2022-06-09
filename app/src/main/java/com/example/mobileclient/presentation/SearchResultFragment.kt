package com.example.mobileclient.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mobileclient.Constants.Companion.LOGIN_STEP_2_MODEL
import com.example.mobileclient.data.storage.models.LoginStep2Model
import com.example.mobileclient.databinding.FragmentSearchResultBinding
import com.example.mobileclient.presentation.adapters.recycleview.SearchResultAdapter
import com.example.mobileclient.presentation.viewmodels.ModelFactory
import com.example.mobileclient.presentation.viewmodels.SearchResultViewModel

class SearchResultFragment : Fragment() {

    private lateinit var binding: FragmentSearchResultBinding
    private lateinit var viewModel: SearchResultViewModel
    private lateinit var resultAuthorization: LoginStep2Model
    private lateinit var searchAdapter: SearchResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSearchResultBinding.inflate(layoutInflater)
        viewModel = activity?.let {
            ViewModelProvider(
                it,
                ModelFactory()
            )[SearchResultViewModel::class.java]
        }!!
        startInit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        resultAuthorization =  arguments?.get(LOGIN_STEP_2_MODEL) as LoginStep2Model
        return binding.root
    }

    private fun startInit(){
        searchAdapter = SearchResultAdapter()
    }

    companion object {
        fun getNewInstance(modelResult: LoginStep2Model) = SearchResultFragment().apply{
            arguments = Bundle().apply {
                putParcelable(LOGIN_STEP_2_MODEL, modelResult)
            }
        }
    }
}