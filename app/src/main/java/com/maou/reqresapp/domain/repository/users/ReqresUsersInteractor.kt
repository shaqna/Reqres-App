package com.maou.reqresapp.domain.repository.users

import androidx.paging.PagingData
import com.maou.reqresapp.domain.model.NewUser
import com.maou.reqresapp.domain.model.ReqresUser
import com.maou.reqresapp.domain.usecase.users.ReqresUsersUseCase
import kotlinx.coroutines.flow.Flow

class ReqresUsersInteractor(
    private val repository: ReqresUsersRepository
): ReqresUsersUseCase {
    override fun getUsers(): Flow<PagingData<ReqresUser>> = repository.getUsers()

    override fun createUser(name: String, job: String): Flow<Result<NewUser>> =
        repository.createNewUser(name, job)
}