package com.hyden.booklibrary.data.repository.source

import com.hyden.booklibrary.data.local.db.BookEntity
import io.reactivex.disposables.Disposable

interface RoomDataSource {

    fun insert(
        bookEntity: BookEntity?,
        success: () -> Unit,
        failure: (String) -> Unit
    ): Disposable



    fun deleteBook(
        isbn13: String,
        success: () -> Unit,
        failure: (String) -> Unit
    ): Disposable

    fun updateBook(
        bookEntity: BookEntity?,
        success: () -> Unit,
        failure: (String) -> Unit
    ): Disposable

    fun getBook(
        isbn13: String,
        success: (BookEntity?) -> Unit,
        failure: (String) -> Unit
    ): Disposable

    fun getAll(
        success: (List<BookEntity>) -> Unit,
        failure: (String) -> Unit
    ): Disposable

    fun isContains(
        isbn13: String,
        success: (Boolean) -> Unit,
        failure: (Boolean) -> Unit
    ): Disposable
}