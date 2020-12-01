package com.jigar.kotlin.ui.main.fragment.home

import com.jigar.kotlin.data.model.user.login.UserData
import com.jigar.kotlin.ui.base.BaseNavigator

interface HomeFragmentNavigator :
    BaseNavigator {
    fun setUserData(data: UserData)
    fun onLogoutSuccess()
}