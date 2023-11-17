package com.maou.reqresapp.presentation.screens.create

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

    private val _uiState: MutableStateFlow<CreateUiState> = MutableStateFlow(CreateUiState())
    val uiState get() = _uiState

    fun processIntent(intent: CreatePersonIntent) {
        when(intent) {
            is CreatePersonIntent.CreateNewPerson -> createNewUser(intent.name, intent.job)
        }
    }

    private fun showLoading() {
        _uiState.value = CreateUiState(isLoading = true)
    }

    private fun hideLoading() {
        _uiState.value = CreateUiState(isLoading = false)
    }

    private fun createNewUser(name: String, job: String) {
        viewModelScope.launch {
            userUserCase.createUser(name, job)
                .onStart {
                    showLoading()
                }
                .catch {
                    hideLoading()
                    _uiState.value = CreateUiState(errMessage =  it.message.toString())
                }
                .collect { result ->
                    result.fold(
                        onSuccess = { newUser ->
                            _uiState.value = CreateUiState(newUser = newUser)
                        },
                        onFailure = {
                            _uiState.value = CreateUiState(errMessage = it.message.toString())
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

data class CreateUiState(
    val isLoading: Boolean = false,
    val newUser: NewUser? = null,
    val errMessage: String = ""
)

sealed interface CreatePersonIntent {
    data class CreateNewPerson(val name: String, val job: String): CreatePersonIntent
}