package com.jigar.kotlin.ui.base

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.androidnetworking.common.ANConstants
import com.androidnetworking.error.ANError
import com.google.gson.Gson
import com.jigar.kotlin.R
import com.jigar.kotlin.data.model.BaseResponse
import com.jigar.kotlin.utils.AppConstants
import com.jigar.kotlin.utils.NetworkUtils
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import okhttp3.Response
import javax.inject.Inject


abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity(),
    BaseNavigator, HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    private var progressBar: ProgressBar? = null
    // TODO
    // this can probably depend on isLoading variable of BaseViewModel,
    // since its going to be common for all the activities
    //private var mProgressDialog: ProgressDialog? = null
    private var mViewDataBinding: T? = null
    private var mViewModel: V? = null
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
    @IdRes
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun getViewModel(): V

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        mNavController = findNavController(R.id.nav_host_fragment)
    }

    fun getViewDataBinding(): T? {
        return mViewDataBinding
    }

    fun openActivityOnTokenExpire() {
        //startActivity(LoginActivity.newIntent(this));
        //finish();
    }

    private fun performDependencyInjection() {
        AndroidInjection.inject(this)
    }


    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        this.mViewModel = mViewModel ?: getViewModel()
        mViewDataBinding!!.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding!!.executePendingBindings()
    }

    // Base Navigator methods
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

    override fun showMessage(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(messageId: Int) {
        showMessage(getString(messageId))
    }

    override fun handleError(throwable: Throwable?) {
        if (throwable is ANError) {

            val networkResponse: Response? =
                if (throwable.response != null) throwable.response.networkResponse() else null

            if (networkResponse == null || throwable.errorBody == null) {
                runOnUiThread { showMessage(R.string.some_thing_wrong) }
            } else if (throwable.errorCode == 0 && throwable.errorDetail == ANConstants.CONNECTION_ERROR) { // No internet available
                runOnUiThread { showMessage(R.string.connection_error) }
            } else if (throwable.errorCode == AppConstants.StatusCodes.AUTHORIZATION_FAILED || throwable.errorCode == AppConstants.StatusCodes.FORBIDDEN) {
                runOnUiThread { showMessage(R.string.session_expired) }
            } else {
                runOnUiThread { showMessage(R.string.request_timeout) }
            }

            // Display error message from error response
            try {
                val errorResponse: BaseResponse =
                    Gson().fromJson(throwable.errorBody, BaseResponse::class.java)
                if (errorResponse.getMessage() != null && errorResponse.getMessage() != "") {
                    runOnUiThread { showMessage(errorResponse.getMessage()) }
                    return
                }
            } catch (e: Exception) {
            }
        } else {
            runOnUiThread { showMessage(throwable!!.message) }
        }
    }

    override fun isNetworkConnected(): Boolean {
        val isNetwork = NetworkUtils.isNetworkConnected(applicationContext)
        if (!isNetwork) {
            showMessage(R.string.no_internet)
        }
        return isNetwork
    }

    override fun androidInjector(): DispatchingAndroidInjector<Any>? {
        return androidInjector
    }

    private var progressDialog: AlertDialog? = null

    open fun initProgressDialog() {
        val inflater = layoutInflater
        val alertLayout: View = inflater.inflate(R.layout.dialog_loading, null)
        val builder1 =
            AlertDialog.Builder(this)
        builder1.setView(alertLayout)
        builder1.setCancelable(true)
        progressDialog = builder1.create()
        progressDialog!!.setCancelable(true)
        progressDialog!!.window!!.setBackgroundDrawableResource(R.color.transparent)
    }

    open fun hideKeyboard() {
        val imm: InputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    protected open fun setStatusBarColor(
        statusBarColor: Int,
        isLight: Boolean
    ) {
        val window: android.view.Window = window
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
}