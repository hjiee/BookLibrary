package com.hyden.util


import android.util.Log
import com.hyden.base.BuildConfig

object LogUtil {
    private const val TAG = "hjiee"

    fun LogV(msg: String,tag: String? = null) {
        if (BuildConfig.DEBUG) {
            Log.v(tag ?: TAG,"${msg}")
        }
    }

    fun LogD(msg: String,tag: String? = null) {
        if (BuildConfig.DEBUG) {
            Log.d(tag ?: TAG,"${msg}")
        }
    }

    fun LogI(msg: String,tag: String? = null) {
        if (BuildConfig.DEBUG) {
            Log.i(tag ?: TAG,"${msg}")
        }
    }

    fun LogW(msg: String,tag: String? = null) {
        if (BuildConfig.DEBUG) {
            Log.w(tag ?: TAG,"${msg}")
        }
    }

    fun LogE(msg: String,tag: String? = null) {
        if (BuildConfig.DEBUG) {
            Log.e(tag ?: TAG,"ERROR : ${msg}")
        }
    }
}


