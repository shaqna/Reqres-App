package com.maou.reqresapp.data.source.remote.service

import com.maou.reqresapp.data.source.remote.request.login.LoginRequest
import com.maou.reqresapp.data.source.remote.request.register.RegisterRequest
import com.maou.reqresapp.data.source.remote.request.users.NewUserRequest
import com.maou.reqresapp.data.source.remote.response.base.BaseResponse
import com.maou.reqresapp.data.source.remote.response.login.LoginResponse
import com.maou.reqresapp.data.source.remote.response.register.RegisterResponse
import com.maou.reqresapp.data.source.remote.response.users.NewUserResponse
import com.maou.reqresapp.data.source.remote.response.users.ReqresUsersResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("api/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @POST("api/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse

    @GET("api/users?per_page=12")
    suspend fun getUsers(): BaseResponse<List<ReqresUsersResponse>>

    @POST("api/users")
    suspend fun createUser(
        @Body newUserRequest: NewUserRequest
    ): NewUserResponse
}