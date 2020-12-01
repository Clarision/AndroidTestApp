package com.jigar.kotlin.ui.loginsignup

import com.jigar.kotlin.data.DataManager
import com.jigar.kotlin.ui.base.BaseViewModel
import com.jigar.kotlin.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LoginSignupViewModel @Inject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider,
    mCompositeDisposable: CompositeDisposable
) : BaseViewModel<LoginSignupNavigator>(dataManager, schedulerProvider, mCompositeDisposable) {


}