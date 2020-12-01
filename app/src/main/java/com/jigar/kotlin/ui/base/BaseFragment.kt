package com.jigar.kotlin.ui.base

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.androidnetworking.common.ANConstants
import com.androidnetworking.error.ANError
import com.google.gson.Gson
import com.jigar.kotlin.R
import com.jigar.kotlin.data.model.BaseResponse
import com.jigar.kotlin.utils.AppConstants.StatusCodes.Companion.AUTHORIZATION_FAILED
import com.jigar.kotlin.utils.AppConstants.StatusCodes.Companion.FORBIDDEN
import com.jigar.kotlin.utils.NetworkUtils
import com.jigar.kotlin.utils.widget.toolbar.MenuTintUtils
import dagger.android.support.AndroidSupportInjection
import okhttp3.Response
import javax.inject.Inject


abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<*>> :
    Fragment(), BaseNavigator {

    lateinit var mRootView: View
    //    private lateinit var mViewDataBinding: T
    private var mViewDataBinding: T? = null
    private lateinit var mViewModel: V
    var mNavController: NavController? = null
        private set

    @Inject
    lateinit var viewModeFactory: ViewModelProvider.Factory

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract fun getBindingVariable(): Int

    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun getViewModel(): V

    fun getViewDataBinding(): T? {
        return mViewDataBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        mViewModel = getViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)

        mViewDataBinding!!.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding!!.executePendingBindings()

        mRootView = mViewDataBinding!!.root
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mNavController = findNavController(this)
        CameraStrictMode()
    }

    open fun CameraStrictMode() {
        //Todo solve camera error
        val builder: StrictMode.VmPolicy.Builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }

    protected open fun getBaseActivity(): BaseActivity<*, *> =
        requireActivity() as BaseActivity<*, *>


    private fun performDependencyInjection() = AndroidSupportInjection.inject(this)

    protected open fun setStatusBarColor(
        statusBarColor: Int,
        isLight: Boolean
    ) {
        val window: android.view.Window = getBaseActivity().window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
//        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags: Int = window.decorView.systemUiVisibility
            flags = if (isLight) {
                flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            } else {
                flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
            window.decorView.systemUiVisibility = flags
        }
        window.statusBarColor = statusBarColor
    }

    protected open fun setStatusBarOverlay() {
        val window: android.view.Window = getBaseActivity().window
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

//        var flags: Int = window.decorView.systemUiVisibility
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//        }
//        window.decorView.systemUiVisibility = flags

    }

    protected open fun setStatusBarHeight(view: View) {

        val statusBarHeight = MenuTintUtils.getStatusBarHeight(requireContext())
        val lp = view.layoutParams
        lp.height = statusBarHeight
        view.layoutParams = lp

    }

    override fun showLoading() {
        if (progressDialog != null && !progressDialog!!.isShowing) {
            progressDialog!!.show()
        } else {
            initProgressDialog()
            progressDialog!!.show()
        }
    }

    override fun hideLoading() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }

    private var progressDialog: AlertDialog? = null

    open fun initProgressDialog() {
        val inflater = layoutInflater
        val alertLayout: View = inflater.inflate(R.layout.dialog_loading, null)
        val builder1 =
            AlertDialog.Builder(this.activity!!)
        builder1.setView(alertLayout)
        builder1.setCancelable(true)
        progressDialog = builder1.create()
        progressDialog!!.setCancelable(false)
        progressDialog!!.window!!.setBackgroundDrawableResource(R.color.transparent)
    }

    override fun showMessage(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(messageId: Int) {
        showMessage(getString(messageId))
    }

    override fun handleError(throwable: Throwable?) {
        if (throwable is ANError) {
            println("handleError=="+throwable.message)
            println("handleError::"+throwable.localizedMessage)
            val networkResponse: Response? =
                if (throwable.response != null) throwable.response.networkResponse() else null

            if (networkResponse == null || throwable.errorBody == null) {
                requireActivity().runOnUiThread {
                    showMessage(R.string.some_thing_wrong)
                }
            } else if (throwable.errorCode == 0 && throwable.errorDetail == ANConstants.CONNECTION_ERROR) { // No internet available
                requireActivity().runOnUiThread { showMessage(R.string.connection_error) }
            } else if (throwable.errorCode == AUTHORIZATION_FAILED || throwable.errorCode == FORBIDDEN) {
                requireActivity().runOnUiThread { showMessage(R.string.session_expired) }
            } else {
                requireActivity().runOnUiThread { showMessage(R.string.request_timeout) }
            }

            // Display error message from error response
            try {
                val errorResponse: BaseResponse =
                    Gson().fromJson(throwable.errorBody, BaseResponse::class.java)
                if (errorResponse.getMessage() != "") {
                    requireActivity().runOnUiThread { showMessage(errorResponse.getMessage()) }
                    return
                }
            } catch (e: Exception) {
            }
        } else {
            requireActivity().runOnUiThread { showMessage(throwable!!.message) }
        }
    }

    override fun isNetworkConnected(): Boolean {
        val isNetwork = NetworkUtils.isNetworkConnected(this.activity!!)
        if (!isNetwork) {
            showMessage(R.string.no_internet)
        }
        return isNetwork
    }

    open fun isNetworkConnected_NoToast(activity: FragmentActivity): Boolean {
        return NetworkUtils.isNetworkConnected(activity)
    }

    open fun hideKeyboard() {
        val imm: InputMethodManager =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = requireActivity().currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(requireActivity())
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}