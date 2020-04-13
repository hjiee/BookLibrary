package com.hyden.booklibrary.data.local.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migrations {

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                """CREATE TABLE sharedbook (isLiked INTEGER NOT null DEFAULT 0,
                            isShared INTEGER NOT null DEFAULT 0 ,
                            isReviews INTEGER NOT null DEFAULT 0 ,
                            bookNote TEXT DEFAULT null,
                            bookReviews TEXT DEFAULT null,
                            title TEXT DEFAULT null,
                            link TEXT DEFAULT null,
                            author TEXT DEFAULT null,
                            pubDate TEXT DEFAULT null,
                            description TEXT DEFAULT null,
                            isbn TEXT DEFAULT null,
                            isbn13 TEXT NOT null PRIMARY KEY,
                            itemId TEXT DEFAULT null,
                            priceSales TEXT DEFAULT null,
                            priceStandard TEXT DEFAULT null,
                            mallType TEXT DEFAULT null,
                            stockStatus TEXT DEFAULT null,
                            mileage TEXT DEFAULT null,
                            cover TEXT DEFAULT null,
                            categoryId TEXT DEFAULT null,
                            categoryName TEXT DEFAULT null,
                            publisher TEXT DEFAULT null,
                            salesPoint TEXT DEFAULT null,
                            adult TEXT DEFAULT null,
                            fixedPrice TEXT DEFAULT null,
                            customerReviewRank TEXT DEFAULT null,
                            bestRank TEXT DEFAULT null)"""
            )
        }
    }
}