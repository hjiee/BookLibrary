package com.hyden.booklibrary.data.repository.source

import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.model.Feed
import com.hyden.booklibrary.data.model.User

interface FirebaseDataSource {



    // Book
    fun saveBook()
    fun updateBook(item : BookEntity)
    fun deleteBook(isbn13 : String)

    fun pushLike(isSelected : Boolean, documentId : String)
    fun getLikeCount(documentId : String,complete : (Long) -> Unit)
    fun getCommentCount(documentId : String,complete : (Long) -> Unit)
    fun pushComment()
    fun pushShare(item : BookEntity)


    // User
    fun saveUser()
    fun updateUser()
    fun deleteUser(id : String)
    fun isExsitUser(users : List<User>) : Boolean

    fun login()
    fun googleSignIn()
    fun googleSignOut()

    // getter
    fun getLoginEmail() : String
    fun getLoginName() : String
    fun getLoginProfile() : String
    fun getLoginNickname() : String
}