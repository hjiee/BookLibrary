package com.hyden.booklibrary.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.remote.network.response.BookItem
import com.hyden.booklibrary.data.remote.network.response.convertToBookEntity
import com.hyden.booklibrary.data.repository.source.BookDataSource
import com.hyden.util.LogUtil.LogE

class UnSavedDetailViewModel(
    private val bookDataSource: BookDataSource
) : BaseViewModel() {

    private val _detailInfo = MutableLiveData<BookItem>()
    val detailInfo: LiveData<BookItem> get() = _detailInfo

    private val _isContain = MutableLiveData<Boolean>()
    val isContain : LiveData<Boolean> get() = _isContain

    private val _isDelete = MutableLiveData<Boolean>()
    val isDelete : LiveData<Boolean> get() = _isDelete

    fun bookInfo(bookInfo: BookItem?) {
        _detailInfo.value = bookInfo
    }

    fun bookInsert() {
        compositeDisposable.add(
            bookDataSource.insertBook(_detailInfo.value?.convertToBookEntity())
                .subscribe(
                    { _isContain.value = true },
                    {
                        _isContain.value = false
                        LogE("$it")
                    }
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
}