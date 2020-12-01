package com.jigar.kotlin.ui.main

import com.jigar.kotlin.data.DataManager
import com.jigar.kotlin.ui.base.BaseViewModel
import com.jigar.kotlin.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomeModel @Inject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider,
    mCompositeDisposable: CompositeDisposable
) : BaseViewModel<HomeNavigator>(dataManager, schedulerProvider, mCompositeDisposable) {

}