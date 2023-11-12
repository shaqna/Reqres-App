package com.maou.reqresapp.presentation.di

import com.maou.reqresapp.presentation.screens.auth.login.LoginViewModel
import com.maou.reqresapp.presentation.screens.auth.register.RegisterViewModel
import com.maou.reqresapp.presentation.screens.home.HomeViewModel
import com.maou.reqresapp.presentation.screens.create.CreateNewUserViewModel
import com.maou.reqresapp.presentation.screens.detail.DetailUserViewModel
import com.maou.reqresapp.presentation.screens.settings.SettingsViewModel
import com.maou.reqresapp.presentation.screens.splash.SplashViewModel
import org.koin.dsl.module

val viewModelModule = module {
    includes(
        LoginViewModel.inject(),
        RegisterViewModel.inject(),
        HomeViewModel.inject(),
        CreateNewUserViewModel.inject(),
        SettingsViewModel.inject(),
        SplashViewModel.inject(),
        DetailUserViewModel.inject()
    )
}