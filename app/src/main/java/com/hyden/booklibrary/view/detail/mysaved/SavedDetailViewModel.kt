package com.hyden.booklibrary.view.detail.mysaved

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.local.db.convertToBookItem
import com.hyden.booklibrary.data.remote.network.response.BookItem
import com.hyden.booklibrary.data.remote.network.response.convertToBookEntity
import com.hyden.booklibrary.data.repository.source.FirebaseDataSource
import com.hyden.booklibrary.data.repository.source.BookDataSource
import com.hyden.util.LogUtil.LogD
import com.hyden.util.LogUtil.LogE

class SavedDetailViewModel(
    private val bookDataSource: BookDataSource,
    private val firebaseDataSource: FirebaseDataSource
) : BaseViewModel() {

    private val _detailInfo = MutableLiveData<BookItem>()
    val detailInfo: LiveData<BookItem> get() = _detailInfo

    private val _isContain = MutableLiveData<Boolean>()
    val isContain: LiveData<Boolean> get() = _isContain

    private val _isDelete = MutableLiveData<Boolean>()
    val isDelete: LiveData<Boolean> get() = _isDelete

    fun bookInfo(bookInfo: BookItem?) {
        _detailInfo.value = bookInfo
        LogD("데이터 저장")
    }

    fun bookReLoad(isbn13: String) {
        compositeDisposable.add(
            bookDataSource.getBook(isbn13)
                .subscribe(
                    { _detailInfo.value = it.convertToBookItem() },
                    { LogE("$it") }
                )
        )
    }

    fun deleteBook(isbn13 : String) {
        compositeDisposable.add(
            bookDataSource.deleteBook(isbn13)
                .subscribe(
                    { _isDelete.value = true },
                    {
                        _isDelete.value = false
                        LogE("$it")
                    }
                )
        )
    }

    fun bookUpdate(bookItem: BookItem) {
        compositeDisposable.add(
            bookDataSource.updateBook(bookEntity = bookItem.convertToBookEntity())
                .subscribe({},{ LogE("$it") })
        )
    }

    fun pushLike(isSelected: Boolean, bookItem: BookItem) {
        val documentId = firebaseDataSource.getLoginEmail() + "-" + bookItem.isbn13
        firebaseDataSource.pushLike(
            isSelected = isSelected,
            documentId = documentId
        )
    }

    fun pushShare(bookItem: BookItem) {
        firebaseDataSource.pushShare(bookItem.convertToBookEntity())
    }

    fun pushDelete(isbn13: String) {
        firebaseDataSource.deleteBook(isbn13)
    }
}




