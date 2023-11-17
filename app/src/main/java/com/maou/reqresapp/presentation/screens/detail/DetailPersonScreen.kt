package com.maou.reqresapp.presentation.screens.detail

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.maou.reqresapp.presentation.model.ReqresUserParcel
import com.maou.reqresapp.utils.generateFullName

@Composable
fun DetailPersonScreen(
    context: Context,
    person: ReqresUserParcel,
    navController: NavController,
    modifier: Modifier = Modifier
) {

    DetailPersonLayout(
        context = context,
        person = person,
        onBackButtonClicked = {
            navController.popBackStack()
        },
        appBarTitle = generateFullName(person.firstName, person.lastName)
    )
}