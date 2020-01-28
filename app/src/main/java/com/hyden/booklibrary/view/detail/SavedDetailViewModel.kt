package com.hyden.booklibrary.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.repository.FirebaseRepository
import com.hyden.booklibrary.data.repository.RoomRepository
import com.hyden.booklibrary.data.repository.source.FirebaseDataSource
import com.hyden.booklibrary.data.repository.source.RoomDataSource
import com.hyden.util.LogUtil.LogD
import com.hyden.util.LogUtil.LogE

class SavedDetailViewModel(
    private val roomDatasource: RoomDataSource,
    private val firebaseRepository: FirebaseDataSource
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

    fun bookInsert() {
        compositeDisposable.add(
            roomDatasource.insert(
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
            roomDatasource.deleteBook(
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
        compositeDisposable.add(roomDatasource.updateBook(bookEntity = bookEntity))
    }

    fun pushLike(isSelected: Boolean, bookEntity: BookEntity) {
        val documentId = firebaseRepository.getLoginEmail() + "-" + bookEntity.isbn13
        firebaseRepository.pushLike(
            isSelected = isSelected,
            documentId = documentId
        )
    }

    fun pushShare(bookEntity: BookEntity) {
        firebaseRepository.pushShare(bookEntity)
    }

    fun pushDelete(isbn13: String) {
        firebaseRepository.deleteBook(isbn13)
    }

    fun isSharedUser() {
    }

    fun isBookContains(
        isbn13: String
    ) {
        compositeDisposable.add(
            roomDatasource.isContains(
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




