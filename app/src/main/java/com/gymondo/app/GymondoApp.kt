package com.gymondo.app

import android.app.Application
import com.gymondo.app.domain.di.domainModule
import com.gymondo.app.remote.di.remoteModule
import com.gymondo.data.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GymondoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin() {
            androidContext(this@GymondoApp)
            androidLogger()
            modules(listOf(remoteModule, dataModule, domainModule))
        }

    }
}