package com.hyden.booklibrary.data.repository.source

import android.net.Uri
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.model.Feed
import com.hyden.booklibrary.data.model.User
import com.hyden.util.Result
import java.io.InputStream

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
    fun updateUser(user : User)
    fun deleteUser(id : String)
    fun isExsitUser(users : List<User>) : Boolean
    fun updateProfile(user : User,success : () -> Unit?)

    fun login()
    fun googleSignIn()
    fun googleSignOut()

    fun uploadProfile(profile : Uri, result : (Result, String) -> Unit)


    // getter
    fun getLoginEmail() : String
    fun getLoginName() : String
    fun getLoginProfile() : String
    fun getLoginNickname() : String

}