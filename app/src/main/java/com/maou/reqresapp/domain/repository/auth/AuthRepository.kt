package com.maou.reqresapp.domain.repository.auth


import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(email: String, password: String): Flow<Result<String>>
    fun register(email: String, password: String): Flow<Result<String>>
    fun getAuthToken(): String?
    fun deleteAuthToken()
}