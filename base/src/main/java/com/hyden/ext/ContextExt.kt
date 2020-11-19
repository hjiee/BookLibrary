package com.hyden.ext

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.provider.Settings
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.hyden.base.R

fun Context.versionName() : String =
    packageManager.getPackageInfo(packageName,0).versionName


fun Context.showKeyboard(view: View) {
    view.run {
        when (view) {
            is EditText -> {
                isFocusable = true
                isFocusableInTouchMode = true
                view.requestFocus()
                (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
                view.setSelection(view.length())

            }
        }
    }
}

fun Context.hideKeyboard(view: View) {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(
        view.windowToken,
        0
    )
}

fun Context.permissonsCheck(
    neededPermissions: Array<String>,
    granted : (() -> Unit)? = null,
    denied : (() -> Unit)? = null
) {
    for(permission in neededPermissions) {
        if(checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            denied?.invoke()
            return
        }
    }
    granted?.invoke()
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showSimpleDialog(
    title : String? = getString(R.string.app_name),
    message: String,
    result: (() -> Unit)? = null
) {
    AlertDialog.Builder(this, R.style.DeleteDialog).apply {
        setTitle(title)
        setMessage("$message")
        setPositiveButton(getString(R.string.ok)) { _, _ ->
            result?.invoke()
        }
        setNegativeButton(getString(R.string.cancel)) { _, _ ->
        }
    }.show()
//        .create().run {
//        val textMessage = this.findViewById<TextView>(android.R.id.message)
//        textMessage?.textSize = 20f
//        textMessage?.typeface = Typeface.createFromAsset(assets,"fonts/scdream5_medium")
//        show()
//    }
}


fun Context.isTimeAutomatic(truth : () -> Unit) {
    if(Settings.Global.getInt(contentResolver, Settings.Global.AUTO_TIME, 0) == 0) {
        showSimpleDialog(message = getString(R.string.setting_system_time)) {
            startActivity(Intent(Settings.ACTION_DATE_SETTINGS))
        }
    } else {
        truth.invoke()
    }

}