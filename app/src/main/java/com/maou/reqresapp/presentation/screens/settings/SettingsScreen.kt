package com.maou.reqresapp.presentation.screens.settings

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.maou.reqresapp.R
import com.maou.reqresapp.presentation.components.LogoutDialog
import com.maou.reqresapp.presentation.navigation.AUTH_GRAPH_ROUTE
import com.maou.reqresapp.presentation.navigation.HOME_GRAPH_ROUTE

@Composable
fun SettingsScreen(
    context: Context,
    navController: NavController,
    onSettingsIntent: (SettingsIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    val openLogoutDialog = remember {
        mutableStateOf(false)
    }
    SettingsLayout(
        onShowLogoutDialog = {
            openLogoutDialog.value = it
        },
        onBackButtonClicked = {
            navController.popBackStack()
        },
        onLogoutConfirmationButtonCLicked = {
            onSettingsIntent(SettingsIntent.LogoutIntent)
            navController.navigate(AUTH_GRAPH_ROUTE) {
                popUpTo(HOME_GRAPH_ROUTE) {
                    inclusive = true
                }
            }
        },
        showDialog = openLogoutDialog.value
    )
}