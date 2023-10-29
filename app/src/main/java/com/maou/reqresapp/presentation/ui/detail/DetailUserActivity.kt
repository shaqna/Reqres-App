package com.maou.reqresapp.presentation.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maou.reqresapp.databinding.ActivityDetailUserBinding
import com.maou.reqresapp.presentation.model.ReqresUserParcel
import com.maou.reqresapp.utils.bindImage
import com.maou.reqresapp.utils.generateFullName
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class DetailUserActivity : AppCompatActivity() {

    private val binding: ActivityDetailUserBinding by lazy {
        ActivityDetailUserBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailUserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadKoinModules(DetailUserViewModel.inject())

        getParcel()
        observerUiState()
    }

    private fun getParcel() {
        viewModel.getDetailUser(intent, REQRES_USER_PARCEL)
    }

    private fun observerUiState() {

        when(val state = viewModel.uiState.value) {
            is DetailUiState.HasData -> {
                setDetailUser(state.user)
            }
            DetailUiState.Init -> Unit
        }
    }

    private fun setDetailUser(user: ReqresUserParcel) {
        with(binding) {
            tvEmail.text = user.email
            tvUserName.text = generateFullName(user.firstName, user.lastName)
            bindImage(this@DetailUserActivity, user.avatar, ivAvatar)
        }
    }

    companion object {
        const val REQRES_USER_PARCEL = "REQRES_USER_PARCEL"
    }

}