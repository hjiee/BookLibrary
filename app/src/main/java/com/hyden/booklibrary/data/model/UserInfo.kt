package com.hyden.booklibrary.data.model

import java.util.*

data class UserInfo(
    val email: String,
    val name: String,
    val nickName: String = "",
    val profile: String = ""
)