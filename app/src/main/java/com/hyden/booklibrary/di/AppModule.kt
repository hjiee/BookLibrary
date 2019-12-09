package com.hyden.booklibrary.di

import com.hyden.booklibrary.data.repository.HomeRepository
import com.hyden.booklibrary.data.repository.RoomRepository
import com.hyden.booklibrary.data.repository.SearchRepository
import org.koin.dsl.module

val appModule = module {
    single { HomeRepository(get()) }
    single { SearchRepository(get()) }
    single { RoomRepository(get()) }
}