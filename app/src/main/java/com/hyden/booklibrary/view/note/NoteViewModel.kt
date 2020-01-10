package com.hyden.booklibrary.view.note

import androidx.databinding.ObservableField
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.repository.FirebaseRepository
import com.hyden.booklibrary.data.repository.RoomRepository

class NoteViewModel(
    private val roomRepository: RoomRepository,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel() {

    var bookNote = ObservableField<String>()

    fun updateBook(bookEntity: BookEntity) {
        compositeDisposable.add(roomRepository.updateBook(bookEntity = bookEntity))
        firebaseRepository.updateBook(bookEntity)
    }


    fun getBook(
        isbn13 : String
    ) {
        compositeDisposable.add(
            roomRepository.getBook(
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