package com.hyden.booklibrary.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.repository.source.FirebaseDataSource
import com.hyden.booklibrary.util.ConstUtil
import com.hyden.booklibrary.view.feed.model.FeedData
import com.hyden.util.LogUtil.LogW
import java.util.HashMap

class LoginViewModel(
    private val firebaseDataSource: FirebaseDataSource
) : BaseViewModel() {

    private val firestore by lazy { FirebaseFirestore.getInstance() }

    private val _auth = MutableLiveData<FirebaseAuth>().apply { FirebaseAuth.getInstance() }
    val auth: LiveData<FirebaseAuth> get() = _auth

    fun loing() {
        firebaseDataSource.login()
    }

    fun googleSignIn() = firebaseDataSource.googleSignIn()


    fun googleSignOut() = firebaseDataSource.googleSignOut()

    fun saveUser() = firebaseDataSource.saveUser()

    fun mysharedBook() {
        firestore.collection(ConstUtil.DATABASENAME_BOOK)
//            .orderBy("sharedInfo.sharedDate", Query.Direction.DESCENDING)
            .whereEqualTo("sharedInfo.users.email", firebaseDataSource.getLoginEmail())
            .get()
            .addOnSuccessListener { documentSnapshot ->
                LogW("${documentSnapshot.size()}")
                for (i in documentSnapshot.documents.indices) {
                    val bookEntity = (documentSnapshot.documents[i].data?.get("bookEntity") as BookEntity)
                    LogW("${(bookEntity)}")
                }
                if (documentSnapshot.size() > 0) {
//                    val temp = mutableListOf<FeedData>()
//                    for (i in documentSnapshot.documents.indices) {
//                        temp.add(FeedData(feed(documentSnapshot.documents[i].data),false))
//                    }
//                    _feedItems.value = _feedItems.value?.let {
//                        it.toMutableList().apply {
//                            addAll(temp)
//                        }
//                    }
                }
            }
    }
}