package com.maou.reqresapp.presentation.screens.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maou.reqresapp.domain.usecase.auth.AuthUseCase
import com.maou.reqresapp.presentation.screens.auth.register.RegisterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModelOf

class LoginViewModel(
    private val useCase: AuthUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val uiState get() = _uiState.asStateFlow()


    fun processIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.OnLogin -> login(intent.email, intent.password)
        }
    }

    private fun showLoading() {
        _uiState.value = LoginUiState(isLoading = true)
    }

    private fun hideLoading() {
        _uiState.value = LoginUiState(isLoading = false)
    }


    private fun login(email: String, password: String) {
        viewModelScope.launch {
            useCase.login(email, password)
                .onStart {
                    showLoading()
                }
                .catch { err ->
                    hideLoading()
                }
                .collect { result ->
                    hideLoading()
                    result.fold(onSuccess = { token ->
                        _uiState.value =
                            LoginUiState(token = token)
                    }, onFailure = {
                        _uiState.value =
                            LoginUiState(
                                errMessage = "Missing Password"
                            )

                    })

                }
        }
    }


    companion object {
        fun inject() = module {
            viewModelOf(::LoginViewModel)
        }
    }
}

data class LoginUiState(
    val isLoading: Boolean = false,
    val token: String = "",
    val errMessage: String = ""
)

sealed interface LoginIntent {
    data class OnLogin(val email: String, val password: String) : LoginIntent
}