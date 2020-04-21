package com.hyden.booklibrary.di

import com.hyden.booklibrary.R
import com.hyden.booklibrary.data.repository.FirebaseRepository
import com.hyden.booklibrary.data.repository.HomeRepository
import com.hyden.booklibrary.data.repository.BookRepository
import com.hyden.booklibrary.data.repository.SearchRepository
import com.hyden.booklibrary.data.repository.source.FirebaseDataSource
import com.hyden.booklibrary.data.repository.source.HomeDataSource
import com.hyden.booklibrary.data.repository.source.BookDataSource
import com.hyden.booklibrary.data.repository.source.SearchDataSource
import com.hyden.booklibrary.view.common.FirebaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<HomeDataSource> { HomeRepository(get()) }
    single<SearchDataSource> { SearchRepository(get()) }
    single<BookDataSource> { BookRepository(get()) }
    single<FirebaseDataSource> { FirebaseRepository(androidContext().getString(R.string.default_web_client_id),androidContext()) }
    single { FirebaseModule(get()) }

}