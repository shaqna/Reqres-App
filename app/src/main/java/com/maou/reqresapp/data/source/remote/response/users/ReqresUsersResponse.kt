package com.maou.reqresapp.data.source.remote.response.users

import com.squareup.moshi.Json

data class ReqresUsersResponse(
    val id: Int,
    val email: String,
    @field:Json(name = "first_name")
    val firstName: String,
    @field:Json(name = "last_name")
    val lastName: String,
    val avatar: String
)