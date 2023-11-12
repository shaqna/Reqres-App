package com.maou.reqresapp.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ErrorMessage(
    modifier: Modifier = Modifier,
    errorText: String
) {
    Box {
        Text(
            text = errorText, modifier = modifier
                .fillMaxSize()
                .align(Alignment.Center)
        )
    }
}