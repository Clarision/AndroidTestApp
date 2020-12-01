package com.jigar.kotlin.data.local.pref

import io.reactivex.Completable

interface PreferencesHelper {

    fun getAppColor(): Int
    fun setAppColor(appColor: Int)

    fun getAccentColor(): Int
    fun setAccentColor(accentColor: Int)

    fun isUserLogin(): Boolean
    fun setUserLogin(isUserLogin: Boolean)

    fun clearAllPrefs(): Completable

}
