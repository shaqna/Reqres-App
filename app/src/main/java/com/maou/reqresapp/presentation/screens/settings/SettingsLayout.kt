package com.maou.reqresapp.presentation.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.maou.reqresapp.R
import com.maou.reqresapp.presentation.components.LogoutDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsLayout(
    onShowLogoutDialog: (Boolean) -> Unit,
    onLogoutConfirmationButtonCLicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
    showDialog: Boolean,
    modifier: Modifier = Modifier
) {

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Settings")
            },
                navigationIcon = {
                    IconButton(onClick = onBackButtonClicked) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                })
        }
    ) { innerPadding ->

        if (showDialog) {
            LogoutDialog(
                onDismissRequest = {
                    onShowLogoutDialog(false)
                },
                onConfirmation = {
                    onShowLogoutDialog(false)
                    onLogoutConfirmationButtonCLicked()
                },
                dialogTitle = stringResource(id = R.string.keluar_dari_aplikasi),
                dialogText = stringResource(id = R.string.logout_question)
            )
        }

        Column(
            modifier = modifier.padding(innerPadding)
        ) {
            Row(
                modifier = modifier
                    .padding(top = 32.dp, start = 16.dp, end = 16.dp)
                    .clickable {
                        onShowLogoutDialog(true)
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_logout_24),
                    contentDescription = null,
                    tint = Color.Red
                )

                Text(
                    text = "Log Out",
                    modifier = modifier.padding(start = 16.dp),
                    color = Color.Red
                )
            }

            Divider(modifier = modifier.padding(top = 16.dp), thickness = 1.dp)
        }
    }
}