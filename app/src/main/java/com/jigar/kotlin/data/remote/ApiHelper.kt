package com.jigar.kotlin.data.remote

import com.jigar.kotlin.data.model.user.login.LoginRequest
import com.jigar.kotlin.data.model.user.login.LoginResponse
import io.reactivex.Single

interface ApiHelper {
    fun userLogin(request: LoginRequest): Single<LoginResponse>
}