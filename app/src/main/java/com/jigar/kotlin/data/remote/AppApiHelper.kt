package com.jigar.kotlin.data.remote

import com.jigar.kotlin.data.local.pref.PreferencesHelper
import com.jigar.kotlin.data.model.user.login.LoginRequest
import com.jigar.kotlin.data.model.user.login.LoginResponse
import com.rx2androidnetworking.Rx2ANRequest
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppApiHelper @Inject constructor(
    val mApiHeader: ApiHeader,
    val preferencesHelper: PreferencesHelper
) :
    ApiHelper {

    //    TODO User Module
    override fun userLogin(request: LoginRequest): Single<LoginResponse> {
        val rxRequest: Rx2ANRequest.PostRequestBuilder =
            Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_LOGIN)
        rxRequest.addHeaders("IMSI", "357175048449937")
        rxRequest.addHeaders("IMEI", "510110406068589")
        rxRequest.addApplicationJsonBody(request)
        return rxRequest.build().getObjectSingle(LoginResponse::class.java)
    }

}