package com.jigar.kotlin.ui.loginsignup.fragment.login

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.jigar.kotlin.BR
import com.jigar.kotlin.R
import com.jigar.kotlin.databinding.FragmentLoginBinding
import com.jigar.kotlin.ui.base.BaseFragment
import com.jigar.kotlin.utils.AppConstants

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginFragmentViewModel>(),
    LoginFragmentNavigator {

    private val viewModelInstance: LoginFragmentViewModel by viewModels {
        viewModeFactory
    }
    private var mBinding: FragmentLoginBinding? = null
    override fun getBindingVariable(): Int = BR.loginFragmentViewModel

    override fun getLayoutId(): Int = R.layout.fragment_login

    override fun getViewModel(): LoginFragmentViewModel = viewModelInstance

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
        setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.white), false)
    }


    override fun init() {
    }

    override fun clickListener() {
    }

    override fun moveNext() {
        requireActivity().finishAffinity()
        mNavController!!.navigate(R.id.toMainActivityFromLogin)
    }


    override fun onBack() {
        requireActivity().finish()
        requireActivity().overridePendingTransition(
            R.anim.b_nav_default_pop_enter_anim,
            R.anim.b_nav_default_pop_exit_anim
        )
    }


}