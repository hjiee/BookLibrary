package com.hyden.booklibrary.view.note

import androidx.databinding.ObservableField
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.repository.FirebaseRepository
import com.hyden.booklibrary.data.repository.RoomRepository
import com.hyden.booklibrary.data.repository.source.FirebaseDataSource
import com.hyden.booklibrary.data.repository.source.RoomDataSource

class NoteViewModel(
    private val roomDataSource: RoomDataSource,
    private val firebaseDataSource: FirebaseDataSource
) : BaseViewModel() {

    var bookNote = ObservableField<String>()

    fun updateBook(bookEntity: BookEntity) {
        compositeDisposable.add(roomDataSource.updateBook(bookEntity = bookEntity))
        firebaseDataSource.updateBook(bookEntity)
    }


    fun getBook(
        isbn13 : String
    ) {
        compositeDisposable.add(
            roomDataSource.getBook(
                isbn13 = isbn13,
                success = {
                    bookNote.set(it?.bookNote)
                },
                failure = {

                }
            )
        )
    }
}