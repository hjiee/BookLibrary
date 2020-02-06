package com.hyden.booklibrary.view.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.repository.RoomRepository
import com.hyden.booklibrary.data.repository.source.RoomDataSource
import com.hyden.util.LogUtil.LogE

class LibraryViewModel(
    private val roomDataSource: RoomDataSource
) : BaseViewModel() {

    private val _bookData = MutableLiveData<List<BookEntity>>()
    val bookData : LiveData<List<BookEntity>> get() = _bookData

    fun loadBook() {
        compositeDisposable.add(
            roomDataSource.getAll(
                success = { _bookData.value = it },
                failure = { LogE("ERROR : $it") }
            )
        )
    }

    fun deleteBook(
        isbn13 : String
    ) {
        compositeDisposable.add(
            roomDataSource.deleteBook(
                isbn13 = isbn13,
                success = { loadBook() },
                failure = { LogE("ERROR : $it") }
            )
        )
    }
}