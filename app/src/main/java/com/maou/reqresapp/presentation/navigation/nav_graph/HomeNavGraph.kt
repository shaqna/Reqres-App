package com.maou.reqresapp.presentation.navigation.nav_graph

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.maou.reqresapp.presentation.model.ReqresUserParcel
import com.maou.reqresapp.presentation.navigation.DETAIL_PERSON_ARGUMENT_KEY
import com.maou.reqresapp.presentation.navigation.HOME_GRAPH_ROUTE
import com.maou.reqresapp.presentation.navigation.Screen
import com.maou.reqresapp.presentation.screens.create.CreateNewUserViewModel
import com.maou.reqresapp.presentation.screens.create.CreatePersonScreen
import com.maou.reqresapp.presentation.screens.detail.DetailPersonScreen
import com.maou.reqresapp.presentation.screens.home.HomeScreen
import com.maou.reqresapp.presentation.screens.home.HomeViewModel
import com.maou.reqresapp.presentation.screens.settings.SettingsScreen
import com.maou.reqresapp.presentation.screens.settings.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.homeGraph(
    navHostController: NavHostController,
    context: Context
) {
    navigation(startDestination = Screen.Home.route, route = HOME_GRAPH_ROUTE) {
        composable(Screen.Home.route) {
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

        composable(Screen.Create.route) {
            val viewModel = koinViewModel<CreateNewUserViewModel>()
            val state by viewModel.uiState.collectAsState()

            CreatePersonScreen(
                context = context,
                navController = navHostController,
                state = state,
                onCreateIntent = { intent ->
                    viewModel.processIntent(intent)
                }
            )
        }

        composable(Screen.Detail.route) {
            val argument =
                navHostController.previousBackStackEntry?.savedStateHandle?.get<ReqresUserParcel>(
                    DETAIL_PERSON_ARGUMENT_KEY
                )

            argument?.let {
                DetailPersonScreen(
                    context = context,
                    person = argument,
                    navController = navHostController
                )
            }


        }

        composable(Screen.Settings.route) {
            val viewModel = koinViewModel<SettingsViewModel>()

            SettingsScreen(
                context,
                navController = navHostController,
                onSettingsIntent = { intent ->
                    viewModel.processIntent(intent)
                }
            )
        }
    }
}