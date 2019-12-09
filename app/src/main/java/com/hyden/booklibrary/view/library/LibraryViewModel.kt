package com.hyden.booklibrary.view.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.repository.RoomRepository
import com.hyden.util.LogUtil.LogE

class LibraryViewModel(
    private val roomRepository: RoomRepository
) : BaseViewModel() {

    private val _bookData = MutableLiveData<List<BookEntity>>()
    val bookData : LiveData<List<BookEntity>> get() = _bookData

    fun loadBook() {
        compositeDisposable.add(
            roomRepository.getAll(
                success = {
                    _bookData.value = it
                },
                failure = {
                    LogE("ERROR : $it")
                }
            )
        )
    }

    fun deleteBook(
        isbn13 : String
    ) {
        compositeDisposable.add(
            roomRepository.deleteBook(
                isbn13 = isbn13,
                success = {
                    loadBook()
                },
                failure = {
                    LogE("ERROR : $it")
                }
            )
        )
    }
}