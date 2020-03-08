package com.hyden.booklibrary.view.profile

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hyden.base.BaseDialogFragment
import com.hyden.booklibrary.R
import com.hyden.booklibrary.databinding.DialogProfileModifyBinding
import com.hyden.ext.showKeyboard
import com.hyden.ext.showToast
import com.hyden.ext.validationNickname
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileNickNameTransparentDialog(
    private val currentNickName : String,
    private val callbackNickName : (String) -> Unit
) : BaseDialogFragment<DialogProfileModifyBinding>(R.layout.dialog_profile_modify) {

    private val profileViewModel by viewModel<ProfileViewModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.run {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setGravity(Gravity.CENTER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initBind() {
        binding.apply {
            vm = profileViewModel
            context?.showKeyboard(edtNickname)

            edtNickname.setText(currentNickName)
            ibClear.setOnClickListener {
                edtNickname.setText("")
                context?.showKeyboard(edtNickname)
            }
            ivBack.setOnClickListener {
                dismiss()
            }
            tvComplete.setOnClickListener {
                if(edtNickname.text.toString().validationNickname()) {
                    callbackNickName.invoke(edtNickname.text.toString())
                    dismiss()
                } else {
                    context?.showToast(getString(R.string.check_validation_nickname))
                }
            }
        }
    }
}