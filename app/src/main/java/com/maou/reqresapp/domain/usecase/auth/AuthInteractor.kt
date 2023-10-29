package com.maou.reqresapp.domain.usecase.auth

import com.maou.reqresapp.domain.repository.auth.AuthRepository
import kotlinx.coroutines.flow.Flow

class AuthInteractor(
    private val repository: AuthRepository
): AuthUseCase {
    override fun login(email: String, password: String): Flow<Result<String>> =
        repository.login(email, password)

    override fun register(email: String, password: String): Flow<Result<String>> =
        repository.register(email, password)

    override fun getToken(): String? = repository.getAuthToken()

    override fun deleteToken() = repository.deleteAuthToken()
}