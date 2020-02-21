package com.hyden.booklibrary.view.profile

import android.os.Bundle
import androidx.lifecycle.Observer
import com.hyden.base.BaseActivity
import com.hyden.booklibrary.R
import com.hyden.booklibrary.databinding.ActivityProfileBinding
import com.hyden.booklibrary.view.common.FirebaseViewModel
import com.hyden.ext.loadUrl
import com.hyden.ext.showToast
import com.hyden.util.ImageTransformType
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileActivity : BaseActivity<ActivityProfileBinding>(R.layout.activity_profile) {

    private val profileViewModel by viewModel<ProfileViewModel>()
    private val firebaseViewModel by viewModel<FirebaseViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserving()
    }

    // TODO: 2020-02-09 프로필 변경 로직 구현하기
    override fun initBind() {
        binding.apply {
            vm = profileViewModel
            firebase = firebaseViewModel
            ivBack.setOnClickListener { finish() }
        }
    }

    fun initObserving() {
        profileViewModel.apply {
            eventProfile.observe(this@ProfileActivity, Observer {
                showToast("프로필변경")
            })
            eventNickName.observe(this@ProfileActivity, Observer {
                showToast("닉네임변경")
            })
            eventComplete.observe(this@ProfileActivity, Observer {
                showToast("변경완료")
                finish()
            })
        }
    }



}