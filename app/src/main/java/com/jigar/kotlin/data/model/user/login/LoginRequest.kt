package com.jigar.kotlin.data.model.user.login

import com.google.gson.annotations.SerializedName

class LoginRequest {
    @SerializedName("username")
    private var username = ""

    @SerializedName("password")
    private var password = ""

    fun getUserName(): String {
        return username
    }

    fun setUserName(username: String) {
        this.username = username
    }

    fun getPassword(): String {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
    }
}