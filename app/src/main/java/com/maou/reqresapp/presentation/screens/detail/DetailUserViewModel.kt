package com.maou.reqresapp.presentation.screens.detail

import android.content.Intent
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maou.reqresapp.presentation.model.ReqresUserParcel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

class DetailUserViewModel: ViewModel() {

    private val _uiState: MutableStateFlow<DetailUiState> = MutableStateFlow(DetailUiState.Init)
    val uiState get() = _uiState

    fun getDetailUser(intent: Intent, key: String) {
        viewModelScope.launch {
            val parcel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(key, ReqresUserParcel::class.java)
            } else {
                intent.getParcelableExtra(key)
            }

            parcel?.let { user ->
                _uiState.value = DetailUiState.HasData(user)
            }
        }

    }


    companion object {
        fun inject() = module {
            viewModelOf(::DetailUserViewModel)
        }
    }
}

sealed interface DetailUiState {
    object Init: DetailUiState
    data class HasData(val user: ReqresUserParcel): DetailUiState
}