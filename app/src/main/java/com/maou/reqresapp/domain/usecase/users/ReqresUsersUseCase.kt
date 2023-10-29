package com.maou.reqresapp.domain.usecase.users

import com.maou.reqresapp.domain.model.NewUser
import com.maou.reqresapp.domain.model.ReqresUser
import kotlinx.coroutines.flow.Flow

interface ReqresUsersUseCase {
    fun getUsers(): Flow<Result<List<ReqresUser>>>
    fun createUser(name: String, job: String): Flow<Result<NewUser>>
}