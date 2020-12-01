package com.jigar.kotlin.data.local.db

import com.jigar.kotlin.data.model.user.login.UserData
import io.reactivex.Completable
import io.reactivex.Single

interface DbHelper {

    fun clearAllData(): Completable
    fun saveUserDB(list: UserData)
    fun getLoggedUser(): Single<UserData>
}