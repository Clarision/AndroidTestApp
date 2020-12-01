package com.jigar.kotlin

import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.facebook.stetho.Stetho
import com.jigar.kotlin.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import okhttp3.OkHttpClient
import javax.inject.Inject

class KotlinBasicApp : Application(), HasAndroidInjector {

    @Inject
    internal lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    internal lateinit var okHttpClient: OkHttpClient

    private var mInstance: KotlinBasicApp? = null

    override fun onCreate() {
        super.onCreate()

        mInstance = this

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }

        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)

        AndroidNetworking.initialize(applicationContext, okHttpClient)

        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY)
//            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BASIC)// for multipart
        }

    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}
