package com.newsreader

import android.app.Application
import com.newsreader.di.appModules
import com.newsreader.di.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            androidLogger()
            modules(platformModule(), appModules)
        }
    }
}
