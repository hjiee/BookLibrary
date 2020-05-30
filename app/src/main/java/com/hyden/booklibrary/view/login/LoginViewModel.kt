package com.hyden.booklibrary.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.repository.source.BookDataSource
import com.hyden.booklibrary.data.repository.source.FirebaseDataSource
import com.hyden.booklibrary.util.SingleLiveEvent

class LoginViewModel(
    private val firebaseDataSource: FirebaseDataSource,
    private val bookDataSource: BookDataSource
) : BaseViewModel() {

    private val firestore by lazy { FirebaseFirestore.getInstance() }

    private val _auth = MutableLiveData<FirebaseAuth>().apply { FirebaseAuth.getInstance() }
    val auth: LiveData<FirebaseAuth> get() = _auth
    val succesInit = SingleLiveEvent<Boolean>()

    fun loing() {
        firebaseDataSource.login()
    }

    fun googleSignIn() = firebaseDataSource.googleSignIn()


    fun googleSignOut() = firebaseDataSource.googleSignOut()

    fun saveInit() {

    }

    fun saveUser() = firebaseDataSource.saveUser()

    fun myBook() {
        firebaseDataSource.myBookAll {
            bookDataSource.insertBookAll(it)
                .subscribe({ succesInit.call() }, { })
        }
    }
}