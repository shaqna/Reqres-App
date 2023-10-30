package com.maou.reqresapp

import android.app.Application
import com.maou.reqresapp.data.di.dataModule
import com.maou.reqresapp.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            androidLogger(Level.ERROR)
            loadKoinModules(
                listOf(
                    dataModule,
                    domainModule
                )
            )
        }
    }
}