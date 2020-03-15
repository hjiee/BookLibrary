package com.hyden.booklibrary.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.repository.FirebaseRepository
import com.hyden.booklibrary.data.repository.source.FirebaseDataSource

class LoginViewModel(
    private val firebaseDataSource: FirebaseDataSource
) : BaseViewModel() {

    private val _auth = MutableLiveData<FirebaseAuth>().apply { FirebaseAuth.getInstance() }
    val auth: LiveData<FirebaseAuth> get() = _auth


    fun loing() {
        firebaseDataSource.login()
//        _auth.value?.createUserWithEmailAndPassword("", "")
    }

    fun googleSignIn() = firebaseDataSource.googleSignIn()


    fun googleSignOut() = firebaseDataSource.googleSignOut()

    fun saveUser() = firebaseDataSource.saveUser()

}