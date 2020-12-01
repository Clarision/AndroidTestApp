package com.jigar.kotlin.utils

object AppConstants {
    internal const val DB_NAME = "kotlin_basic.db";
    internal const val PREF_NAME = "kotlin_basic_pref";

    const val CHANNEL_DESCRIPTION = "Klicck notification channel"
    const val LOCAL_CHANNEL_ID = "Klicck_notification_local_channel_id"
    const val LOCAL_CHANNEL_NAME = "Klicck Notification"
    val APP_FIREBASE_TOPIC = "Klicck_app"
    const val CUSTOMER_FIREBASE_TOPIC = "Klicck_customer_"

    interface apiSearch {
        companion object {
            const val searchValue = "searchValue"
            const val searchType_Hospital = "Hospital"
            const val searchType_Package = "Package"
        }
    }
    interface apiTimeout {
        companion object {
            const val Timeout = 300L
        }
    }
    interface HomeClicks {
        companion object {
            const val TYPE = "type"
            const val FROM = "FROM"

            const val FROM_HOME = 1
            const val FROM_LIST = 2


            const val EDIT_PROFILE = 1
            const val LANGUAGE = 2
            const val LOGOUT = 3

            const val HOME = 4
            const val MY_BUSINESS = 5
            const val MORE = 6

            const val HTML = 7
            const val PRIVACY = 8
        }
    }
    annotation class Extras_Comman {
        companion object {
            var DATA = "data"
            var imagePath = "imagePath"
            var customerId = "customerId"
            var FROM = "from"
            var startLocation = "startLocation"
            var endLocation = "endLocation"

            var app_language_EN = "en"
            var app_language_HI = "hi"
            var app_language_GU = "gu"
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

    interface htmlPageSlug {
        companion object {
            const val htmlType = "htmlType"
            const val privacy_policy = "privacy-policy"
            const val disclaimer = "disclaimer"
            const val terms_condition = "term-condition"
            const val refund_policy = "refund-policy"
            const val about_us = "about-us"
        }
    }

    // TODO api param
    interface apiComman {
        companion object {
            const val from = "from"
            const val fromOTP = "OTP"
            const val fromStart = "start"
            const val fromHome = "home"
            const val fromInstruction = "Instruction"
            const val fromEditProduct = "EditProduct"
            const val customers_id = "customers_id"
            const val Currency = "₹"
        }
    }

}