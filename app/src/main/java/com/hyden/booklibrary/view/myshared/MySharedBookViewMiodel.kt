package com.hyden.booklibrary.view.myshared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.local.db.convertToBookItems
import com.hyden.booklibrary.data.remote.network.response.BookItem
import com.hyden.booklibrary.data.repository.source.BookDataSource
import com.hyden.util.LogUtil.LogE

class MySharedBookViewMiodel(
    private val bookDataSource: BookDataSource
): BaseViewModel() {

    private val _sharedItems = MutableLiveData<List<BookItem>>()
    val sharedItem : LiveData<List<BookItem>> get() = _sharedItems

    fun loadBook() {
        compositeDisposable.add(
            bookDataSource.getSharedBook()
                .subscribe(
                    {
                        _sharedItems.value = it?.convertToBookItems()
                    },
                    {
                        LogE(it.toString())
                    }
                )
        )
    }
    override fun onCleared() {
        super.onCleared()
    }
}