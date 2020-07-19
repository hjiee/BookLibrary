package com.hyden.booklibrary.view.setting

import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.repository.FirebaseRepository
import com.hyden.booklibrary.data.repository.source.FirebaseDataSource

class SettingViewModel(
    private val firebaseDataSource: FirebaseDataSource
) : BaseViewModel() {

    fun signOut() = firebaseDataSource.googleSignOut()

}