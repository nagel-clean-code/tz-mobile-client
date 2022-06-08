package com.example.mobileclient.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.mobileclient.Constants
import com.example.mobileclient.R
import com.example.mobileclient.data.storage.models.LoginStep1Model
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

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
        showFragmentLogin()
    }

    private fun setupListenerResult() {
        supportFragmentManager.setFragmentResultListener(
            Constants.FRAGMENT_AUTHORIZATION,
            this
        ) { _, bundle ->
            val number = bundle.getString(Constants.NUMBER_PHONE)
            showFragmentConfirmationPhone(number!!)
        }
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
//        mActionBarToolbar?.overflowIcon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_close_24)
        setSupportActionBar(mActionBarToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeButtonEnabled(true)
    }
}