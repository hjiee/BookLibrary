package com.hyden.util

import android.content.Context
import android.util.TypedValue

fun Float.toPx(context : Context): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this,
    context.resources.displayMetrics
).toInt()

enum class Result {
    SUCCESS,
    FAILURE
}