package com.hyden.booklibrary.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.hyden.booklibrary.data.local.db.BookDataBase
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.util.ConstUtil.Companion.DATABASENAME
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val roomModule = module {
    single { Room.databaseBuilder(androidContext(),BookDataBase::class.java,"${DATABASENAME}.db").build() }
    single { get<BookDataBase>().getBookDao() }
}