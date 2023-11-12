package com.maou.reqresapp.presentation.screens.auth.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.maou.reqresapp.presentation.navigation.Screen

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    context: Context,
    navController: NavController,
    loginUiState: LoginUiState,
    onLoginIntent: (LoginIntent) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LoginLayout(
        modifier = modifier,
        context = context,
        email = email,
        password = password,
        onEmailValueChange = {
            email = it
        },
        onPasswordValueChange = {
            password = it
        },
        onSignInButtonClicked = {
            onLoginIntent(LoginIntent.OnLogin(email, password))
        },
        onSignUpTextClicked = {
          navController.navigate(Screen.Register.name)
        },
        onSuccessIntent = {
            navController.navigate(Screen.Home.name) {
                popUpTo(Screen.Login.name) { inclusive  = true }
            }
        },
        onFailedIntent = { errMessage ->
            Toast.makeText(context, errMessage, Toast.LENGTH_SHORT).show()
        },
        onLoading = {
            CircularProgressIndicator(
                modifier = modifier
                    .padding(start = 16.dp, end = 16.dp, top = 64.dp)
            )
        },
        state = loginUiState
    )
}

