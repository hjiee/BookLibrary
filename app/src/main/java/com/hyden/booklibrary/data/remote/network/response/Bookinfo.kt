package com.hyden.booklibrary.data.remote.network.response

import com.google.gson.annotations.SerializedName

data class Bookinfo(
    @SerializedName("authors")
    val authors: List<Author>,
    @SerializedName("ebookList")
    val ebookList: List<Ebook>,
    @SerializedName("itemPage")
    val itemPage: Int,
    @SerializedName("letslookimg")
    val letslookimg: List<String>,
    @SerializedName("originalTitle")
    val originalTitle: String,
    @SerializedName("subTitle")
    val subTitle: String,
    @SerializedName("toc")
    val toc: String
)