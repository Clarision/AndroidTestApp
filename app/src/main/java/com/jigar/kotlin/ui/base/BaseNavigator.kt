package com.jigar.kotlin.ui.base

import androidx.annotation.StringRes

interface BaseNavigator {
    fun handleError(throwable: Throwable?)
    fun showLoading()
    fun hideLoading()
    fun onBack()
    fun init()
    fun clickListener()
    fun isNetworkConnected(): Boolean
    fun showMessage(message: String?)
    fun showMessage(@StringRes messageId: Int)
}
