package com.maou.reqresapp.domain.di

import com.maou.reqresapp.domain.repository.users.ReqresUsersInteractor
import com.maou.reqresapp.domain.usecase.auth.AuthInteractor
import com.maou.reqresapp.domain.usecase.auth.AuthUseCase
import com.maou.reqresapp.domain.usecase.users.ReqresUsersUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

private val useCaseModule = module {
    singleOf(::AuthInteractor) {
        bind<AuthUseCase>()
    }

    singleOf(::ReqresUsersInteractor) {
        bind<ReqresUsersUseCase>()
    }
}

val domainModule = module {
    includes(useCaseModule)
}



