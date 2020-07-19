package com.hyden.booklibrary

import com.google.android.gms.ads.MobileAds
import com.hyden.base.BaseApplication
import com.hyden.booklibrary.di.appModule
import com.hyden.booklibrary.di.networkModule
import com.hyden.booklibrary.di.roomModule
import com.hyden.booklibrary.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HomeApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(applicationContext) { }
        startKoin {
            androidContext(this@HomeApplication)
            modules(listOf(networkModule, viewModelModule, appModule, roomModule))
        }
    }
}