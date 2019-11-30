package com.hyden.booklibrary.di

import com.hyden.booklibrary.data.repository.HomeRepository
import org.koin.dsl.module

val appModule = module {
    single { HomeRepository(get()) }
}