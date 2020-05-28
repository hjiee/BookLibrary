package com.hyden.booklibrary.view.profile

import android.net.Uri
import android.provider.MediaStore
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.loader.content.CursorLoader
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.model.User
import com.hyden.booklibrary.data.repository.source.FirebaseDataSource
import com.hyden.booklibrary.util.SingleLiveEvent
import com.hyden.util.LogUtil.LogE
import com.hyden.util.Result
import java.io.InputStream
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

    private val _userEmail = MutableLiveData<String>()
    val userEmail : LiveData<String> get() = _userEmail

    private val _userName = MutableLiveData<String>()
    val userName : LiveData<String> get() = _userName

    private val _userProfile = MutableLiveData<String>()
    val userProfile : LiveData<String> get() = _userProfile

    private val _userProfileStream = MutableLiveData<Uri>()
    val userProfileStream : LiveData<Uri> get() = _userProfileStream

    private val _currentUserProfileStream = MutableLiveData<Uri>()
    val currentUserProfileStream : LiveData<Uri> get() = _currentUserProfileStream

    private var isProfileChangeState = ObservableField<Boolean>()
    private var isNicknameChangeState = ObservableField<Boolean>()

    init {
        _userProfile.value = firebaseDataSource.getLoginProfile()
        _userNickName.value = firebaseDataSource.getLoginNickname()
        _userEmail.value = firebaseDataSource.getLoginEmail()
        _userName.value = firebaseDataSource.getLoginName()
        isProfileChangeState.set(false)
        isNicknameChangeState.set(false)
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

    fun currentProfile(profile: Uri?) {
        _currentUserProfileStream.value = profile
        _userProfileStream.value = profile
    }
    fun setProfile(profile : Uri?) {
        _userProfileStream.value = profile
        isProfileChangeState.set(true)
//        _userProfile.value = profile
    }

    fun setNickname(nickName: String?) {
        _userNickName.value = nickName
        isNicknameChangeState.set(true)
    }



    // 완료 버튼 클릭 이벤트
    fun changedComplete() {
        if(isProfileChangeState.get()!! || isNicknameChangeState.get()!!) {
            _eventComplete.call(ProfileUpdateType.STARTING)

        } else {
            _eventComplete.call(ProfileUpdateType.CANCEL)
        }
    }

    /**
     * 파이어베이스 스토리지에 변경한 이미지를 저장하고 다운로드 url을 가져온다.
     */
    fun saveStroageProfile() {
        if(isProfileChangeState.get()!!) {
            _userProfileStream.value?.let {
                firebaseDataSource.uploadProfile(it) { result, url ->
                    when(result) {
                        Result.SUCCESS -> {
                            _userProfile.value = url
                            updateUserProfile()
                        }
                        Result.FAILURE -> {
                            LogE(url)
                            _eventComplete.call(ProfileUpdateType.FAILURE)
                        }
                    }
                }
            }
        } else {
            updateUserProfile()
        }

    }

    /**
     * 파이어베이스 유저정보를 업데이트 한다.
     */
    fun updateUserProfile() {
        firebaseDataSource.updateProfile(User(
            firebaseDataSource.getLoginEmail(),
            firebaseDataSource.getLoginName(),
            _userNickName.value ?: "",
            _userProfile.value ?: "",
            Date())
        ) {
            _eventComplete.call(ProfileUpdateType.SUCCESS)
        }
    }
}