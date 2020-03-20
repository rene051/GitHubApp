package com.github.app

import android.app.Application
import com.github.app.di.AppModule
import com.github.app.di.NetModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin

class App : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(listOf(AppModule, NetModule))
        }
    }
}