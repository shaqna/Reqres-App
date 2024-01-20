package com.maou.reqresapp.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.maou.reqresapp.R
import com.maou.reqresapp.databinding.ActivityHomeBinding
import com.maou.reqresapp.presentation.mapper.toParcel
import com.maou.reqresapp.presentation.ui.create.CreateNewUserActivity
import com.maou.reqresapp.presentation.ui.detail.DetailUserActivity
import com.maou.reqresapp.presentation.ui.detail.DetailUserActivity.Companion.REQRES_USER_PARCEL
import com.maou.reqresapp.presentation.ui.settings.SettingsActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class HomeActivity : AppCompatActivity() {

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter().apply {
            onItemClickListener = { user, optionCompat ->
                Intent(this@HomeActivity, DetailUserActivity::class.java).also {
                    it.putExtra(REQRES_USER_PARCEL, user.toParcel())
                    startActivity(it, optionCompat.toBundle())
                }
            }
        }
    }

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadKoinModules(HomeViewModel.inject())

        setupAdapter()
        setupToolbar()
        setButtonAction()
        fetchListUser()
//        observeUiState()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }


    private fun setupAdapter() {
        with(binding) {
            rvUsers.adapter = homeAdapter
            rvUsers.layoutManager = LinearLayoutManager(this@HomeActivity)
        }
    }

    private fun setupToolbar() {
        with(binding) {
            toolbar.setOnMenuItemClickListener {item ->
                when(item.itemId) {
                    R.id.settingsMenu -> {
                        startActivity(Intent(this@HomeActivity, SettingsActivity::class.java))
                    }
                }

                false
            }
        }
    }

    private fun setButtonAction() {
        with(binding) {
            floatingActionButton.setOnClickListener {
                Intent(this@HomeActivity, CreateNewUserActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }

    private fun fetchListUser() {
        lifecycleScope.launch {
            viewModel.getUsers().collectLatest {users ->
                homeAdapter.submitData(users)
            }
        }
    }

//    private fun observeUiState() {
//        viewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
//            .onEach { homeUiState ->
//                handleState(homeUiState)
//            }
//            .launchIn(lifecycleScope)
//    }

//    private fun handleState(state: HomeUiState) {
//        when (state) {
//            is HomeUiState.Failure -> {
//                showLoading(false)
//                showErrorMessage(state.message, true)
//                showToast(state.message)
//            }
//
//            HomeUiState.Init -> Unit
//            is HomeUiState.Loading -> {
//                showErrorMessage(isVisible = false)
//                showLoading(state.isLoading)
//            }
//
//            is HomeUiState.Success -> {
//                showLoading(false)
//                showErrorMessage(isVisible = false)
//
//                Log.d("MyTAG", "handleState: ${state.users.toString()}")
//
//                homeAdapter.apply {
//                    addLoadStateListener { loadState ->
//                        submitData(state.users)
//                    }
//                }
//            }
//        }
//    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            progressBar.isVisible = isLoading
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@HomeActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun showErrorMessage(message: String? = null, isVisible: Boolean) {
        binding.tvError.apply {
            text = message
            this.isVisible = isVisible
        }
    }
}