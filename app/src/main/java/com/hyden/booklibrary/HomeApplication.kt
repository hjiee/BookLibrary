package com.hyden.booklibrary

import com.facebook.stetho.Stetho
import com.hyden.base.BaseApplication
import com.hyden.booklibrary.di.appModule
import com.hyden.booklibrary.di.networkModule
import com.hyden.booklibrary.di.roomModule
import com.hyden.booklibrary.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class HomeApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        startKoin {
            androidContext(this@HomeApplication)
            modules(listOf(networkModule, viewModelModule, appModule, roomModule))
        }
    }
}