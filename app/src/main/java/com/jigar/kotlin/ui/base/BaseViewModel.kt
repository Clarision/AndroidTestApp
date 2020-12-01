package com.jigar.kotlin.ui.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.jigar.kotlin.data.DataManager
import com.jigar.kotlin.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

abstract class BaseViewModel<N : BaseNavigator>(
    private val dataManager: DataManager,
    val schedulerProvider: SchedulerProvider,
    val mCompositeDisposable: CompositeDisposable
) : ViewModel() {

    lateinit var mNavigator: WeakReference<N>

    fun getDataManager(): DataManager = dataManager

    fun getScheduleProvider(): SchedulerProvider = schedulerProvider

    fun getCompositeDisposable(): CompositeDisposable = mCompositeDisposable

    private val mAccentColor = dataManager.getAccentColor()
    private val mAppColor = dataManager.getAppColor()

    fun getNavigator(): N? {
        return mNavigator.get()
    }

    fun setNavigator(navigator: N) {
        this.mNavigator = WeakReference(navigator)
    }

    private val isEmpty = ObservableBoolean(true)

    open fun isEmpty(): ObservableBoolean? {
        return isEmpty
    }

    open fun setIsEmpty(isEmpty: Boolean) {
        this.isEmpty.set(isEmpty)
    }

    open fun getAccentColor(): Int {
        return mAccentColor
    }

    open fun getAppColor(): Int {
        return mAppColor
    }
}