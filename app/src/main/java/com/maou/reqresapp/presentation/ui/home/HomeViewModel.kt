package com.maou.reqresapp.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maou.reqresapp.domain.model.ReqresUser
import com.maou.reqresapp.domain.usecase.users.ReqresUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

class HomeViewModel(private val useCase: ReqresUsersUseCase) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Init)
    val uiState get() = _uiState


    private fun showLoading() {
        _uiState.value = HomeUiState.Loading(true)
    }

    private fun hideLoading() {
        _uiState.value = HomeUiState.Loading(false)
    }

    fun getUsers() {
        viewModelScope.launch {
            useCase.getUsers()
                .onStart {
                    showLoading()
                }
                .catch { t ->
                    hideLoading()
                    _uiState.value = HomeUiState.Failure(t.message.toString())
                }
                .collect { result ->
                    result.fold(
                        onSuccess = {listUsers ->
                            _uiState.value = HomeUiState.Success(listUsers)
                        },
                        onFailure = {t ->
                            _uiState.value = HomeUiState.Failure(t.message.toString())
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

sealed interface HomeUiState {
    data object Init : HomeUiState
    data class Loading(val isLoading: Boolean) : HomeUiState
    data class Success(val users: List<ReqresUser>) : HomeUiState
    data class Failure(val message: String) : HomeUiState
}