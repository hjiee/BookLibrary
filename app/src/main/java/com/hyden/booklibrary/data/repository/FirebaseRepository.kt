package com.hyden.booklibrary.data.repository

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.model.*
import com.hyden.booklibrary.data.repository.source.FirebaseDataSource
import com.hyden.booklibrary.util.ConstUtil.Companion.DATABASENAME
import com.hyden.booklibrary.util.ConstUtil.Companion.USER_FIRESTORE_NAME
import java.util.*


class FirebaseRepository(
    clientId: String,
    context: Context
) : FirebaseDataSource {

    private val firebaseFireStore by lazy { FirebaseFirestore.getInstance() }
    private val googleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(clientId)
            .requestEmail()
            .build()
    }
    private val googleSignInClient by lazy { GoogleSignIn.getClient(context,googleSignInOptions) }
    private val googleAuth by lazy { FirebaseAuth.getInstance() }
    private val currentUser by lazy { User(getLoginEmail(), getLoginName(), getLoginNickname(), getLoginProfile()) }


    // Book
    override fun pushLike(isSelected : Boolean, documentId : String) {
        if (isSelected) {
            firebaseFireStore.collection(DATABASENAME).document(documentId).update("likesCount", FieldValue.increment(1))
            firebaseFireStore.collection(DATABASENAME).document(documentId).update("likesInfo.users", FieldValue.arrayUnion((currentUser)))
        } else {
            firebaseFireStore.collection(DATABASENAME).document(documentId).update("likesCount", FieldValue.increment(-1))
            firebaseFireStore.collection(DATABASENAME).document(documentId).update("likesInfo.users", FieldValue.arrayRemove(((currentUser))))
        }

    }

    override fun getLikeCount(documentId : String,complete : (Long) -> Unit) {
        var count = 0L
        firebaseFireStore.collection(DATABASENAME).document(documentId).get().addOnCompleteListener {
            count = it.result?.get("likesCount") as Long
            complete.invoke(count)
        }
    }

    override fun getCommentCount(documentId: String, complete : (Long) -> Unit) {
        var count = 0L
        firebaseFireStore.collection(DATABASENAME).document(documentId).get().addOnCompleteListener {
            count = it.result?.get("commentsCount") as Long
            complete.invoke(count)

        }
    }

    override fun pushComment() {
    }

    override fun pushShare(item : BookEntity) {
        firebaseFireStore.collection(DATABASENAME).document(getLoginEmail()+"-"+item.isbn13).set(
            Feed(
                bookEntity = item,
                sharedInfo = SharedInfo(Date(),currentUser),
                usersInfo = currentUser,
                likesCount = if(item.isLiked == true) 1 else 0,
                likesInfo = if(item.isLiked == true) Like(listOf(currentUser)) else Like(emptyList()),
                commentsCount = if(item.isReviews == true) 1 else 0,
                commentsInfo = if(item.isReviews == true) Comment(listOf(currentUser)) else Comment(emptyList())
            ),
            SetOptions.merge())
    }

    override fun updateBook(item : BookEntity) {
        firebaseFireStore.collection(DATABASENAME).document(getLoginEmail()+"-"+item.isbn13).update("bookEntity",item)
    }

    override fun deleteBook(isbn13 : String) {
        firebaseFireStore.collection(DATABASENAME).document(getLoginEmail()+"-"+isbn13).delete()
    }

    override fun saveBook() {
    }

    // User
    override fun updateUser() {

    }

    override fun deleteUser(id: String) {

    }

    override fun saveUser() {

    }

    override fun isExsitUser(users : List<User>): Boolean {
        return users.contains(currentUser)
    }

    override fun login() {
    }

    override fun googleSignIn() {
        firebaseFireStore.collection(USER_FIRESTORE_NAME).document(getLoginEmail()).set(currentUser)
    }

    override fun googleSignOut() {
        googleAuth.signOut()
        googleSignInClient.signOut()
    }

    // Getter
    override fun getLoginEmail(): String = googleAuth.currentUser?.email ?: ""

    override fun getLoginName(): String = googleAuth.currentUser?.displayName ?: ""

    override fun getLoginProfile(): String = googleAuth.currentUser?.photoUrl.toString() ?: ""

    override fun getLoginNickname(): String = ""
}