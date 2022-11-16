package com.example.anirecord

import android.app.Application
import com.example.anirecord.di.injectModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AniRecord : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@AniRecord)
            injectModules()
        }
    }
}