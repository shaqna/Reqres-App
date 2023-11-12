package com.maou.reqresapp.presentation.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.maou.reqresapp.presentation.model.ReqresUserParcel

@Composable
fun ListPerson(
    modifier: Modifier = Modifier,
    persons: List<ReqresUserParcel>,
    onItemClick: (ReqresUserParcel) -> Unit
) {
    LazyColumn {
        items(items = persons) { person ->
            PersonItem(person = person,
                onClick = {
                    onItemClick(person)
                }
            )
        }
    }
}