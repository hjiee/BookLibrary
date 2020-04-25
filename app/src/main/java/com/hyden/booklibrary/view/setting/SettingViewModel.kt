package com.hyden.booklibrary.view.setting

import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.repository.BillingRepository
import com.hyden.booklibrary.data.repository.FirebaseRepository
import com.hyden.booklibrary.data.repository.source.FirebaseDataSource

class SettingViewModel(
    private val firebaseDataSource: FirebaseDataSource,
    private val billingRepository : BillingRepository
) : BaseViewModel() {

    fun signOut() = firebaseDataSource.googleSignOut()

    fun donation() {
        billingRepository.donation()
    }

}