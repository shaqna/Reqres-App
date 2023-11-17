package com.maou.reqresapp.presentation.screens.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maou.reqresapp.domain.model.NewUser
import com.maou.reqresapp.presentation.components.CommonTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewPersonLayout(
    state: CreateUiState,
    onBackButtonClicked: () -> Unit,
    onPersonNameValueChanged: (String) -> Unit,
    onPersonJobValueChanged: (String) -> Unit,
    onCreateNewPersonButtonClicked: () -> Unit,
    onCreateSuccess: (NewUser) -> Unit,
    onCreateFailed: (String) -> Unit,
    onCreateLoading: @Composable () -> Unit,
    newPersonName: String,
    newPersonJob: String,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Create New Person", fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = onBackButtonClicked) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CommonTextField(
                value = newPersonName,
                labelText = "Name",
                onValueChange = onPersonNameValueChanged,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, start = 16.dp, end = 16.dp)
            )

            CommonTextField(
                value = newPersonJob,
                labelText = "Job",
                onValueChange = onPersonJobValueChanged,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            )


            if (state.isLoading) {
                onCreateLoading()
            } else {
                Button(
                    onClick = onCreateNewPersonButtonClicked,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                ) {
                    Text(text = "Create")
                }
            }

            if (!state.isLoading && state.errMessage.isNotBlank()) {
                onCreateFailed(state.errMessage)
            }

            if (!state.isLoading && state.newUser != null) {
                onCreateSuccess(state.newUser)
            }
        }
    }
}