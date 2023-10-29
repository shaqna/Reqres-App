package com.maou.reqresapp.presentation.mapper

import com.maou.reqresapp.domain.model.ReqresUser
import com.maou.reqresapp.presentation.model.ReqresUserParcel

fun ReqresUser.toParcel(): ReqresUserParcel =
    ReqresUserParcel(id, email, firstName, lastName, avatar)