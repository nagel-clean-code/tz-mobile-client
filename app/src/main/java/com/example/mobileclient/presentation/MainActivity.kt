package com.example.mobileclient.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.mobileclient.Constants
import com.example.mobileclient.Constants.Companion.TYPE_ICON_CLOSE
import com.example.mobileclient.Constants.Companion.TYPE_ICON_DEFAULT
import com.example.mobileclient.Constants.Companion.TYPE_ICON_GO_BACK
import com.example.mobileclient.R
import com.example.mobileclient.data.storage.models.*
import com.example.mobileclient.databinding.ActivityMainBinding
import com.example.mobileclient.presentation.contract.CustomAction
import com.example.mobileclient.presentation.contract.HasCustomActionToolbar
import com.example.mobileclient.presentation.contract.Navigator
import com.example.mobileclient.presentation.viewmodels.MainViewModel
import com.example.mobileclient.presentation.viewmodels.ModelFactory
import com.example.mobileclient.presentation.viewmodels.SearchViewModel

class MainActivity : AppCompatActivity(), Navigator {
    private lateinit var binding: ActivityMainBinding
    private var mActionBarToolbar: Toolbar? = null
    private val currentFragment: Fragment
        get() = supportFragmentManager.findFragmentById(R.id.fragment_container)!!
    private lateinit var viewModel: MainViewModel
    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            updateUi()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ModelFactory(this))[MainViewModel::class.java]

        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)
        if (savedInstanceState == null) {
            val session = viewModel.getSession()
            if (session == null) {
                showFragmentLogin()
            } else {
                showFragmentSearch(session)
            }
        }
        initActionBarToolbar()
        setupListenerResult()
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
    }

    private fun setupListenerResult() {
        supportFragmentManager.setFragmentResultListener(
            Constants.FRAGMENT_AUTHORIZATION,
            this
        ) { _, bundle ->
            val number: LoginStep1Model =
                bundle.get(Constants.LOGIN_STEP_1_MODEL) as LoginStep1Model
            showFragmentConfirmationPhone(number.normalizedPhone!!)
        }

        supportFragmentManager.setFragmentResultListener(
            Constants.FRAGMENT_CONFIRMATION,
            this
        ) { _, bundle ->
            val modelResult: LoginStep2Model =
                bundle.get(Constants.LOGIN_STEP_2_MODEL) as LoginStep2Model
            saveSession(modelResult)
            showFragmentSearch(modelResult)
        }
    }

    private fun saveSession(loginStep2Model: LoginStep2Model) {
        viewModel.saveSession(loginStep2Model)
    }

    override fun showFragmentSearchResult(responseSearch: ResponseSearch) =
        launchFragment(ShowSearchResultFragment.getNewInstance(responseSearch))

    override fun showFragmentSearch(loginStep2Model: LoginStep2Model) =
        launchFragment(SearchFragment.getNewInstance(loginStep2Model))

    override fun showFragmentConfirmationPhone(numberPhone: String) =
        launchFragment(ConfirmationPhoneFragment.getNewInstance(numberPhone))

    override fun showFragmentLogin() =
        launchFragment(AuthorizationFragment.getNewInstance())

    override fun showInformationAboutCampaignFragment(campaignsItem: CampaignsItem) =
        launchFragment(InformationAboutElementFragment.getNewInstance(campaignsItem))

    override fun showInformationAboutProductFragment(productsItem: ProductsItem) =
        launchFragment(InformationAboutElementFragment.getNewInstance(productsItem))

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun goBack() {
        if (supportFragmentManager.backStackEntryCount != 1) {
            onBackPressed()
        }
    }

    private fun initActionBarToolbar(): Toolbar? {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = findViewById<View>(R.id.toolbar) as Toolbar
            if (mActionBarToolbar != null) {
                setSupportActionBar(mActionBarToolbar)
            }
        }
        return mActionBarToolbar
    }

    private fun updateUi() {
        val fragment = currentFragment

        if (fragment is HasCustomActionToolbar) {
            createCustomToolbarAction(fragment.getCustomAction())
        } else {
            createCustomToolbarAction(CustomAction(TYPE_ICON_DEFAULT))
        }
    }

    private fun createCustomToolbarAction(action: CustomAction) {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setDisplayShowHomeEnabled(false)
        }
        when (action.typeIcon) {
            TYPE_ICON_CLOSE -> {
                mActionBarToolbar?.setNavigationIcon(R.drawable.ic_baseline_close_24)
                setSupportActionBar(mActionBarToolbar)
                supportActionBar?.setDisplayShowTitleEnabled(false)
            }
            TYPE_ICON_GO_BACK -> {
                mActionBarToolbar?.navigationIcon = null
                setSupportActionBar(mActionBarToolbar)
                supportActionBar?.setDisplayShowTitleEnabled(false)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setHomeButtonEnabled(true)
            }
            TYPE_ICON_DEFAULT -> {
                mActionBarToolbar?.navigationIcon = null
                setSupportActionBar(mActionBarToolbar)
                supportActionBar?.setDisplayShowTitleEnabled(false)
            }
        }
        mActionBarToolbar!!.setNavigationOnClickListener { goBack() }
    }
}