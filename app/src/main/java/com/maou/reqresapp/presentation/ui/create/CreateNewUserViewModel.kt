package com.maou.reqresapp.presentation.ui.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maou.reqresapp.domain.model.NewUser
import com.maou.reqresapp.domain.usecase.users.ReqresUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

class CreateNewUserViewModel(private val userUserCase: ReqresUsersUseCase): ViewModel() {

    private val _uiState: MutableStateFlow<CreateUiState> = MutableStateFlow(CreateUiState.Init)
    val uiState get() = _uiState

    private fun showLoading() {
        _uiState.value = CreateUiState.Loading(true)
    }

    private fun hideLoading() {
        _uiState.value = CreateUiState.Loading(false)
    }

    fun createNewUser(name: String, job: String) {
        viewModelScope.launch {
            userUserCase.createUser(name, job)
                .onStart {
                    showLoading()
                }
                .catch {
                    hideLoading()
                    _uiState.value = CreateUiState.Failure(it.message.toString())
                }
                .collect { result ->
                    result.fold(
                        onSuccess = { newUser ->
                            _uiState.value = CreateUiState.Success(newUser)
                        },
                        onFailure = {
                            _uiState.value = CreateUiState.Failure(it.message.toString())
                        }
                    )
                }
        }
    }

    companion object {
        fun inject() = module {
            viewModelOf(::CreateNewUserViewModel)
        }
    }
}

sealed interface CreateUiState {
    data object Init: CreateUiState
    data class Loading(val isLoading: Boolean): CreateUiState
    data class Success(val newUser: NewUser): CreateUiState
    data class Failure(val message: String): CreateUiState
}