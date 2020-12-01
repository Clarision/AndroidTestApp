package com.jigar.kotlin.data.remote

import com.google.gson.annotations.SerializedName
import com.jigar.kotlin.data.local.pref.PreferencesHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiHeader @Inject constructor(private val preferencesHelper: PreferencesHelper) {
    fun getApiHeader(): TokenHeader {
        return TokenHeader("")
    }

    class TokenHeader(accessToken: String) {
        @SerializedName("Authorization")
        private val mAccessToken: String = String.format("Bearer %s", accessToken)

    }
}