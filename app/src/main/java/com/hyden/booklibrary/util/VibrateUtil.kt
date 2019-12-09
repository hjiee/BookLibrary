package com.hyden.booklibrary.util

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment

private const val vibrateMills = 50L
private const val vibrateAmplitude = 50

fun Fragment.longClickVibrate() {
    (activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).apply {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            vibrate(
                VibrationEffect.createOneShot(
                    vibrateMills,
                    vibrateAmplitude
                )
            )
        } else {
            vibrate(vibrateMills)
        }
    }
}