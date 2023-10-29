package com.maou.reqresapp.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReqresUserParcel(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String
): Parcelable
