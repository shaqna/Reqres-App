package com.maou.reqresapp.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.maou.reqresapp.presentation.screens.auth.login.LoginIntent
import com.maou.reqresapp.presentation.screens.auth.login.LoginScreen
import com.maou.reqresapp.presentation.screens.auth.login.LoginViewModel
import com.maou.reqresapp.presentation.screens.auth.register.RegisterScreen
import com.maou.reqresapp.presentation.screens.auth.register.RegisterViewModel
import com.maou.reqresapp.presentation.screens.home.HomeScreen
import com.maou.reqresapp.presentation.screens.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    startDestination: String,
    innerPadding: PaddingValues
) {

    val context = LocalContext.current

    NavHost(
        navController = navHostController, startDestination = startDestination
    ) {
        composable(Screen.Login.name) {

            val viewModel = koinViewModel<LoginViewModel>()
            val state by viewModel.uiState.collectAsState()

            LoginScreen(
                context = context,
                navController = navHostController,
                loginUiState = state,
                onLoginIntent = {intent ->
                    viewModel.processIntent(intent)
                }
            )
        }

        composable(Screen.Register.name) {
            val viewModel = koinViewModel<RegisterViewModel>()
            val state by viewModel.uiState.collectAsState()

            RegisterScreen(
                context = context,
                navController = navHostController,
                registerUiState = state,
                onRegisterIntent = { intent ->
                    viewModel.processIntent(intent)
                }
            )
        }

        composable(Screen.Home.name) {
            val viewModel = koinViewModel<HomeViewModel>()
            val state by viewModel.uiState.collectAsState()

            HomeScreen(
                context = context,
                navController = navHostController,
                state = state,
                onIntent = { intent ->
                    viewModel.processIntent(intent)
                }
            )
        }
    }
}