package com.hyden.booklibrary.view.profile

import androidx.lifecycle.LiveData
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.repository.source.FirebaseDataSource
import com.hyden.booklibrary.util.SingleLiveEvent

class ProfileViewModel(private val firebaseDataSource: FirebaseDataSource) : BaseViewModel() {

    private val _eventProfile = SingleLiveEvent<Any>()
    val eventProfile : LiveData<Any> get() = _eventProfile

    private val _eventNickName = SingleLiveEvent<Any>()
    val eventNickName : LiveData<Any> get() = _eventNickName

    private val _eventComplete = SingleLiveEvent<Any>()
    val eventComplete : LiveData<Any> get() = _eventComplete

    // 프로필 이미지 변경
    fun changeProfile() {
        _eventProfile.call()
    }

    // 프로필 닉네임 변경
    fun changeNickName() {
        _eventNickName.call()
    }

    // 완료 버튼 클릭 이벤트
    fun changedComplete() {
        _eventComplete.call()
    }

}