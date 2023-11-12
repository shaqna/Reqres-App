package com.maou.reqresapp.presentation.screens.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maou.reqresapp.domain.usecase.auth.AuthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModelOf

class RegisterViewModel(
    private val authUseCase: AuthUseCase
): ViewModel() {

    private val _uiState: MutableStateFlow<RegisterUiState> = MutableStateFlow(RegisterUiState())
    val uiState get() = _uiState

    fun processIntent(intent: RegisterIntent) {
        when(intent) {
            is RegisterIntent.OnRegister -> register(intent.email, intent.password)
        }
    }

    private fun showLoading() {
        _uiState.value = RegisterUiState(isLoading = true)
    }

    private fun hideLoading() {
        _uiState.value = RegisterUiState(isLoading = false)
    }

    private fun register(email: String, password: String) {
        viewModelScope.launch {
            authUseCase.register(email, password)
                .onStart {
                    showLoading()
                }
                .catch { err ->
                    hideLoading()
                    _uiState.value = RegisterUiState(errMessage = "Missing password")
                }
                .collect { result ->
                    hideLoading()
                    result.fold(onSuccess = {token ->
                        _uiState.value = RegisterUiState(token = token)
                    }, onFailure = {
                        _uiState.value = RegisterUiState(errMessage = "Missing password")
                    })
                }
        }
    }

    companion object {
        fun inject() = module {
            viewModelOf(::RegisterViewModel)
        }
    }

}

data class RegisterUiState(
    val isLoading: Boolean = false,
    val token: String = "",
    val errMessage: String = ""
)

sealed interface RegisterIntent {
    data class OnRegister(val email: String, val password: String): RegisterIntent
}