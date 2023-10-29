package com.maou.reqresapp.data.di

import android.content.Context
import com.maou.reqresapp.BuildConfig
import com.maou.reqresapp.data.repository.auth.AuthRepositoryImpl
import com.maou.reqresapp.data.repository.users.ReqresUsersRepositoryImpl
import com.maou.reqresapp.data.source.local.AppSharedPrefs
import com.maou.reqresapp.data.source.remote.service.ApiService
import com.maou.reqresapp.domain.repository.auth.AuthRepository
import com.maou.reqresapp.domain.repository.users.ReqresUsersRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private val retrofit = module {
    single {
        val retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            ).build()

        retrofit.create(ApiService::class.java)
    }
}

private val sharedPrefsModule = module {
    single {
        androidApplication().getSharedPreferences(AppSharedPrefs.APP_SHARED_PREFS, Context.MODE_PRIVATE)
    }
}

private val appPrefsModule = module {
    singleOf(::AppSharedPrefs)
}

private val repositoryModule = module {
    singleOf(::AuthRepositoryImpl) {
        bind<AuthRepository>()
    }

    singleOf(::ReqresUsersRepositoryImpl) {
        bind<ReqresUsersRepository>()
    }
}

val dataModule = module {
    includes(retrofit, sharedPrefsModule, appPrefsModule, repositoryModule)
}