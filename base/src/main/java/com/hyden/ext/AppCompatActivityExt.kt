package com.hyden.ext

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hyden.base.R
import com.hyden.util.ConstValueUtil.Companion.DEF_REQUEST_PERMISSION_CODE

private const val BOOK_NOTE_REQUEST_CODE = 1224

fun AppCompatActivity.replaceFragment(fragment : Fragment, layoutId : Int) {
    supportFragmentManager.beginTransaction()
        .setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
        .replace(layoutId,fragment)
        .commitNow()
}
fun AppCompatActivity.replaceFragmentStack(fragment : Fragment, layoutId : Int) {
    supportFragmentManager.beginTransaction()
        .setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
        .replace(layoutId,fragment)
        .addToBackStack(null)
        .commit()
}

fun AppCompatActivity.moveToActivity(intent : Intent, options: Bundle? = null) {
    startActivity(intent,options)
//    startActivity(intent)
    overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
}

fun AppCompatActivity.moveToActivityForResult(intent : Intent, requestCode : Int = BOOK_NOTE_REQUEST_CODE, options: Bundle? = null) {
    startActivityForResult(intent,requestCode,options)
    overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
}

fun AppCompatActivity.permissonsCheck(
    neededPermissions: Array<String>,
    granted : (() -> Unit)? = null
) {
    applicationContext.permissonsCheck(
        neededPermissions = neededPermissions,
        granted = {
            granted?.invoke()
        },
        denied = {
            requestPermissions(neededPermissions,DEF_REQUEST_PERMISSION_CODE)
        }
    )


}
