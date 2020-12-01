package com.jigar.kotlin.utils

import android.view.View

object ViewUtils {
    fun disableView(view: View, alpha: Float) {
        view.isEnabled = false
        view.alpha = alpha
    }

    fun enableView(view: View) {
        view.isEnabled = true
        view.alpha = 1.0f
    }
}