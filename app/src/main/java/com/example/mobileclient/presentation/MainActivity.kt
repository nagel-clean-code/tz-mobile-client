package com.example.mobileclient.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.mobileclient.Constants
import com.example.mobileclient.R
import com.example.mobileclient.data.storage.models.LoginStep1Model
import com.example.mobileclient.data.storage.models.LoginStep2Model

class MainActivity : AppCompatActivity() {
    private var mActionBarToolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        getActionBarToolbar()
        initToolBar()

        setupListenerResult()
        //showFragmentLogin() //FIXME пока тестю работу с поиском, потом восстановить
        showFragmentSearch(LoginStep2Model())
    }

    private fun setupListenerResult() {
        supportFragmentManager.setFragmentResultListener(
            Constants.FRAGMENT_AUTHORIZATION,
            this
        ) { _, bundle ->
            val number: LoginStep1Model = bundle.get(Constants.LOGIN_STEP_1_MODEL) as LoginStep1Model
            showFragmentConfirmationPhone(number.normalizedPhone!!)
        }

        supportFragmentManager.setFragmentResultListener(
            Constants.FRAGMENT_CONFIRMATION,
            this
        ) { _, bundle ->
            val modelResult: LoginStep2Model = bundle.get(Constants.LOGIN_STEP_2_MODEL) as LoginStep2Model
//            showFragmentConfirmationPhone(number!!)
        }
    }

    private fun showFragmentSearch(loginStep2Model: LoginStep2Model) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, SearchResultFragment.getNewInstance(loginStep2Model)).commit()
    }

    private fun showFragmentConfirmationPhone(numberPhone: String) {
        mActionBarToolbar?.navigationIcon = null
        setSupportActionBar(mActionBarToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, ConfirmationPhoneFragment.getNewInstance(numberPhone))
            .commit()
    }

    private fun showFragmentLogin() {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, AuthorizationFragment.getNewInstance()).commit()
    }

    private fun getActionBarToolbar(): Toolbar? {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = findViewById<View>(R.id.toolbar) as Toolbar
            if (mActionBarToolbar != null) {
                setSupportActionBar(mActionBarToolbar)
            }
        }
        return mActionBarToolbar
    }

    private fun initToolBar() {
        mActionBarToolbar?.setNavigationIcon(R.drawable.ic_baseline_close_24)
//        mActionBarToolbar?.overflowIcon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_close_24) //FIXME оставил код, на случай если понадобится
        setSupportActionBar(mActionBarToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeButtonEnabled(true)
    }
}