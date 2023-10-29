package com.maou.reqresapp.presentation.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maou.reqresapp.domain.usecase.auth.AuthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModelOf

class LoginViewModel(
    private val useCase: AuthUseCase
): ViewModel() {
    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState.Init)
    val uiState get() = _uiState


    private fun showLoading() {
        _uiState.value = LoginUiState.Loading(true)
    }

    private fun hideLoading() {
        _uiState.value = LoginUiState.Loading(false)
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            useCase.login(email, password)
                .onStart {
                    showLoading()
                }
                .catch {err ->
                    hideLoading()
                    _uiState.value = LoginUiState.Error("Missing password")
                }
                .collect { result ->
                    hideLoading()
                    result.fold(onSuccess = {token ->
                        _uiState.value = LoginUiState.Success(token)
                    }, onFailure = {
                        _uiState.value = LoginUiState.Error("Missing password")
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

sealed interface LoginUiState {
    data object Init: LoginUiState
    data class Loading(val isLoading: Boolean): LoginUiState
    data class Success(val token: String) : LoginUiState
    data class Error(val message: String): LoginUiState
}