package com.hyden.ext

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.fragment.app.Fragment
import com.hyden.base.R
import com.hyden.util.ConstValueUtil.Companion.DEF_REQUEST_PERMISSION_CODE

private const val vibrateMills = 50L
private const val vibrateAmplitude = 50

fun Fragment.replaceFragment(fragment: Fragment, layoutId : Int) {
    fragmentManager?.beginTransaction()
        ?.replace(layoutId,fragment)
        ?.commitNow()
}


fun Fragment.replaceFragmentStack(fragment: Fragment, layoutId : Int) {
    fragmentManager?.beginTransaction()
        ?.replace(layoutId,fragment)
        ?.addToBackStack(null)
        ?.commit()
}


fun Fragment.moveToActivity(intent : Intent?) {
    startActivity(intent)
    activity?.overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
}

fun Fragment.moveToActivityForResult(intent : Intent?,requestCode : Int) {
    startActivityForResult(intent,requestCode)
    activity?.overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
}

fun Fragment.permissonsCheck(
    neededPermissions: Array<String>,
    granted : (() -> Unit)? = null
) {
    context?.permissonsCheck(
        neededPermissions = neededPermissions,
        granted = {
            granted?.invoke()
        },
        denied = {
            requestPermissions(neededPermissions,DEF_REQUEST_PERMISSION_CODE)
        }
    )
}