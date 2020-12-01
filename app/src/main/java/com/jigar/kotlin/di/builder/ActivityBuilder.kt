package com.jigar.kotlin.di.builder

import com.jigar.kotlin.ui.main.HomeActivity
import com.jigar.kotlin.ui.loginsignup.LoginSignupActivity
import com.jigar.kotlin.ui.start.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(FragmentBuildersModule::class)])
    abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [(FragmentBuildersModule::class)])
    abstract fun bindLoginSignupActivity(): LoginSignupActivity

    @ContributesAndroidInjector(modules = [(FragmentBuildersModule::class)])
    abstract fun bindHomeActivity(): HomeActivity

}