package com.jigar.kotlin.ui.main.fragment.home

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.jigar.kotlin.BR
import com.jigar.kotlin.R
import com.jigar.kotlin.data.model.user.login.UserData
import com.jigar.kotlin.databinding.DialogCommanBinding
import com.jigar.kotlin.databinding.FragmentHomeBinding
import com.jigar.kotlin.ui.base.BaseFragment
import com.jigar.kotlin.ui.loginsignup.LoginSignupActivity
import com.jigar.kotlin.ui.start.SplashActivity


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeFragmentViewModel>(),
    HomeFragmentNavigator, Toolbar.OnMenuItemClickListener {

    private val viewModelInstance: HomeFragmentViewModel by viewModels {
        viewModeFactory
    }
    private var mBinding: FragmentHomeBinding? = null
    override fun getBindingVariable(): Int = BR.homeFragmentViewModel

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun getViewModel(): HomeFragmentViewModel = viewModelInstance

    private var alertDailog: AlertDialog? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = getViewDataBinding()
        viewModelInstance.setNavigator(this)

        toolBar()
        init()
        clickListener()
    }


    private fun toolBar() {

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        mBinding!!.toolBarLayout.toolBar.navigationIcon = null
        mBinding!!.toolBarLayout.toolBar.title = resources.getString(R.string.app_name)

        mBinding!!.toolBarLayout.toolBar.inflateMenu(R.menu.menu_home)
        mBinding!!.toolBarLayout.toolBar.setOnMenuItemClickListener(this)
    }


    override fun init() {
        // TODO Fetch user data
        getViewModel().getUserData()
    }

    override fun clickListener() {

    }

    override fun setUserData(data: UserData) {
        mBinding!!.userData = data
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menuLogout -> {
                logoutAlert()
            }
        }
        return true
    }

    // TODO Logout user
    private fun logoutAlert() {

        val inflater = layoutInflater
        val alertLayout: DialogCommanBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_comman, null, false)

        val builder1 =
            AlertDialog.Builder(requireContext())
        builder1.setView(alertLayout.root)
        builder1.setCancelable(true)
        alertLayout.txtTitle.text = resources.getString(R.string.account_Logout)
        alertLayout.txtMsg.text = resources.getString(R.string.account_Logout_msg)
        alertLayout.txtCancel.setOnClickListener { alertDailog!!.dismiss() }
        alertLayout.txtYes.setOnClickListener {
            alertDailog!!.dismiss()
            getViewModel().logoutUser()
        }
        alertDailog = builder1.create()
        alertDailog!!.show()
    }


    override fun onLogoutSuccess() {
        requireActivity().finishAffinity()
        val intentNextScreen = Intent(requireActivity(), SplashActivity::class.java)
        startActivity(intentNextScreen)
        requireActivity().finish()
    }

    override fun onBack() {
        requireActivity().finish()
        requireActivity().overridePendingTransition(
            R.anim.b_nav_default_pop_enter_anim,
            R.anim.b_nav_default_pop_exit_anim
        )
    }

}