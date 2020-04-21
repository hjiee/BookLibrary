package com.hyden.booklibrary.view.note

import androidx.databinding.ObservableField
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.repository.source.FirebaseDataSource
import com.hyden.booklibrary.data.repository.source.BookDataSource

class NoteViewModel(
    private val bookDataSource: BookDataSource,
    private val firebaseDataSource: FirebaseDataSource
) : BaseViewModel() {

    var bookNote = ObservableField<String>()

    fun updateBook(bookEntity: BookEntity) {
        compositeDisposable.add(bookDataSource.updateBook(bookEntity = bookEntity))
        firebaseDataSource.updateBook(bookEntity)
    }


    fun getBook(
        isbn13 : String
    ) {
        compositeDisposable.add(
            bookDataSource.getBook(
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