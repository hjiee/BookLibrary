package com.hyden.booklibrary.data.repository.source

import com.hyden.booklibrary.data.local.db.BookEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface BookDataSource {

    fun insertBook(bookEntity: BookEntity?): Completable
    fun insertBookAll(bookEntities: List<BookEntity?>): Completable

    fun deleteBook(isbn13: String): Completable

    fun updateBook(bookEntity: BookEntity): Completable

    fun getBook(isbn13: String): Single<BookEntity>

    fun getSharedBook() : Flowable<List<BookEntity>>

    fun getAll(): Flowable<List<BookEntity>>

    fun isContains(isbn13: String): Completable
}