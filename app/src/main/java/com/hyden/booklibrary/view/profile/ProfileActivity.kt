package com.hyden.booklibrary.view.profile

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.lifecycle.Observer
import com.hyden.base.BaseActivity
import com.hyden.booklibrary.R
import com.hyden.booklibrary.databinding.ActivityProfileBinding
import com.hyden.booklibrary.view.common.FirebaseViewModel
import com.hyden.ext.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileActivity : BaseActivity<ActivityProfileBinding>(R.layout.activity_profile) {

    private val profileViewModel by viewModel<ProfileViewModel>()
    private val firebaseViewModel by viewModel<FirebaseViewModel>()
    private val REQUEST_GALLERY_CODE = 2000
    private val REQUEST_CAMERA_CODE = 2000
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            REQUEST_GALLERY_CODE -> {
                showToast("사진")
            }
            REQUEST_CAMERA_CODE -> {
                showToast("카메라")
            }
        }
    }

    private fun initObserving() {
        profileViewModel.apply {
            /**
             * 프로필 이미지 변경
             */
            eventProfile.observe(this@ProfileActivity, Observer {
                // TODO: 2020-03-08 이미지 갤러리로 부터 사진 불러오기 로직 구현

                ProfileImageDialog(
                    gallery = {
                        Intent(Intent.ACTION_PICK).run {
                            type = MediaStore.Images.Media.CONTENT_TYPE
                            moveToActivityForResult(this,REQUEST_GALLERY_CODE)
                        }
                    },
                    camera = {
                        if(checkPermission(listOf(Manifest.permission.CAMERA))) {
                            Intent(MediaStore.ACTION_IMAGE_CAPTURE).run {
                                moveToActivityForResult(this,REQUEST_CAMERA_CODE)
                            }
                        }
                    }
                ).show(supportFragmentManager,"")


//                profileViewModel.setProfile("")
            })

            /**
             * 프로필 닉네임 변경
             */
            eventNickName.observe(this@ProfileActivity, Observer {
                ProfileNickNameTransparentDialog(binding.tvNickname.text.toString()) { nickName ->
                    profileViewModel.setNickname(nickName)
                }.show(supportFragmentManager,"")
            })

            /**
             * 프로필 변경 완료 이벤트
             */
            eventComplete.observe(this@ProfileActivity, Observer {
                when(it) {
                    ProfileUpdateType.STARTING -> {
                        isTimeAutomatic {
                            dialogSimple(getString(R.string.check_update_profile)) {
                                profileViewModel.updateUserProfile()
                            }
                        }
                    }
                    ProfileUpdateType.CANCEL -> {
                        showToast(getString(R.string.check_update_profile_cancel))
                    }
                    ProfileUpdateType.SUCCESS -> {
                        showToast(getString(R.string.check_update_profile_success))
                        finish()
                    }
                }

            })
        }
    }



}