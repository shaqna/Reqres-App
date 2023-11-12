package com.maou.reqresapp.presentation.screens.auth.register

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    context: Context,
    navController: NavController,
    registerUiState: RegisterUiState,
    onRegisterIntent: (RegisterIntent) -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    RegisterLayout(
        context = context,
        email = email,
        password = password,
        onEmailValueChange = {
            email = it
        },
        onPasswordValueChange = {
            password = it
        },
        onSignUpButtonClick = {
            onRegisterIntent(RegisterIntent.OnRegister(email,password))
        },
        onSignInTextClick = {
            navController.popBackStack()
        },
        state = registerUiState,
        modifier = modifier
    )
}