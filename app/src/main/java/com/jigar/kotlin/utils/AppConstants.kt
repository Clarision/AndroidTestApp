package com.jigar.kotlin.utils

object AppConstants {
    internal const val DB_NAME = "kotlin_basic.db";
    internal const val PREF_NAME = "kotlin_basic_pref";

    interface apiTimeout {
        companion object {
            const val Timeout = 300L
        }
    }

    annotation class StatusCodes {
        companion object {
            var SUCCESS = 200
            var CREATED = 201
            var ACCEPTED = 202
            var NO_CONTENT = 204
            var BAD_REQUEST = 400
            var AUTHORIZATION_FAILED = 401
            var FORBIDDEN = 403
            var NOT_FOUND = 404
            var METHOD_NOT_ALLOWED = 405
            var NOT_ACCEPTED = 406
            var PROXY_AUTHENTICATION_REQUIRED = 407
            var CONFLICT = 409
            var PREECONDITION_FAILED = 412
            var UNSUPPORDER_EDIA_TYPE = 415
            var INTERNAL_SERVER_ERROR = 500
            var NOT_IMPLEMENTED = 501
            var LOCAL_ERROR = 0
        }
    }
}