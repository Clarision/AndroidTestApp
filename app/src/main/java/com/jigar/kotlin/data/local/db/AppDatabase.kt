package com.jigar.kotlin.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jigar.kotlin.data.local.db.forms.UserDataDao
import com.jigar.kotlin.data.model.user.login.UserData

@Database(
    entities = [UserData::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDataDao(): UserDataDao
}
