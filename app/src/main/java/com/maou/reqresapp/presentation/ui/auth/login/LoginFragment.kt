package com.maou.reqresapp.presentation.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.maou.reqresapp.databinding.FragmentLoginBinding
import com.maou.reqresapp.presentation.ui.auth.register.RegisterFragment
import com.maou.reqresapp.presentation.ui.home.HomeActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class LoginFragment : Fragment() {

    private lateinit var _binding: FragmentLoginBinding
    private val binding get() = _binding
    private val viewModel: LoginViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(LoginViewModel.inject())

        setButtonAction()
        observeUiState()
    }

    private fun setButtonAction() {
        with(binding) {
            btnLogin.setOnClickListener {
                login()
            }

            tvRegister.setOnClickListener {
                moveToRegisterPage()
            }
        }
    }

    private fun moveToRegisterPage() {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RegisterFragment>(R.id.fragment_container_view)
        }
    }

    private fun login() {
        with(binding) {
            val email = tilEmail.editText?.text.toString()
            val password = tilPassword.editText?.text.toString()

            Log.d("Akun", "$email, $password")

            viewModel.login(email, password)

        }
    }

    private fun observeUiState() {
        viewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { loginState ->
                handleState(loginState)
            }
            .launchIn(lifecycleScope)
    }

    private fun handleState(state: LoginUiState) {
        when (state) {
            is LoginUiState.Error -> {
                showLoading(false)
                showToast(state.message)
                Log.d("My Err", state.message)
            }

            LoginUiState.Init -> Unit
            is LoginUiState.Loading -> showLoading(state.isLoading)
            is LoginUiState.Success -> {
                showLoading(false)
                Intent(requireActivity(), HomeActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            btnLogin.isEnabled = !isLoading
            progressBar.isVisible = isLoading
        }
    }

}