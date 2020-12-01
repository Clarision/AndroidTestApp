package com.jigar.kotlin.data.local.db.forms

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jigar.kotlin.data.model.user.login.UserData
import io.reactivex.Single

@Dao
interface UserDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: UserData)

    @Query("SELECT *  FROM userData LIMIT 1")
    fun getLoggedUserData(): Single<UserData>

    @Query("DELETE FROM userData")
    fun deleteAll()
}