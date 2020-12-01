package com.jigar.kotlin.ui.start

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.jigar.kotlin.R
import com.jigar.kotlin.data.local.pref.AppPreferencesHelper
import com.jigar.kotlin.databinding.ActivitySplashBinding
import com.jigar.kotlin.ui.loginsignup.LoginSignupActivity
import com.jigar.kotlin.ui.main.HomeActivity
import com.jigar.kotlin.utils.AppConstants
import dagger.android.AndroidInjection

class SplashActivity : AppCompatActivity() {

    private var binding: ActivitySplashBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        AndroidInjection.inject(this)
        Init()
    }

    private fun Init() {
        AppPreferencesHelper(this, AppConstants.PREF_NAME).setAppColor(
            ContextCompat.getColor(
                this,
                R.color.colorPrimaryDark
            )
        )
        AppPreferencesHelper(
            this,
            AppConstants.PREF_NAME
        ).setAccentColor(ContextCompat.getColor(this, R.color.colorAccent))
        goNext()
    }


    private fun goNext() {
        if (AppPreferencesHelper(this, AppConstants.PREF_NAME).isUserLogin()) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, LoginSignupActivity::class.java))
            finish()
        }

    }
}