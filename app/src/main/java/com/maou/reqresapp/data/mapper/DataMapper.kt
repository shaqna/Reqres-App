package com.maou.reqresapp.data.mapper

import com.maou.reqresapp.data.source.remote.response.users.NewUserResponse
import com.maou.reqresapp.data.source.remote.response.users.ReqresUsersResponse
import com.maou.reqresapp.domain.model.NewUser
import com.maou.reqresapp.domain.model.ReqresUser

fun ReqresUsersResponse.toDomainModel(): ReqresUser =
    ReqresUser(
        id, email, firstName, lastName, avatar
    )

fun List<ReqresUsersResponse>.toListDomainModel(): List<ReqresUser> =
    map {
        it.toDomainModel()
    }

fun NewUserResponse.toDomainModel(): NewUser =
    NewUser(name, job, id, createdAt)