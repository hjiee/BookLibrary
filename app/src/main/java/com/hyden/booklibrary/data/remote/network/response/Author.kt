package com.hyden.booklibrary.data.remote.network.response

import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("authorType")
    val authorType: String,
    @SerializedName("authorid")
    val authorid: Int,
    @SerializedName("desc")
    val desc: String,
    @SerializedName("name")
    val name: String
)