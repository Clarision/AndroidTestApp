package com.jigar.kotlin.data.local.db

import com.jigar.kotlin.data.model.user.login.UserData
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDbHelper @Inject
constructor(private val appDatabase: AppDatabase) :
    DbHelper {

    override fun clearAllData(): Completable {
        return Completable
            .fromAction { appDatabase.clearAllTables() }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .delay(1000, TimeUnit.MILLISECONDS)
    }

    override fun saveUserDB(data: UserData) = appDatabase.userDataDao().insert(data)

    override fun getLoggedUser(): Single<UserData> {
        return appDatabase.userDataDao().getLoggedUserData()
    }


}