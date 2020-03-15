package com.hyden.booklibrary.view.common

import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.repository.source.FirebaseDataSource

class FirebaseModule(private val firebaseDataSource: FirebaseDataSource) : BaseViewModel() {
    override fun onCleared() {
        super.onCleared()
    }

    fun getProfile() : String = firebaseDataSource.getLoginProfile()
    fun getName() : String = firebaseDataSource.getLoginName()
    fun getEmail() : String = firebaseDataSource.getLoginEmail()
    fun getNickName() : String = firebaseDataSource.getLoginNickname()
}