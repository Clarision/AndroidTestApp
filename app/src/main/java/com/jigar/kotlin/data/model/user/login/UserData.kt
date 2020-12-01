package com.jigar.kotlin.data.model.user.login

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "userData")
class UserData {
    @PrimaryKey(autoGenerate = true)
    private var appId = 0

    @SerializedName("userId")
    @Expose
    private var userId: String = ""

    @SerializedName("userName")
    @Expose
    private var userName: String = ""

    fun getAppId(): Int {
        return appId
    }

    fun setAppId(appId: Int) {
        this.appId = appId
    }

    fun getUserId(): String {
        return userId
    }

    fun setUserId(userId: String) {
        this.userId = userId
    }

    fun getUserName(): String {
        return userName
    }

    fun setUserName(userName: String) {
        this.userName = userName
    }

}