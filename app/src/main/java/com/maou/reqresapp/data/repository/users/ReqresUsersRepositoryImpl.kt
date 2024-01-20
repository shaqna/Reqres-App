package com.maou.reqresapp.data.repository.users

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.maou.reqresapp.data.mapper.toDomainModel
import com.maou.reqresapp.data.mapper.toListDomainModel
import com.maou.reqresapp.data.paging.UsersPagingSource
import com.maou.reqresapp.data.source.remote.request.users.NewUserRequest
import com.maou.reqresapp.data.source.remote.service.ApiService
import com.maou.reqresapp.domain.model.NewUser
import com.maou.reqresapp.domain.model.ReqresUser
import com.maou.reqresapp.domain.repository.users.ReqresUsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ReqresUsersRepositoryImpl(
    private val apiService: ApiService
): ReqresUsersRepository {
    override fun getUsers(): Flow<PagingData<ReqresUser>> =
        Pager(
            config = PagingConfig(pageSize = 2),
            pagingSourceFactory = {
                UsersPagingSource(apiService)
            }
        ).flow
    override fun createNewUser(name: String, job: String): Flow<Result<NewUser>> =
        flow {
            val request = NewUserRequest(name, job)
            val response = apiService.createUser(request)

            emit(Result.success(response.toDomainModel()))
        }.catch {
            emit(Result.failure(it))
        }.flowOn(Dispatchers.IO)
}