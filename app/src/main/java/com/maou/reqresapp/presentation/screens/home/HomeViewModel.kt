package com.maou.reqresapp.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maou.reqresapp.domain.model.ReqresUser
import com.maou.reqresapp.domain.usecase.users.ReqresUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

class HomeViewModel(private val useCase: ReqresUsersUseCase) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState get() = _uiState.asStateFlow()

    fun processIntent(intent: HomeIntent) {
        when(intent) {
            HomeIntent.FetchUsers -> getUsers()
        }
    }

    private fun showLoading() {
        _uiState.value = HomeUiState(isLoading = true)
    }

    private fun hideLoading() {
        _uiState.value = HomeUiState(isLoading = true)
    }

    private fun getUsers() {
        viewModelScope.launch {
            useCase.getUsers()
                .onStart {
                    showLoading()
                }
                .catch { t ->
                    hideLoading()
                    _uiState.value = HomeUiState(errMessage = t.message.toString())
                }
                .collect { result ->
                    hideLoading()
                    result.fold(
                        onSuccess = {listUsers ->
                            _uiState.value = HomeUiState(users = listUsers)
                        },
                        onFailure = {t ->
                            _uiState.value = HomeUiState(errMessage = t.message.toString())
                        }
                    )
                }
        }
    }

    companion object {
        fun inject() = module {
            viewModelOf(::HomeViewModel)
        }
    }
}

sealed interface HomeIntent {
    object FetchUsers: HomeIntent
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val users: List<ReqresUser> = emptyList(),
    val errMessage: String = ""
)