package com.jigar.kotlin.data.remote

import com.jigar.kotlin.BuildConfig


object ApiEndPoint {

    private const val BASE_URL: String = BuildConfig.BASE_URL
    const val ENDPOINT_SERVER_LOGIN = BASE_URL + "login"
}
