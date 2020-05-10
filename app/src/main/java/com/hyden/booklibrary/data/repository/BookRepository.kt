package com.hyden.booklibrary.data.repository

import com.hyden.booklibrary.data.local.db.BookDao
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.repository.source.BookDataSource
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BookRepository(
    private val bookDao: BookDao
) : BookDataSource {


    override fun insertBook(bookEntity: BookEntity?): Completable {
        return bookDao.insertBook(listOf(bookEntity))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    override fun deleteBook(isbn13: String): Completable {
        return bookDao.deleteBook(isbn13)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun updateBook(bookEntity: BookEntity): Completable {
        return bookDao.updateBook(bookEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getBook(isbn13: String): Single<BookEntity> {
        return Single.create<BookEntity> { emitter ->
            emitter.onSuccess(bookDao.getBook(isbn13))
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getSharedBook(): Flowable<List<BookEntity>> {
        return bookDao.getSharedBook()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getAll(): Flowable<List<BookEntity>> {
        return Flowable.create<List<BookEntity>>({ emitter ->
            emitter.onNext(bookDao.getAll())
        }, BackpressureStrategy.BUFFER)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun isContains(isbn13: String): Completable {
        return Single.create<Int> { emitter ->
            emitter.onSuccess(bookDao.isContains(isbn13))
        }.flatMapCompletable {
            when (it) {
                0 -> Completable.error(Throwable())
                else -> Completable.complete()
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
