package com.maou.reqresapp.presentation.ui.auth.register

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

    private val _uiState: MutableStateFlow<RegisterUiState> = MutableStateFlow(RegisterUiState.Init)
    val uiState get() = _uiState


    private fun showLoading() {
        _uiState.value = RegisterUiState.Loading(true)
    }

    private fun hideLoading() {
        _uiState.value = RegisterUiState.Loading(false)
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            authUseCase.register(email, password)
                .onStart {
                    showLoading()
                }
                .catch { err ->
                    hideLoading()
                    _uiState.value = RegisterUiState.Error("Missing password")
                }
                .collect { result ->
                    hideLoading()
                    result.fold(onSuccess = {token ->
                        _uiState.value = RegisterUiState.Success(token)
                    }, onFailure = {
                        _uiState.value = RegisterUiState.Error("Missing password")
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

sealed interface RegisterUiState {
    data object Init: RegisterUiState
    data class Loading(val isLoading: Boolean): RegisterUiState
    data class Success(val token: String): RegisterUiState
    data class Error(val message: String): RegisterUiState
}