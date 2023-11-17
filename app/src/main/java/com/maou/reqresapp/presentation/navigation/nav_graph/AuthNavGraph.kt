package com.maou.reqresapp.presentation.navigation.nav_graph

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.maou.reqresapp.presentation.navigation.AUTH_GRAPH_ROUTE
import com.maou.reqresapp.presentation.navigation.Screen
import com.maou.reqresapp.presentation.screens.auth.login.LoginScreen
import com.maou.reqresapp.presentation.screens.auth.login.LoginViewModel
import com.maou.reqresapp.presentation.screens.auth.register.RegisterScreen
import com.maou.reqresapp.presentation.screens.auth.register.RegisterViewModel
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.authGraph(
    navHostController: NavHostController,
    context: Context
) {
    navigation(startDestination = Screen.Login.route, route = AUTH_GRAPH_ROUTE) {
        composable(Screen.Login.route) {

            val viewModel = koinViewModel<LoginViewModel>()
            val state by viewModel.uiState.collectAsState()

            LoginScreen(
                context = context,
                navController = navHostController,
                loginUiState = state,
                onLoginIntent = { intent ->
                    viewModel.processIntent(intent)
                }
            )
        }

        composable(Screen.Register.route) {
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
    }
}
