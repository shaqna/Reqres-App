package com.maou.reqresapp.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maou.reqresapp.domain.usecase.auth.AuthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

class SplashViewModel(private val useCase: AuthUseCase): ViewModel() {

    private val _token: MutableStateFlow<String?> = MutableStateFlow(null)
    val token get() = _token

    fun getToken() {
        viewModelScope.launch {
            _token.value = useCase.getToken()
        }
    }

    companion object {
        fun inject() = module {
            viewModelOf(::SplashViewModel)
        }
    }
}