package com.jigar.kotlin.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class BaseResponse {

    @SerializedName("errorCode")
    @Expose
    private var errorCode: String = ""

    @SerializedName("errorMessage")
    @Expose
    private var errorMessage: String = ""

    open fun getErrorCode(): String {
        return errorCode
    }

    open fun setErrorCode(errorCode: String) {
        this.errorCode = errorCode
    }

    open fun getMessage(): String {
        return errorMessage
    }

    open fun setErrorMessage(errorMessage: String) {
        this.errorMessage = errorMessage
    }
}