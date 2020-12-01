package com.jigar.kotlin.ui.loginsignup.fragment.login

import com.jigar.kotlin.R
import com.jigar.kotlin.data.DataManager
import com.jigar.kotlin.data.model.user.login.LoginRequest
import com.jigar.kotlin.ui.base.BaseViewModel
import com.jigar.kotlin.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class LoginFragmentViewModel @Inject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider,
    mCompositeDisposable: CompositeDisposable
) : BaseViewModel<LoginFragmentNavigator>(dataManager, schedulerProvider, mCompositeDisposable) {

    // 1. This method is called from xml view Button
    fun onLoginSubmit(username: String, password: String) {
        if (username.isEmpty()) {
            getNavigator()!!.showMessage(R.string.valid_username)
        }else if (password.isEmpty()) {
            getNavigator()!!.showMessage(R.string.valid_password)
        } else if(getNavigator()!!.isNetworkConnected()){
            val request = LoginRequest()
            request.setUserName(username)
            request.setPassword(password)
            performLogin(request)
        }else{
            getNavigator()!!.showMessage(R.string.no_internet)
        }
    }

    // TODO Api calling
    private fun performLogin(request: LoginRequest) {
        getNavigator()!!.showLoading()
        val disposable: Disposable = getDataManager()
            .userLogin(request).subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ response ->
                getNavigator()!!.hideLoading()
                if (response.getErrorCode() == "00") {
                    getNavigator()?.moveNext()
                }else{
                    getNavigator()!!.showMessage(response.getMessage())
                }
            }, { throwable ->
                getNavigator()!!.hideLoading()
                getNavigator()!!.handleError(throwable)
            })
        mCompositeDisposable.add(disposable)
    }
}