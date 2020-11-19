package com.hyden.ext

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.hyden.util.ConstValueUtil.Companion.YYMMDDHHMMSS_FORMAT
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter(value = ["dateFormat"], requireAll = false)
fun TextView.dateFormat(date : Date) {
    val sdf = SimpleDateFormat(YYMMDDHHMMSS_FORMAT)
    val formDate = sdf.format(date).toString()
    text = formDate
}

@BindingAdapter(value = ["elapsedTimeFormat"], requireAll = false)
fun TextView.elapsedTimeFormat(createdDate : Date) {
    val sdf = SimpleDateFormat(YYMMDDHHMMSS_FORMAT)
    val createdAt = sdf.format(createdDate).toString()

    // 현재 시간 ( milliseconds )
    val currentedAt = sdf.format(Date())
    val currentTime = System.currentTimeMillis()
    // 생성 시간 ( milliseconds )
    val createdTime = SimpleDateFormat(YYMMDDHHMMSS_FORMAT).parse(createdAt).time

    val elapsedTime = currentTime - createdTime

    text = elapsedTime.elapsedTimeFormatter(createdDate)
}

@BindingAdapter(value = ["likeCount"], requireAll = false)
fun TextView.likeCount(count : Long) {
    text = count.toString()
}