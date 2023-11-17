package com.maou.reqresapp.presentation.screens.auth.register

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
fun RegisterLayout(
    context: Context,
    email: String,
    password: String,
    onEmailValueChange: (String) -> Unit,
    onPasswordValueChange: (String) -> Unit,
    onSignUpButtonClick: () -> Unit,
    onSignInTextClick: () -> Unit,
    state: RegisterUiState,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = modifier.padding(top = 16.dp),
            text = "Register",
            fontSize = 30.sp,
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Medium
        )

        EmailField(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 16.dp, end = 16.dp),
            email = email,
            onValueChange = onEmailValueChange
        )

        PasswordField(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            password = password,
            onChangeValue = onPasswordValueChange
        )

        if (!state.isLoading) {
            Button(
                onClick =  onSignUpButtonClick,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 64.dp)
            ) {
                Text(text = "Register")
            }
        } else {
            CircularProgressIndicator(
                modifier = modifier.padding(start = 16.dp, end = 16.dp, top = 64.dp)
            )
        }

        if (!state.isLoading && state.errMessage.isNotBlank()) {
            Toast.makeText(context, state.errMessage, Toast.LENGTH_SHORT).show()
        }

        ClickableText(
            modifier = modifier.padding(top = 10.dp),
            text = AnnotatedString(stringResource(id = R.string.sudah_punya_akun_daftar)),
            onClick = {
                onSignInTextClick()
            }
        )
    }
}