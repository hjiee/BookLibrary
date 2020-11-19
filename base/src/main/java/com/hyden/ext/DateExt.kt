package com.hyden.ext

import com.hyden.util.ConstValueUtil.Companion.YYMMDDHHMMSS_FORMAT
import com.hyden.util.ConstValueUtil.Companion.YYMMDD_FORMAT
import java.text.SimpleDateFormat
import java.util.*

fun Date.toDateTimeFormat() : String {
    val sdf = SimpleDateFormat(YYMMDDHHMMSS_FORMAT)
    val date = sdf.format(this).toString()
    return date
}

fun Date.toDateFormat() : String {
    val sdf = SimpleDateFormat(YYMMDD_FORMAT)
    val date = sdf.format(this).toString()
    return date
}