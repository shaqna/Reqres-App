package com.maou.reqresapp.domain.repository.users

import androidx.paging.PagingData
import com.maou.reqresapp.domain.model.NewUser
import com.maou.reqresapp.domain.model.ReqresUser
import kotlinx.coroutines.flow.Flow

interface ReqresUsersRepository {
    fun getUsers(): Flow<PagingData<ReqresUser>>
    fun createNewUser(name: String, job: String): Flow<Result<NewUser>>
}