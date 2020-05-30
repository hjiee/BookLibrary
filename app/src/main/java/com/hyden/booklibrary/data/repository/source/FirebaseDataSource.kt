package com.hyden.booklibrary.data.repository.source

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.model.Feed
import com.hyden.booklibrary.data.model.User
import com.hyden.util.Result
import java.io.InputStream
import java.util.*

interface FirebaseDataSource {

    var currentUser : User

    // Book
    fun saveBook()
    fun updateBook(item : BookEntity)
    fun deleteBook(isbn13 : String)

    fun pushLike(isSelected : Boolean, documentId : String)
    fun getLikeCount(documentId : String,complete : (Long) -> Unit)
    fun getCommentCount(documentId : String,complete : (Long) -> Unit)
    fun pushComment()
    fun pushShare(item : BookEntity)
    fun myBookInsert(item : BookEntity)
    fun myBookDelete(isbn13 : String)


    // User
    fun saveUser()
    fun updateUser(user : User)
    fun deleteUser(id : String)
    fun isExistUser(users : List<User>) : Boolean
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