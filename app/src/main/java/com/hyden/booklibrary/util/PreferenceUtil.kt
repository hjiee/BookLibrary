package com.hyden.booklibrary.util

import android.content.Context.MODE_PRIVATE
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hyden.booklibrary.R
import kotlinx.android.synthetic.main.include_appbar_main.*

/**
 * 테마 설정
 */
fun AppCompatActivity.getPreferenceTheme() : String {
    return getSharedPreferences(getString(R.string.setting_key_theme),MODE_PRIVATE)?.getString("themeColor","블랙") ?: "블랙"
}

fun Fragment.getPreferenceTheme() : String {
    return context?.getSharedPreferences(getString(R.string.setting_key_theme),MODE_PRIVATE)?.getString("themeColor","블랙") ?: "블랙"
}

fun Fragment.setPreferenceTheme(value : String) {
    context?.getSharedPreferences(getString(R.string.setting_key_theme), MODE_PRIVATE)?.edit()?.apply {
        putString("themeColor",value)
        apply()
    }
}


/**
 * 시작화면 설정
 */
fun AppCompatActivity.getPreferenceStartView() : String {
    return getSharedPreferences(getString(R.string.setting_key_start_view),MODE_PRIVATE)?.getString("startView","홈") ?: "홈"
}

fun Fragment.getPreferenceStartView() : String {
    return context?.getSharedPreferences(getString(R.string.setting_key_start_view),MODE_PRIVATE)?.getString("startView","홈") ?: "홈"
}

fun Fragment.setPreferenceStartView(value : String) {
    context?.getSharedPreferences(getString(R.string.setting_key_start_view), MODE_PRIVATE)?.edit()?.apply {
        putString("startView",value)
        apply()
    }
}