package com.hyden.ext

import java.util.*


fun Long.elapsedTimeFormatter(createdDate : Date) : String {
    var elapsedTime = ""
    // 경과 시간이 1분보다 작을때. 경과시간 / 60000 < 1
    if(this / 60000 < 1) {
        elapsedTime = (this / 1000).toString() + "초 전"
    }
    // 경과 시간이 1시간보다 작을때. 경과시간 / 3600000 < 1
    else if(this / 3600000 < 1) {
        elapsedTime = (this / 60000).toString() + "분 전"
    }
    // 경과 시간이 1일보다 작을때. 경과시간 / 86400000 < 1
    else if(this / 86400000 < 1) {
        elapsedTime = (this / 3600000).toString() + "시간 전"
    }
    // 경과 시간이 1개월보다 작을때. 경과시간 / 2678400000 < 1
    else if(this / 2678400000 < 1) {
        elapsedTime = (this / 86400000).toString() + "일 전"
    }
    // 경과 시간이 1년보다 작을때. 경과시간 / 31536000000 < 1
    else {
        elapsedTime = createdDate.toDateFormat()
    }
//    else if(this / 31536000000 < 1) {
////        elapsedTime = (this / 2678400000).toString() + " 개월전"
//    }
//    else {
////        elapsedTime = (this / 31536000000).toString() + " 년전"
//    }


    return elapsedTime
}