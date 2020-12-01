package com.jigar.kotlin.di.builder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jigar.kotlin.di.ViewModelFactory
import com.jigar.kotlin.di.ViewModelKey
import com.jigar.kotlin.ui.loginsignup.LoginSignupViewModel
import com.jigar.kotlin.ui.loginsignup.fragment.login.LoginFragmentViewModel
import com.jigar.kotlin.ui.main.HomeModel
import com.jigar.kotlin.ui.main.fragment.home.HomeFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    // TODO Login Pages
    @Binds
    @IntoMap
    @ViewModelKey(LoginSignupViewModel::class)
    abstract fun bindLoginViewModel(myViewModel: LoginSignupViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginFragmentViewModel::class)
    abstract fun bindLoginFragmentViewModel(myViewModel: LoginFragmentViewModel): ViewModel

    // TODO Home Page
    @Binds
    @IntoMap
    @ViewModelKey(HomeModel::class)
    abstract fun bindMainViewModel(myViewModel: HomeModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeFragmentViewModel::class)
    abstract fun bindHomeFragmentViewModel(myViewModel: HomeFragmentViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}