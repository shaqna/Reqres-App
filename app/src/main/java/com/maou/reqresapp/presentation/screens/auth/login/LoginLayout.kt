package com.maou.reqresapp.presentation.screens.auth.login

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maou.reqresapp.R
import com.maou.reqresapp.presentation.components.EmailField
import com.maou.reqresapp.presentation.components.PasswordField
import com.maou.reqresapp.ui.theme.interFontFamily

@Composable
fun LoginLayout(
    context: Context,
    email: String,
    onEmailValueChange: (String) -> Unit,
    password: String,
    onPasswordValueChange: (String) -> Unit,
    onSignInButtonClicked: () -> Unit,
    onSignUpTextClicked: () -> Unit,
    onSuccessIntent: () -> Unit,
    onFailedIntent: (String) -> Unit,
    onLoading: @Composable () -> Unit,
    state: LoginUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = modifier.padding(top = 16.dp),
            text = "Login",
            fontSize = 30.sp,
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Medium
        )

        EmailField(
            email = email,
            onValueChange = onEmailValueChange,
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 32.dp)

        )

        PasswordField(
            password = password,
            onChangeValue = onPasswordValueChange,
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        )

        if (!state.isLoading) {
            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 64.dp),
                onClick = onSignInButtonClicked
            ) {
                Text(text = "Login", fontSize = 16.sp)
            }
        } else {
            onLoading()
        }

        if (state.errMessage.isNotBlank()) {
            onFailedIntent(state.errMessage)
        }

        if(state.token.isNotBlank()) {
            onSuccessIntent()
        }




        ClickableText(
            modifier = modifier.padding(top = 8.dp),
            text = AnnotatedString(stringResource(id = R.string.belum_punya_akun_daftar)),
            onClick = {
                onSignUpTextClicked()
            }
        )
    }
}