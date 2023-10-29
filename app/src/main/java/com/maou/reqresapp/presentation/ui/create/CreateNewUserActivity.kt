package com.maou.reqresapp.presentation.ui.create

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.maou.reqresapp.databinding.ActivityCreateNewUserBinding
import com.maou.reqresapp.presentation.ui.home.HomeActivity
import com.maou.reqresapp.utils.generateNotificationMessage
import com.maou.reqresapp.utils.makeStatusNotification
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class CreateNewUserActivity : AppCompatActivity() {

    private val binding: ActivityCreateNewUserBinding by lazy {
        ActivityCreateNewUserBinding.inflate(layoutInflater)
    }

    private val viewModel: CreateNewUserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadKoinModules(CreateNewUserViewModel.inject())

        setButtonAction()
        observerUiState()
    }

    private fun setButtonAction() {
        with(binding) {
            btnCreate.setOnClickListener {
                createNewUser()
            }

            btnBack.setOnClickListener {
                back()
            }
        }
    }

    private fun back() {
        startActivity(Intent(this, HomeActivity::class.java)).also {
            finish()
        }
    }

    private fun createNewUser() {
        with(binding) {
            val fullName = tilFullName.editText?.text.toString()
            val job = tilJob.editText?.text.toString()

            viewModel.createNewUser(fullName, job)
        }
    }

    private fun observerUiState() {
        viewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { createUiState ->
                handleState(createUiState)
            }
            .launchIn(lifecycleScope)
    }

    private fun handleState(state: CreateUiState) {
        when(state) {
            is CreateUiState.Failure -> {
                showLoading(false)
                showToast(state.message)
            }
            CreateUiState.Init -> Unit
            is CreateUiState.Loading -> showLoading(state.isLoading)
            is CreateUiState.Success -> {
                showLoading(false)

                val message = generateNotificationMessage("User ${state.newUser.name} has been created")
                makeStatusNotification(message, this)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            btnCreate.isEnabled = !isLoading
            progressBar.isVisible = isLoading
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@CreateNewUserActivity, message, Toast.LENGTH_SHORT).show()
    }

}