package com.hyden.booklibrary.view.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.local.db.convertToBookItems
import com.hyden.booklibrary.data.remote.network.response.BookItem
import com.hyden.booklibrary.data.repository.source.BookDataSource
import com.hyden.util.LogUtil.LogE

class LibraryViewModel(
    private val bookDataSource: BookDataSource
) : BaseViewModel() {

    private val _bookData = MutableLiveData<List<BookItem>>()
    val bookData : LiveData<List<BookItem>> get() = _bookData

    fun loadBook() {
        compositeDisposable.add(
            bookDataSource.getAll()
                .subscribe(
                    { _bookData.value = it.convertToBookItems() },
                    { LogE("$it")}
                )
        )
    }

    fun deleteBook(isbn13 : String) {
        compositeDisposable.add(
            bookDataSource.deleteBook(isbn13)
                .subscribe(
                    { loadBook() },
                    { LogE("$it") }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
    }
}