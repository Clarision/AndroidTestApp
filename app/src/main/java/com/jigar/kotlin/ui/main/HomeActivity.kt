package com.jigar.kotlin.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavGraph
import com.jigar.kotlin.BR
import com.jigar.kotlin.R
import com.jigar.kotlin.databinding.BaseActivityBinding
import com.jigar.kotlin.ui.base.BaseActivity

class HomeActivity : BaseActivity<BaseActivityBinding, HomeModel>(),
    HomeNavigator {

    private var mBinding: BaseActivityBinding? = null

    private val viewModelInstance: HomeModel by viewModels {
        viewModeFactory
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int = R.layout.base_activity

    override fun getViewModel(): HomeModel {
        return viewModelInstance
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = getViewDataBinding()

        viewModelInstance.setNavigator(this)
        setupNavController()
        toolbar()
        init()
        clickListener()
    }

    private fun toolbar() {

    }

    private fun setupNavController() {
        setStatusBarColor(getViewModel().getAppColor(), true)
        setSupportActionBar(mBinding!!.toolBarLayout.toolBar)
        val graph: NavGraph =
            mNavController!!.navInflater.inflate(R.navigation.nav_home_graph)
        graph.startDestination = R.id.homeFragment
        mNavController!!.graph = graph

    }

    override fun init() {
        mBinding!!.toolBarLayout.toolBar.visibility = View.GONE
    }


    override fun clickListener() {
    }

    override fun onBack() {
    }

}
