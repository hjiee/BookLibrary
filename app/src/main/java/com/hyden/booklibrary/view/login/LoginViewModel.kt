package com.hyden.booklibrary.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.repository.FirebaseRepository

class LoginViewModel(
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel() {

    private val _auth = MutableLiveData<FirebaseAuth>().apply { FirebaseAuth.getInstance() }
    val auth: LiveData<FirebaseAuth> get() = _auth


    fun loing() {
        firebaseRepository.login()
//        _auth.value?.createUserWithEmailAndPassword("", "")
    }

    fun googleSignIn() = firebaseRepository.googleSignIn()


    fun googleSignOut() = firebaseRepository.googleSignOut()
}