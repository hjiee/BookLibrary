package com.hyden.booklibrary.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.repository.RoomRepository
import com.hyden.util.LogUtil.LogD
import com.hyden.util.LogUtil.LogE

class SavedDetailViewModel(
    private val roomRepository: RoomRepository
) : BaseViewModel() {

    private val _detailInfo = MutableLiveData<BookEntity>()
    val detailInfo: LiveData<BookEntity> get() = _detailInfo

    private val _isContain = MutableLiveData<Boolean>()
    val isContain : LiveData<Boolean> get() = _isContain

    private val _isDelete = MutableLiveData<Boolean>()
    val isDelete : LiveData<Boolean> get() = _isDelete

    fun bookInfo(bookInfo: BookEntity?) {
        _detailInfo.value = bookInfo
        LogD("데이터 저장")
    }

    fun bookInsert() {
        compositeDisposable.add(
            roomRepository.insert(
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
        isbn13 : String
    ) {
        compositeDisposable.add(
            roomRepository.deleteBook(
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

    fun bookUpdate(
        bookEntity: BookEntity
    ) {
        compositeDisposable.add(
            roomRepository.updateBook(
                bookEntity = bookEntity,
                success = {
                },
                failure = {
                    LogE("ERROR : $it")
                }
            )
        )
    }

    fun isBookContains(
        isbn13 : String
    ) {
        compositeDisposable.add(
            roomRepository.isContains(
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




