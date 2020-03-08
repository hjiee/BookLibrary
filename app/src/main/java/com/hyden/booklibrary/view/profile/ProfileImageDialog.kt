package com.hyden.booklibrary.view.profile

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyden.base.BaseDialogFragment
import com.hyden.booklibrary.R
import com.hyden.booklibrary.databinding.DialogProfileImageBinding

class ProfileImageDialog(
    private val gallery : () -> Unit,
    private val camera : () -> Unit
) : BaseDialogFragment<DialogProfileImageBinding>(R.layout.dialog_profile_image) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.run {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setGravity(Gravity.CENTER)
        }
    }

    override fun initBind() {
        binding.apply {
            ibGallery.setOnClickListener {
                gallery.invoke()
                dismiss()
            }
            ibCamera.setOnClickListener {
                camera.invoke()
                dismiss()
            }
        }
    }
}