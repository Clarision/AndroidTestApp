package com.jigar.kotlin.data.local.pref

import android.content.Context
import android.content.SharedPreferences
import com.jigar.kotlin.di.PreferenceInfo
import io.reactivex.Completable
import javax.inject.Inject


class AppPreferencesHelper @Inject constructor(
    context: Context,
    @PreferenceInfo private val prefFileName: String
) :
    PreferencesHelper {
    companion object {
        private const val PREF_APP_COLOR = "pref_app_color"
        private const val PREF_BG_COLOR = "pref_bg_color"
        private const val PREF_UserLogin = "pref_is_user_login"
    }

    private val mPrefs: SharedPreferences =
        context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    override fun getAppColor(): Int {
        return mPrefs.getInt(PREF_APP_COLOR, 0)
    }

    override fun setAppColor(appColor: Int) {
        mPrefs.edit().putInt(PREF_APP_COLOR, appColor).apply()
    }

    override fun getAccentColor(): Int {
        return mPrefs.getInt(PREF_BG_COLOR, 0)
    }

    override fun setAccentColor(bgColor: Int) {
        mPrefs.edit().putInt(PREF_BG_COLOR, bgColor).apply()
    }

    override fun isUserLogin(): Boolean {
        return mPrefs.getBoolean(PREF_UserLogin, false)
    }

    override fun setUserLogin(isUserLogin: Boolean) {
        mPrefs.edit().putBoolean(PREF_UserLogin, isUserLogin).apply()
    }

    override fun clearAllPrefs(): Completable {
        return Completable.fromAction {
            mPrefs.edit().clear().apply()
        }
    }
}
