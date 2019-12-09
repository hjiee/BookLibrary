package com.hyden.booklibrary.di

import com.hyden.booklibrary.BuildConfig
import com.hyden.booklibrary.data.remote.BookApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val connectTime = 30L
private const val readTime = 30L
private const val writeTime = 30L

val networkModule = module {

    single {
        OkHttpClient.Builder()
            .connectTimeout(connectTime, TimeUnit.SECONDS)
            .readTimeout(readTime, TimeUnit.MINUTES)
            .writeTimeout(writeTime, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            }).build()
    }

    single<Converter.Factory> {
        GsonConverterFactory.create()
    }

    single<CallAdapter.Factory> {
        RxJava2CallAdapterFactory.create()
    }

    single {
        Retrofit
            .Builder()
            .client(get())
            .baseUrl(BuildConfig.BASEURL)
            .addConverterFactory(get())
            .addCallAdapterFactory(get())
            .build()
            .create(BookApi::class.java)

    }
}
