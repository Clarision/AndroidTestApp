package com.jigar.kotlin.data

import com.google.gson.Gson
import com.jigar.kotlin.data.local.db.DbHelper
import com.jigar.kotlin.data.local.pref.PreferencesHelper
import com.jigar.kotlin.data.remote.ApiHelper
import io.reactivex.Completable

interface DataManager : ApiHelper,
    DbHelper,
    PreferencesHelper {

    fun getGsonNow(): Gson

    fun getVersionName(): String

}