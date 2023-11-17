package com.maou.reqresapp.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maou.reqresapp.domain.usecase.auth.AuthUseCase
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

class SettingsViewModel(private val authUseCase: AuthUseCase): ViewModel() {

    fun processIntent(intent: SettingsIntent) {
        when(intent) {
            SettingsIntent.LogoutIntent -> deleteToken()
        }
    }

    private fun deleteToken() {
        viewModelScope.launch {
            authUseCase.deleteToken()
        }
    }

    companion object {
        fun inject() = module {
            viewModelOf(::SettingsViewModel)
        }
    }
}

sealed interface SettingsIntent {
    object LogoutIntent: SettingsIntent
}