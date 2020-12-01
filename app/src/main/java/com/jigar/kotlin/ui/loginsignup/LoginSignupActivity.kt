package com.jigar.kotlin.ui.loginsignup

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavGraph
import com.jigar.kotlin.BR
import com.jigar.kotlin.R
import com.jigar.kotlin.databinding.BaseActivityBinding
import com.jigar.kotlin.ui.base.BaseActivity
import com.jigar.kotlin.utils.AppConstants
import java.util.*

class  LoginSignupActivity : BaseActivity<BaseActivityBinding, LoginSignupViewModel>(),
    LoginSignupNavigator {

    private var mBinding: BaseActivityBinding? = null

    private val viewModelInstance: LoginSignupViewModel by viewModels {
        viewModeFactory
    }


    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int = R.layout.base_activity

    override fun getViewModel(): LoginSignupViewModel {
        return viewModelInstance
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = getViewDataBinding()

        setupNavController()
        viewModelInstance.setNavigator(this)
        init()
        clickListener()

    }

    private fun setupNavController() {
        val graph: NavGraph =
            mNavController!!.navInflater.inflate(R.navigation.nav_login_graph)
        graph.startDestination = R.id.loginFragment
        mNavController!!.graph = graph
    }

    override fun onBack() {

    }

    override fun init() {
        mBinding!!.toolBarLayout.toolBar.visibility = View.GONE
    }

    override fun clickListener() {
    }


}
