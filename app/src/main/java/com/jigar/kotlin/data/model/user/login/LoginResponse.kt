package com.jigar.kotlin.data.model.user.login

import com.google.gson.annotations.SerializedName
import com.jigar.kotlin.data.model.BaseResponse

class LoginResponse : BaseResponse() {

    @SerializedName("user")
    private var userData: UserData = UserData()

    fun getUserData(): UserData {
        return userData
    }

    fun setUserData(data: UserData) {
        userData = data
    }
}