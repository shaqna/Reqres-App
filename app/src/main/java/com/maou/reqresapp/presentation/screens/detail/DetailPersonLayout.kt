package com.maou.reqresapp.presentation.screens.detail

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.maou.reqresapp.presentation.model.ReqresUserParcel
import com.maou.reqresapp.ui.theme.interFontFamily
import com.maou.reqresapp.utils.generateFullName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPersonLayout(
    context: Context,
    person: ReqresUserParcel,
    onBackButtonClicked: () -> Unit,
    appBarTitle: String,
    modifier: Modifier = Modifier
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = appBarTitle)
                },
                navigationIcon = {
                    IconButton(onClick = onBackButtonClicked) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = person.avatar,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier.size(width = 120.dp, height = 120.dp).clip(CircleShape)
            )

            Text(
                text = generateFullName(person.firstName, person.lastName),
                fontFamily = interFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = modifier.padding(top = 24.dp)
            )

            Text(
                text = person.email,
                fontFamily = interFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = modifier.padding(top = 24.dp)
            )
        }
    }
}