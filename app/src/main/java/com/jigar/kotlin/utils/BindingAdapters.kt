package com.jigar.kotlin.utils

import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.jigar.kotlin.R
import com.jigar.kotlin.data.local.pref.AppPreferencesHelper

object BindingAdapters {


    @BindingAdapter("txtColorApp")
    @JvmStatic
    fun txtColorApp(view: MaterialTextView, tintColor: Int?) {
        var tintColor = tintColor
        if (tintColor == 0) {
            tintColor =
                AppPreferencesHelper(view.context, AppConstants.PREF_NAME).getAppColor()
        } else if (tintColor == 1) {
            tintColor =
                AppPreferencesHelper(view.context, AppConstants.PREF_NAME).getAccentColor()
        }
        view.setTextColor(ColorStateList.valueOf(tintColor!!))
    }

    @BindingAdapter("viewBG")
    @JvmStatic
    fun viewBG(view: View, tintColor: Int?) {
        var tintColor = tintColor
        if (tintColor == 0) {
            tintColor =
                AppPreferencesHelper(view.context, AppConstants.PREF_NAME).getAppColor()
        } else if (tintColor == 1) {
            tintColor =
                AppPreferencesHelper(view.context, AppConstants.PREF_NAME).getAccentColor()
        }
        view.setBackgroundColor(tintColor!!)
    }


    @BindingAdapter("buttonColor")
    @JvmStatic
    fun buttonColor(button: MaterialButton, tintColor: Int?) {
        var tintColor = tintColor
        if (tintColor == null) {
            tintColor =
                AppPreferencesHelper(button.context, AppConstants.PREF_NAME).getAccentColor()
        }
        button.backgroundTintList = ColorStateList.valueOf(tintColor)

        button.setTextColor(
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    button.context,
                    R.color.white
                )
            )
        )
    }


}