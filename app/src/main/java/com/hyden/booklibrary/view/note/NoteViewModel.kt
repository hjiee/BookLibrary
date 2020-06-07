package com.hyden.booklibrary.view.note

import androidx.databinding.ObservableField
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.remote.network.response.BookItem
import com.hyden.booklibrary.data.remote.network.response.convertToBookEntity
import com.hyden.booklibrary.data.repository.source.BookDataSource
import com.hyden.booklibrary.data.repository.source.FirebaseDataSource
import com.hyden.util.LogUtil.LogE

class NoteViewModel(
    private val bookDataSource: BookDataSource,
    private val firebaseDataSource: FirebaseDataSource
) : BaseViewModel() {

    var bookNote = ObservableField<String>()

    fun updateBook(bookItem: BookItem) {
        compositeDisposable.add(
            bookDataSource.updateBook(bookEntity = bookItem.convertToBookEntity())
                .subscribe({}, { LogE("$it") })
        )
        firebaseDataSource.updateBook(bookItem.convertToBookEntity())
    }


    fun getBook(
        isbn13: String
    ) {
        compositeDisposable.add(
            bookDataSource.getBook(isbn13)
                .subscribe({ bookNote.set(it?.bookNote) }, { LogE("$it") })
        )
    }
}