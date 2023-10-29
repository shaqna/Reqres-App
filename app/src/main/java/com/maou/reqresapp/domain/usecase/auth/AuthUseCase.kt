package com.maou.reqresapp.domain.usecase.auth

import kotlinx.coroutines.flow.Flow

interface AuthUseCase {

    fun login(email: String, password: String): Flow<Result<String>>
    fun register(email: String, password: String): Flow<Result<String>>
    fun getToken(): String?
    fun deleteToken()
}