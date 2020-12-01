package com.jigar.kotlin.ui.main.fragment.home

import com.jigar.kotlin.data.DataManager
import com.jigar.kotlin.ui.base.BaseViewModel
import com.jigar.kotlin.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class HomeFragmentViewModel @Inject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider,
    mCompositeDisposable: CompositeDisposable
) : BaseViewModel<HomeFragmentNavigator>(
    dataManager,
    schedulerProvider,
    mCompositeDisposable
) {

    fun getUserData() {
        val disposable: Disposable = getDataManager()
            .getLoggedUser()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({ data ->
                getNavigator()!!.setUserData(data)
            }, { throwable ->
                getNavigator()!!.handleError(throwable)
            })
        mCompositeDisposable.add(disposable)
    }

    fun logoutUser() {
        getNavigator()!!.showLoading()
        val disposable = getDataManager().clearAllData()
            .subscribeOn(getScheduleProvider().io())
            .observeOn(getScheduleProvider().ui())
            .subscribe {
                getNavigator()!!.hideLoading()
                getNavigator()!!.onLogoutSuccess()
            }
        getCompositeDisposable().add(disposable)
    }

}