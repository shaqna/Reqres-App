package com.maou.reqresapp.presentation.screens.home

import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.maou.reqresapp.presentation.navigation.DETAIL_PERSON_ARGUMENT_KEY
import com.maou.reqresapp.presentation.navigation.Screen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    context: Context,
    navController: NavController,
    state: HomeUiState,
    onIntent: (HomeIntent) -> Unit,
) {


    HomeLayout(
        context = context,
        state = state,
        onCreateNewUserClicked = {
           navController.navigate(Screen.Create.route)
        },
        onSettingsButtonClicked = {
            navController.navigate(Screen.Settings.route)
        },
        onPersonItemClicked = {person ->
            navController.currentBackStackEntry?.savedStateHandle?.set(
                key = DETAIL_PERSON_ARGUMENT_KEY,
                value = person
            )
            navController.navigate(Screen.Detail.route)
        }
    )

    LaunchedEffect(key1 = true) {
        Log.d("MyEffect", "Launch effect happen")
        onIntent(HomeIntent.FetchUsers)
    }

}