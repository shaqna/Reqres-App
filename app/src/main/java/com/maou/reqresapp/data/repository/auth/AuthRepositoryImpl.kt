package com.maou.reqresapp.data.repository.auth

import com.maou.reqresapp.data.source.local.AppSharedPrefs
import com.maou.reqresapp.data.source.remote.request.login.LoginRequest
import com.maou.reqresapp.data.source.remote.request.register.RegisterRequest
import com.maou.reqresapp.data.source.remote.service.ApiService
import com.maou.reqresapp.domain.repository.auth.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthRepositoryImpl(
    private val apiService: ApiService,
    private val sharedPrefs: AppSharedPrefs
) : AuthRepository {
    override fun login(email: String, password: String): Flow<Result<String>> =
        flow {
            val loginRequest = LoginRequest(email, password)
            val response = apiService.login(loginRequest)

            sharedPrefs.saveToken(response.token)

            emit(Result.success(response.token))

        }.catch {
            emit(Result.failure(it))
        }.flowOn(Dispatchers.IO)

    override fun register(email: String, password: String): Flow<Result<String>> =
        flow {
            val registerRequest = RegisterRequest(email, password)
            val response = apiService.register(registerRequest)

            emit(Result.success(response.token))
        }.catch {
            emit(Result.failure(it))
        }.flowOn(Dispatchers.IO)

    override fun getAuthToken(): String? {
        return sharedPrefs.fetchToken()
    }

    override fun deleteAuthToken() {
        sharedPrefs.deleteToken()
    }
}