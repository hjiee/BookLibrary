package com.hyden.booklibrary.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hyden.booklibrary.util.ConstUtil.Companion.DATABASELIMIT
import com.hyden.booklibrary.util.ConstUtil.Companion.DATABASENAME_BOOK
import io.reactivex.Completable

@Dao
interface BookDao {

    @Insert
    fun insert(
        bookEntity: List<BookEntity?>
    ): Completable

    @Insert
    fun insertNote(
        bookEntity: BookEntity?
    ): Completable


    @Query("DELETE FROM $DATABASENAME_BOOK WHERE isbn13 = :isbn13")
    fun deleteBook(
        isbn13: String
    ): Completable

    @Query("SELECT * FROM $DATABASENAME_BOOK WHERE isbn13 = :isbn13")
    fun getBook(
        isbn13: String
    ): BookEntity

    @Query("SELECT * FROM $DATABASENAME_BOOK LIMIT $DATABASELIMIT")
    fun getAll(): List<BookEntity>

    @Update
    fun updateBook(bookEntity: BookEntity?) : Completable

    @Query("SELECT COUNT(*) FROM $DATABASENAME_BOOK WHERE isbn13 = :isbn13")
    fun isContains(isbn13: String): Int
}