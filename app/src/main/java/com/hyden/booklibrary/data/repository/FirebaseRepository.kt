package com.hyden.booklibrary.data.repository

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.model.*
import com.hyden.booklibrary.data.repository.source.FirebaseDataSource
import com.hyden.booklibrary.util.ConstUtil.Companion.DATABASENAME
import com.hyden.booklibrary.util.ConstUtil.Companion.FIRESTORE_USERS
import com.hyden.booklibrary.util.getUserNickName
import com.hyden.booklibrary.util.setUserNickName
import java.util.*


class FirebaseRepository(
    val clientId: String,
    val context: Context
) : FirebaseDataSource {

    private val firebaseFireStorage by lazy { FirebaseStorage.getInstance() }
    private val firebaseFireStore by lazy { FirebaseFirestore.getInstance() }
    private val googleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(clientId)
            .requestEmail()
            .build()
    }
    private val googleSignInClient by lazy { GoogleSignIn.getClient(context, googleSignInOptions) }
    private val googleAuth by lazy { FirebaseAuth.getInstance() }
    private val currentUser by lazy {
        User(getLoginEmail(), getLoginName(), getLoginNickname(), getLoginProfile(), Date())
    }




    // Book
    /**
     * 좋아요 클릭
     */
    override fun pushLike(isSelected: Boolean, documentId: String) {
        if (isSelected) {
            firebaseFireStore.collection(DATABASENAME).document(documentId)
                .update("likesCount", FieldValue.increment(1))
            firebaseFireStore.collection(DATABASENAME).document(documentId)
                .update("likesInfo.users", FieldValue.arrayUnion((currentUser)))
        } else {
            firebaseFireStore.collection(DATABASENAME).document(documentId)
                .update("likesCount", FieldValue.increment(-1))
            firebaseFireStore.collection(DATABASENAME).document(documentId)
                .update("likesInfo.users", FieldValue.arrayRemove(((currentUser))))
        }
    }


    /**
     * 좋아요 개수
     */
    override fun getLikeCount(documentId: String, complete: (Long) -> Unit) {
        var count = 0L
        firebaseFireStore.collection(DATABASENAME).document(documentId).get()
            .addOnCompleteListener {
                count = it.result?.get("likesCount") as Long
                complete.invoke(count)
            }
    }

    /**
     * 댓글 개수
     */
    override fun getCommentCount(documentId: String, complete: (Long) -> Unit) {
        var count = 0L
        firebaseFireStore.collection(DATABASENAME).document(documentId).get()
            .addOnCompleteListener {
                count = it.result?.get("commentsCount") as Long
                complete.invoke(count)

            }
    }

    override fun pushComment() {
    }

    // 피드에 책정보를 공유
    /**
     * 책정보 피드에 등록
     */
    override fun pushShare(item: BookEntity) {
        firebaseFireStore.collection(DATABASENAME).document(getLoginEmail() + "-" + item.isbn13)
            .set(
                Feed(
                    bookEntity = item,
                    sharedInfo = SharedInfo(getDate(), currentUser),
                    likesCount = if (item.isLiked == true) 1 else 0,
                    likesInfo = if (item.isLiked == true) Like(listOf(currentUser)) else Like(
                        emptyList()
                    ),
                    commentsCount = if (item.isReviews == true) 1 else 0,
                    commentsInfo = if (item.isReviews == true) Comment(listOf(currentUser)) else Comment(
                        emptyList()
                    )
                ), SetOptions.merge()
            )
    }


    override fun updateBook(item: BookEntity) {
        firebaseFireStore.collection(DATABASENAME).document(getLoginEmail() + "-" + item.isbn13)
            .update("bookEntity", item)
    }

    override fun deleteBook(isbn13: String) {
        firebaseFireStore.collection(DATABASENAME).document(getLoginEmail() + "-" + isbn13).delete()
    }

    override fun saveBook() {
    }

    // User
    override fun saveUser() {
        if (currentUser.nickName.isNullOrEmpty()) {

        }
        context.setUserNickName(currentUser.nickName)
        firebaseFireStore.collection(FIRESTORE_USERS).document(getLoginEmail())
            .set(currentUser, SetOptions.merge())
    }

    override fun updateUser(user: User) {
//        firebaseFireStore.collection(DATABASENAME)
//            .whereEqualTo("sharedInfo.users.email",getLoginEmail())

    }

    override fun updateProfile(user: User, success: () -> Unit?) {
        firebaseFireStore.collection(FIRESTORE_USERS).document(getLoginEmail()).set(user)
        context.setUserNickName(user.nickName)
        firebaseFireStore.collection(DATABASENAME)
            .whereEqualTo("sharedInfo.users.email", getLoginEmail())
            .get()
            .addOnSuccessListener { documentSnapshot ->
                for (i in documentSnapshot.documents.indices) {
                    val isbn13 =
                        (documentSnapshot.documents[i].data?.get("bookEntity") as HashMap<*, *>)["isbn13"] as String
//                    ((documentSnapshot.documents[i].data?.get("sharedInfo") as HashMap<*,*>).get("users") as HashMap<*,*>)["email"]
//                    ((documentSnapshot.documents[i].data?.get("sharedInfo") as HashMap<*,*>).get("users") as HashMap<*,*>)["name"]
//                    ((documentSnapshot.documents[i].data?.get("sharedInfo") as HashMap<*,*>).get("users") as HashMap<*,*>)["nickName"]
//                    ((documentSnapshot.documents[i].data?.get("sharedInfo") as HashMap<*,*>).get("users") as HashMap<*,*>)["profile"]
//                    ((documentSnapshot.documents[i].data?.get("sharedInfo") as HashMap<*,*>).get("users") as HashMap<*,*>)["updateAt"]
                    firebaseFireStore.collection(DATABASENAME)
                        .document(getLoginEmail() + "-" + isbn13).update("sharedInfo.users", user)
//                    LogW(documentSnapshot.documents[i].data.toString())
                }
                success.invoke()
            }
    }

    override fun uploadProfile() {
    }

    override fun deleteUser(id: String) {

    }


    /**
     * 게시글 좋아요를 클릭한 유저인지 검사
     * true 좋아요를 클릭한 유저
     * false 좋아요를 클릭하지 않은 유저
     */
    override fun isExsitUser(users: List<User>): Boolean {
        return users.contains(currentUser)
    }

    override fun login() {
    }

    // 로그인
    override fun googleSignIn() {
        firebaseFireStore.collection(FIRESTORE_USERS).document(getLoginEmail()).set(currentUser)
    }

    // 로그아웃
    override fun googleSignOut() {
        googleAuth.signOut()
        googleSignInClient.signOut()
    }

    // Getter
    override fun getLoginEmail(): String = googleAuth.currentUser?.email?.trim() ?: ""

    override fun getLoginName(): String = googleAuth.currentUser?.displayName?.trim() ?: ""

    override fun getLoginProfile(): String = googleAuth.currentUser?.photoUrl.toString() ?: ""

    override fun getLoginNickname(): String {
        val userNickName = context.getUserNickName()
        if (userNickName.isNullOrEmpty()) {
            return getLoginEmail().split("@")[0]
        }
        return userNickName
    }
}