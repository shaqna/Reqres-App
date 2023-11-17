package com.maou.reqresapp.presentation.screens.create

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
import com.maou.reqresapp.utils.makeStatusNotification

@Composable
fun CreatePersonScreen(
    context: Context,
    navController: NavController,
    state: CreateUiState,
    onCreateIntent: (CreatePersonIntent) -> Unit,
    modifier: Modifier = Modifier
) {

    var personName by remember { mutableStateOf("") }
    var personJob by remember { mutableStateOf("") }

    CreateNewPersonLayout(
        state = state,
        onBackButtonClicked = {
            navController.popBackStack()
        },
        onPersonNameValueChanged = {
            personName = it
        },
        onPersonJobValueChanged = {
            personJob = it
        },
        onCreateNewPersonButtonClicked = {
            onCreateIntent(
                CreatePersonIntent.CreateNewPerson(
                    personName,
                    personJob
                )
            )
        },
        onCreateSuccess = {
            //show notification
            makeStatusNotification(
                context = context,
                message = "Successfully create new user ${it.name}"
            )
        },
        onCreateFailed = { errMessage ->
            Toast.makeText(context, errMessage, Toast.LENGTH_SHORT).show()
        },
        onCreateLoading = {
            CircularProgressIndicator(
                modifier = modifier.padding(start = 16.dp, end = 16.dp, top = 64.dp)
            )
        },
        newPersonName = personName,
        newPersonJob = personJob
    )

}