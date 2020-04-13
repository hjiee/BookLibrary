package com.hyden.booklibrary.di

import androidx.room.Room
import com.hyden.booklibrary.data.local.db.AppDataBase
import com.hyden.booklibrary.data.local.db.migrations.Migrations.MIGRATION_1_2
import com.hyden.booklibrary.util.ConstUtil.Companion.DATABASENAME_BOOK
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val roomModule = module {
    single {
        Room.databaseBuilder(androidContext(),AppDataBase::class.java,"${DATABASENAME_BOOK}.db")
            .addMigrations(MIGRATION_1_2)
            .build()
    }
    single { get<AppDataBase>().getBookDao() }
}