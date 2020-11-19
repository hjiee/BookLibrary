package com.hyden.ext

import java.text.DecimalFormat
import java.util.regex.Pattern


fun String.numberFormatter(): String {
    return DecimalFormat("#,###").let {
        it.format(this.toDouble())
    }
}

fun CharSequence.onlyNumber(): String {
    return "^*[^0-9]*+".toRegex().replace(this,"")
}

fun String.validationNickname() : Boolean {
    return Pattern.matches("^*[가-힣a-zA-Z0-9_]*+",this)
}
