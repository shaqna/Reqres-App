package com.maou.reqresapp.presentation.screens.home

import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    context: Context,
    navController: NavController,
    state: HomeUiState,
    onIntent: (HomeIntent) -> Unit,
) {

    LaunchedEffect(key1 = true) {
        onIntent(HomeIntent.FetchUsers)
    }

    HomeLayout(
        context = context,
        state = state,
        onCreateNewUserClicked = {

        },
        onSettingsButtonClicked = {

        }
    )

}