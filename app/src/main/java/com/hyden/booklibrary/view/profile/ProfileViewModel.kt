package com.hyden.booklibrary.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.model.User
import com.hyden.booklibrary.data.repository.source.FirebaseDataSource
import com.hyden.booklibrary.util.SingleLiveEvent
import java.util.*

class ProfileViewModel(private val firebaseDataSource: FirebaseDataSource) : BaseViewModel() {

    private val _eventProfile = SingleLiveEvent<Any>()
    val eventProfile : LiveData<Any> get() = _eventProfile

    private val _eventNickName = SingleLiveEvent<Any>()
    val eventNickName : LiveData<Any> get() = _eventNickName

    private val _eventComplete = SingleLiveEvent<Any>()
    val eventComplete : LiveData<Any> get() = _eventComplete

    private val _userNickName = MutableLiveData<String>()
    val userNickName : LiveData<String> get() = _userNickName

    private val _userProfile = MutableLiveData<String>()
    val userProfile : LiveData<String> get() = _userProfile

    init {
        _userProfile.value = firebaseDataSource.getLoginProfile()
        _userNickName.value = firebaseDataSource.getLoginNickname()
    }

    // 프로필 이미지 변경
    fun clickProfile() {
        // TODO: 2020-03-08 프로필 이미지 변경 로직 구현
        _eventProfile.call()
    }

    // 닉네임 변경
    fun clickNickName() {
        _eventNickName.call()
    }

    fun setProfile(profile : String?) {
        _userProfile.value = profile
    }
    fun setNickname(nickName: String?) {
        _userNickName.value = nickName

    }

    // 완료 버튼 클릭 이벤트
    fun changedComplete() {
        firebaseDataSource.uploadProfile()
        if(firebaseDataSource.getLoginProfile() != _userProfile.value || firebaseDataSource.getLoginNickname() != _userNickName.value) {
            _eventComplete.call(ProfileUpdateType.STARTING)
        } else {
            _eventComplete.call(ProfileUpdateType.CANCEL)
        }
    }

    fun updateUserProfile() {
        firebaseDataSource.updateProfile(User(
            firebaseDataSource.getLoginEmail(),
            firebaseDataSource.getLoginName(),
            _userNickName.value ?: "",
            _userProfile.value ?: "",
            Date())
        ) { _eventComplete.call(ProfileUpdateType.SUCCESS) }
    }

}