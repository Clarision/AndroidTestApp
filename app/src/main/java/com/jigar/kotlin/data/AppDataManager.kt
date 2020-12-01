package com.jigar.kotlin.data

import android.content.Context
import android.content.pm.PackageInfo
import com.google.gson.Gson
import com.jigar.kotlin.R
import com.jigar.kotlin.data.local.db.DbHelper
import com.jigar.kotlin.data.local.pref.PreferencesHelper
import com.jigar.kotlin.data.model.user.login.LoginRequest
import com.jigar.kotlin.data.model.user.login.LoginResponse
import com.jigar.kotlin.data.model.user.login.UserData
import com.jigar.kotlin.data.remote.ApiHelper
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppDataManager @Inject constructor(
    val context: Context, val apiHelper: ApiHelper, val
    dbHelper: DbHelper, val mPreferencesHelper: PreferencesHelper, val gson: Gson
) : DataManager {


    //TODO User Module
    override fun userLogin(request: LoginRequest): Single<LoginResponse> {
        return apiHelper.userLogin(request)
            .flatMap label@{ response: LoginResponse ->
                if (response.getErrorCode() == "00") {
                    // TODO store user data
                    saveUserDB(response.getUserData())
                    setUserLogin(true)
                    return@label Single.just(response)
                }
                Single.just(response)
            }
    }

    // TODO Database
    override fun clearAllData(): Completable {
        return dbHelper.clearAllData().andThen(clearAllPrefs())
    }

    override fun saveUserDB(data: UserData) {
        dbHelper.saveUserDB(data)
    }

    override fun getLoggedUser(): Single<UserData> {
        return dbHelper.getLoggedUser()
    }

    // TODO Preferences
    override fun clearAllPrefs(): Completable {
        return mPreferencesHelper.clearAllPrefs()
    }

    override fun getAppColor(): Int {
        return mPreferencesHelper.getAppColor()
    }

    override fun setAppColor(appColor: Int) {
        mPreferencesHelper.setAppColor(appColor)
    }

    override fun getAccentColor(): Int {
        return mPreferencesHelper.getAccentColor()
    }

    override fun setAccentColor(bgColor: Int) {
        mPreferencesHelper.setAccentColor(bgColor)
    }

    override fun isUserLogin(): Boolean {
        return mPreferencesHelper.isUserLogin()
    }

    override fun setUserLogin(isUserLogin: Boolean) {
        mPreferencesHelper.setUserLogin(isUserLogin)
    }

    override fun getGsonNow(): Gson {
        return gson
    }

    override fun getVersionName(): String {
        val pInfo: PackageInfo =
            context.packageManager.getPackageInfo(context.packageName, 0)
        return context.resources.getString(R.string.app_name) + " v" + pInfo.versionName
    }


}