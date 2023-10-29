package com.maou.reqresapp.domain.repository.users

import com.maou.reqresapp.domain.model.NewUser
import com.maou.reqresapp.domain.model.ReqresUser
import kotlinx.coroutines.flow.Flow

interface ReqresUsersRepository {
    fun getUsers(): Flow<Result<List<ReqresUser>>>
    fun createNewUser(name: String, job: String): Flow<Result<NewUser>>
}