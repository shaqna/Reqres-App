package com.maou.reqresapp.presentation.screens.home

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.maou.reqresapp.R
import com.maou.reqresapp.domain.model.ReqresUser
import com.maou.reqresapp.presentation.components.PersonItem
import com.maou.reqresapp.presentation.mapper.toParcel
import com.maou.reqresapp.ui.theme.RoyalBlue

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeLayout(
    context: Context,
    state: HomeUiState,
    onCreateNewUserClicked: () -> Unit,
    onSettingsButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Reqres App", color = Color.White)
                },
                actions = {
                    IconButton(onClick = onSettingsButtonClicked ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_settings_24),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = RoyalBlue)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateNewUserClicked,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_person_add_alt_24),
                    contentDescription = null
                )
            }
        }
    ) { innerPadding ->
        if (state.isLoading) {
            Box {
                CircularProgressIndicator(
                    modifier = modifier.align(Alignment.Center)
                )
            }
        }

        if (!state.isLoading && state.errMessage.isNotBlank()) {
            Box {
                Text(
                    text = state.errMessage,
                    modifier = modifier.align(Alignment.Center)
                )
            }
        }

        if (!state.isLoading && state.users.isNotEmpty()) {
            LazyColumn(
                modifier = modifier.padding(innerPadding)
            ) {
                items(items = state.users) { users ->
                    PersonItem(person = users.toParcel(),
                        onClick = {

                        }
                    )
                }
            }
        }
    }


}