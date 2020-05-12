package com.hyden.booklibrary.data.remote.network.response

data class Bookinfo(
    val authors: List<Author>,
    val ebookList: List<Any>,
    val itemPage: Int,
    val letslookimg: List<String>,
    val originalTitle: String,
    val subTitle: String,
    val toc: String
)