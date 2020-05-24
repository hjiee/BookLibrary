package com.hyden.booklibrary.di

import com.hyden.booklibrary.R
import com.hyden.booklibrary.data.repository.*
import com.hyden.booklibrary.data.repository.source.FirebaseDataSource
import com.hyden.booklibrary.data.repository.source.AladinDataSource
import com.hyden.booklibrary.data.repository.source.BookDataSource
import com.hyden.booklibrary.view.common.FirebaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { AladinRepository(get()) }
    single<BookDataSource> { BookRepository(get()) }
    single<FirebaseDataSource> { FirebaseRepository(androidContext().getString(R.string.default_web_client_id),androidContext()) }
    single { FirebaseModule(get()) }

}