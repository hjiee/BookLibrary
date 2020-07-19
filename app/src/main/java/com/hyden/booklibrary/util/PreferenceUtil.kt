package com.hyden.booklibrary.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.hyden.booklibrary.R

/**
 * 테마 설정
 */
fun AppCompatActivity.getPreferenceTheme(): String {
    return getSharedPreferences(
        getString(R.string.setting_key_theme),
        MODE_PRIVATE
    )?.getString("themeColor", "블랙") ?: "블랙"
}

fun Fragment.getPreferenceTheme(): String {
    return context?.getSharedPreferences(
        getString(R.string.setting_key_theme),
        MODE_PRIVATE
    )?.getString("themeColor", "블랙") ?: "블랙"
}

fun Fragment.setPreferenceTheme(value: String) {
    context?.getSharedPreferences(getString(R.string.setting_key_theme), MODE_PRIVATE)?.edit()
        ?.apply {
            putString("themeColor", value)
            apply()
        }
}


/**
 * 시작화면 설정
 */
fun AppCompatActivity.getPreferenceStartView(): String {
    return getSharedPreferences(
        getString(R.string.setting_key_start_view),
        MODE_PRIVATE
    )?.getString("startView", "홈") ?: "홈"
}

fun Fragment.getPreferenceStartView(): String {
    return context?.getSharedPreferences(
        getString(R.string.setting_key_start_view),
        MODE_PRIVATE
    )?.getString("startView", "홈") ?: "홈"
}

fun Fragment.setPreferenceStartView(value: String) {
    context?.getSharedPreferences(getString(R.string.setting_key_start_view), MODE_PRIVATE)?.edit()
        ?.apply {
            putString("startView", value)
            apply()
        }
}

/**
 * 유저 정보
 */
fun Context.setUserNickName(nickname: String) {
    getSharedPreferences(getString(R.string.string_nickname), MODE_PRIVATE)?.edit()
        ?.apply {
            putString("userNickName", nickname)
            apply()
        }
}

fun Context.getUserNickName(): String {
    return getSharedPreferences(
        getString(R.string.string_nickname),
        MODE_PRIVATE
    )?.getString("userNickName", null) ?: ""
}

fun Context.setUserProfile(profile: String) {
    getSharedPreferences(getString(R.string.string_profile), MODE_PRIVATE)?.edit()
        ?.apply {
            putString("userProfile", profile)
            apply()
        }
}

fun Context.getUserProfile(): String {
    return getSharedPreferences(
        getString(R.string.string_profile),
        MODE_PRIVATE
    )?.getString("userProfile", null) ?: FirebaseAuth.getInstance().currentUser?.photoUrl.toString()
}

/**
 * 초기
 */
fun Context.setInitPreferences(data: Boolean) {
    getSharedPreferences(getString(R.string.init_pref_book), MODE_PRIVATE)?.edit()
        ?.apply {
            putBoolean("init_data", data)
            apply()
        }
}

fun Context.getInitPreferences(): Boolean {
    return getSharedPreferences(
        getString(R.string.init_pref_book),
        MODE_PRIVATE
    )?.getBoolean("init_data", false) ?: false
}