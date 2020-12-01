package com.jigar.kotlin.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jigar.kotlin.BuildConfig
import com.jigar.kotlin.data.AppDataManager
import com.jigar.kotlin.data.DataManager
import com.jigar.kotlin.data.local.db.AppDatabase
import com.jigar.kotlin.data.local.db.AppDbHelper
import com.jigar.kotlin.data.local.db.DbHelper
import com.jigar.kotlin.data.local.pref.AppPreferencesHelper
import com.jigar.kotlin.data.local.pref.PreferencesHelper
import com.jigar.kotlin.data.remote.ApiHeader
import com.jigar.kotlin.data.remote.ApiHelper
import com.jigar.kotlin.data.remote.AppApiHelper
import com.jigar.kotlin.di.DatabaseInfo
import com.jigar.kotlin.di.PreferenceInfo
import com.jigar.kotlin.di.builder.ViewModelModule
import com.jigar.kotlin.utils.AppConstants
import com.jigar.kotlin.utils.rx.AppSchedulerProvider
import com.jigar.kotlin.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    internal fun provideAppDataManager(appDataManager: AppDataManager): DataManager = appDataManager

    @Provides
    @Singleton
    internal fun provideApiHelper(appApiHelper: AppApiHelper): ApiHelper = appApiHelper

    @Provides
    @Singleton
    fun provideApiHeader(preferencesHelper: PreferencesHelper): ApiHeader {
        return ApiHeader(preferencesHelper)
    }

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider =
        AppSchedulerProvider()

    @Provides
    internal fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .connectTimeout(AppConstants.apiTimeout.Timeout, TimeUnit.SECONDS)
            .readTimeout(AppConstants.apiTimeout.Timeout, TimeUnit.SECONDS)
            .writeTimeout(AppConstants.apiTimeout.Timeout, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(provideGson()))
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .build()

    // Preferences
    @Provides
    @PreferenceInfo
    internal fun providePreferenceName(): String = AppConstants.PREF_NAME

    @Provides
    @Singleton
    internal fun providePreferencesHelper(appPreferencesHelper: AppPreferencesHelper): PreferencesHelper =
        appPreferencesHelper

    // Local Database
    @Provides
    @DatabaseInfo
    internal fun provideDbName(): String = AppConstants.DB_NAME

    @Provides
    @Singleton
    internal fun provideDbHelper(appDbHelper: AppDbHelper): DbHelper = appDbHelper

    @Provides
    @Singleton
    internal fun provideAppDatabase(@DatabaseInfo dbName: String, context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            dbName
        ).fallbackToDestructiveMigration()
            .build()


    // Networking
    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }


}