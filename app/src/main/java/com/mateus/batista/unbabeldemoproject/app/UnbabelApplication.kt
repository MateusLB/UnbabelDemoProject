package com.mateus.batista.unbabeldemoproject.app

import android.app.Application
import com.mateus.batista.unbabeldemoproject.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class UnbabelApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@UnbabelApplication)
            modules(listOf(dataModule, remoteModule, serviceModule, cacheModule, databaseModule, domainModule, viewModelModule))
        }
    }
}