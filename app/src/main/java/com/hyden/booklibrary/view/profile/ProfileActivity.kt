package com.hyden.booklibrary.view.profile

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.lifecycle.Observer
import com.hyden.base.BaseActivity
import com.hyden.booklibrary.R
import com.hyden.booklibrary.databinding.ActivityProfileBinding
import com.hyden.ext.*
import com.hyden.util.ImageTransformType
import com.hyden.util.LogUtil.LogE
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
import com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE
import com.theartofdev.edmodo.cropper.CropImageView
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileActivity : BaseActivity<ActivityProfileBinding>(R.layout.activity_profile) {

    private val profileViewModel by viewModel<ProfileViewModel>()
    private val REQUEST_GALLERY_CODE = 2000
    private val REQUEST_CAMERA_CODE = 2001

    private val profileChoiceDialog = ProfileImageDialog(
        // 사진 선택
        gallery = {
            Intent(Intent.ACTION_PICK).run {
                type = MediaStore.Images.Media.CONTENT_TYPE
                moveToActivityForResult(this, REQUEST_GALLERY_CODE)
            }
        },
        // 카메라
        camera = {
            permissonsCheck(arrayOf(Manifest.permission.CAMERA)) {
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).run {
                    moveToActivityForResult(this, REQUEST_CAMERA_CODE)
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.ivProfile.loadUrl(profileViewModel.userProfile.value, ImageTransformType.CIRCLE)
        profileViewModel.currentProfile(Uri.parse("android.resource://com.hyden.booklibrary/${R.drawable.background_circle}"))
        initObserving()
    }

    override fun initBind() {
        binding.apply {
            vm = profileViewModel
            ivBack.setOnClickListener { finish() }
        }
        LogE("${supportFragmentManager.backStackEntryCount}")
        supportFragmentManager.addOnBackStackChangedListener {
            LogE("${supportFragmentManager.backStackEntryCount}")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        profileChoiceDialog.dismiss()
        when (requestCode) {
            REQUEST_GALLERY_CODE -> { startImageCropView(data) }
            REQUEST_CAMERA_CODE -> { startImageCropView(data) }
            CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val cropResult = CropImage.getActivityResult(data)
                if (resultCode == RESULT_OK) {
                    cropResult?.uri?.let {
                        profileViewModel.setProfile(it)
                        var bitmap : Bitmap? = null
                        contentResolver.openInputStream(it)?.run {
                            bitmap = BitmapFactory.decodeStream(this)
                            close()
                        }
                        binding.ivProfile.loadBitmap(bitmap)
                    }
                } else if (resultCode == CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    LogE("${cropResult?.error}")
                }
            }
        }
    }

    /**
     * 이미지 크롭 뷰 시작
     */
    private fun startImageCropView(data : Intent?) {
        data?.data?.let {
            CropImage.activity(it)
                .setActivityTitle("이미지 편집")
                .setCropMenuCropButtonTitle("완료")
                .setCropShape(CropImageView.CropShape.OVAL) // 원형 크롭
                .setAutoZoomEnabled(false) // 크로필될때 줌 설정 끄기
                .setAllowFlipping(false) // 좌우반전 옵션
                .setFixAspectRatio(true) // 비율 고정
                .setAllowRotation(true) // 회전 허용
                .start(this@ProfileActivity)
        }
    }

    private fun initObserving() {
        profileViewModel.apply {
            /**
             * 프로필 이미지 변경
             */
            eventProfile.observe(this@ProfileActivity, Observer {
                // TODO: 2020-03-08 이미지 갤러리로 부터 사진 불러오기 로직 구현
                profileChoiceDialog.show(supportFragmentManager, "")
            })

            /**
             * 프로필 닉네임 변경
             */
            eventNickName.observe(this@ProfileActivity, Observer {
                ProfileNickNameTransparentDialog(binding.tvNickname.text.toString()) { nickName ->
                    profileViewModel.setNickname(nickName)
                }.show(supportFragmentManager, "")
            })

            /**
             * 프로필 변경 완료 이벤트
             */
            eventComplete.observe(this@ProfileActivity, Observer {
                when (it) {
                    ProfileUpdateType.STARTING -> {
                        isTimeAutomatic {
                            showSimpleDialog(message = getString(R.string.check_update_profile)) {
                                // 이미지를 firebase storage에 저장한다.
                                profileViewModel.showLoading()
                                profileViewModel.saveStroageProfile()
                            }
                        }
                    }
                    ProfileUpdateType.FAILURE -> {
                        showToast(getString(R.string.check_update_profile_failure))
                    }
                    ProfileUpdateType.CANCEL -> {
                        showToast(getString(R.string.check_update_profile_cancel))
                    }
                    ProfileUpdateType.SUCCESS -> {
                        profileViewModel.hideLoading()
                        showToast(getString(R.string.check_update_profile_success))
                        finish()
                    }
                }

            })
        }
    }

//    fun getPath(uri: Uri): String {
//        var thePath: String? = "no-path-found"
//        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
//        val cursor: Cursor = contentResolver.query(uri, filePathColumn, null, null, null)!!
//        if (cursor.moveToFirst()) {
//            val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
//            thePath = cursor.getString(columnIndex)
//        }
//        LogW("${MediaStore.Images.Media.EXTERNAL_CONTENT_URI} / ${MediaStore.Images.Media._ID}")
//        cursor.close()
//        return thePath!!
//    }
}