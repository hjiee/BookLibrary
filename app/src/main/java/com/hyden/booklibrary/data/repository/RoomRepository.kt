package com.hyden.booklibrary.data.repository

import com.hyden.booklibrary.data.local.db.BookDao
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.repository.source.RoomDataSource
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RoomRepository(
    private val bookDao: BookDao
) : RoomDataSource {


    override fun insert(
        bookEntity: BookEntity?,
        success: () -> Unit,
        failure: (String) -> Unit
    ): Disposable {
        return bookDao.insert(listOf(bookEntity))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { success.invoke() },
                { failure.invoke(it.toString()) }
            )
    }


    override fun deleteBook(
        isbn13: String,
        success: () -> Unit,
        failure: (String) -> Unit
    ): Disposable {
        return bookDao.deleteBook(isbn13)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { success.invoke() },
                { failure.invoke(it.toString()) }
            )
    }

    override fun updateBook(
        bookEntity: BookEntity?,
        success: () -> Unit,
        failure: (String) -> Unit
    ): Disposable {
        return bookDao.updateBook(bookEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { success.invoke() },
                { failure.invoke(it.toString()) }
            )
    }

    override fun getBook(
        isbn13: String,
        success: (BookEntity?) -> Unit,
        failure: (String) -> Unit
    ): Disposable {
        return Single.create<BookEntity> { emitter ->
            emitter.onSuccess(bookDao.getBook(isbn13))
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { success.invoke(it) },
                { failure.invoke(it.toString()) }
            )

    }

    override fun getAll(
        success: (List<BookEntity>) -> Unit,
        failure: (String) -> Unit
    ): Disposable {
        return Observable.create<List<BookEntity>> { emitter ->
            emitter.onNext(bookDao.getAll())
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { success.invoke(it) },
                { failure.invoke(it.toString()) }
            )
    }

    override fun isContains(
        isbn13: String,
        success: (Boolean) -> Unit,
        failure: (Boolean) -> Unit
    ): Disposable {
        return Single.create<Int> { emitter ->
            emitter.onSuccess(bookDao.isContains(isbn13))
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when (it) {
                        0 -> failure.invoke(false)
                        else -> success.invoke(true)
                    }
                },
                {
                    failure.invoke(false)
                }
            )
    }
}
