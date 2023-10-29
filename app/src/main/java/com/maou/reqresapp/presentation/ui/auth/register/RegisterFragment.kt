package com.maou.reqresapp.presentation.ui.auth.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.maou.reqresapp.R
import com.maou.reqresapp.databinding.FragmentRegisterBinding
import com.maou.reqresapp.presentation.ui.auth.login.LoginFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.context.loadKoinModules
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterFragment : Fragment() {

    private lateinit var _binding: FragmentRegisterBinding
    private val binding get() = _binding
    private val viewModel: RegisterViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(RegisterViewModel.inject())

        setButtonAction()
        observerUiState()
    }

    private fun setButtonAction() {
        with(binding) {
            btnRegister.setOnClickListener {
                register()
            }
            tvLogin.setOnClickListener {
                moveToLoginPage()
            }
        }
    }

    private fun moveToLoginPage() {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<LoginFragment>(R.id.fragment_container_view)
        }
    }

    private fun register() {
        with(binding) {
            val email = tilEmail.editText?.text.toString()
            val password = tilPassword.editText?.text.toString()

            viewModel.register(email, password)
        }
    }

    private fun observerUiState() {
        viewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {registerState ->
                handleState(registerState)
            }
            .launchIn(lifecycleScope)
    }

    private fun handleState(state: RegisterUiState) {
        when(state) {
            is RegisterUiState.Error -> {
                showLoading(false)
                showToast(state.message)
            }
            RegisterUiState.Init -> Unit
            is RegisterUiState.Loading -> {
                showLoading(true)
            }
            is RegisterUiState.Success -> {
                showLoading(false)
                parentFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<LoginFragment>(R.id.fragment_container_view)
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            btnRegister.isEnabled = !isLoading
            progressBar.isVisible = isLoading
        }
    }

}