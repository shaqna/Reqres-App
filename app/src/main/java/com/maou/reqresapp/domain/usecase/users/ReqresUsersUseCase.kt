package com.maou.reqresapp.domain.usecase.users

import androidx.paging.PagingData
import com.maou.reqresapp.domain.model.NewUser
import com.maou.reqresapp.domain.model.ReqresUser
import kotlinx.coroutines.flow.Flow

interface ReqresUsersUseCase {
    fun getUsers(): Flow<PagingData<ReqresUser>>
    fun createUser(name: String, job: String): Flow<Result<NewUser>>
}