package com.hyden.booklibrary.view.detail.mysaved

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.repository.source.FirebaseDataSource
import com.hyden.booklibrary.data.repository.source.BookDataSource
import com.hyden.util.LogUtil.LogD
import com.hyden.util.LogUtil.LogE

class SavedDetailViewModel(
    private val bookDatasource: BookDataSource,
    private val firebaseDataSource: FirebaseDataSource
) : BaseViewModel() {

    private val _detailInfo = MutableLiveData<BookEntity>()
    val detailInfo: LiveData<BookEntity> get() = _detailInfo

    private val _isContain = MutableLiveData<Boolean>()
    val isContain: LiveData<Boolean> get() = _isContain

    private val _isDelete = MutableLiveData<Boolean>()
    val isDelete: LiveData<Boolean> get() = _isDelete

    fun bookInfo(bookInfo: BookEntity?) {
        _detailInfo.value = bookInfo
        LogD("데이터 저장")
    }

    fun bookReLoad(isbn13: String) {
        compositeDisposable.add(
            bookDatasource.getBook(
                isbn13 = isbn13,
                success = {
                    _detailInfo.value = it
                },
                failure = {
                    LogE("ERROR : $it")
                })
        )
    }

    fun bookInsert() {
        compositeDisposable.add(
            bookDatasource.insert(
                bookEntity = _detailInfo.value,
                success = {
                    _isContain.value = true
                    LogD("SUCCESS")
                },
                failure = {
                    _isContain.value = false
                    LogE("ERROR : $it")
                }
            )
        )
    }

    fun deleteBook(
        isbn13: String
    ) {
        compositeDisposable.add(
            bookDatasource.deleteBook(
                isbn13 = isbn13,
                success = {
                    _isDelete.value = true
                },
                failure = {
                    _isDelete.value = false
                    LogE("ERROR : $it")
                }
            )
        )
    }

    fun bookUpdate(bookEntity: BookEntity) {
        compositeDisposable.add(bookDatasource.updateBook(bookEntity = bookEntity))
    }

    fun pushLike(isSelected: Boolean, bookEntity: BookEntity) {
        val documentId = firebaseDataSource.getLoginEmail() + "-" + bookEntity.isbn13
        firebaseDataSource.pushLike(
            isSelected = isSelected,
            documentId = documentId
        )
    }

    fun pushShare(bookEntity: BookEntity) {
        firebaseDataSource.pushShare(bookEntity)
    }

    fun pushDelete(isbn13: String) {
        firebaseDataSource.deleteBook(isbn13)
    }

    fun isSharedUser() {
    }

    fun isBookContains(
        isbn13: String
    ) {
        compositeDisposable.add(
            bookDatasource.isContains(
                isbn13 = isbn13,
                success = {
                    _isContain.value = it
                },
                failure = {
                    _isContain.value = it
                }
            )
        )
    }
}




