package com.hyden.booklibrary.data.remote.network.response

import com.google.gson.annotations.SerializedName

data class Ebook(
    @SerializedName("isbn")
    val isbn: String,
    @SerializedName("itemId")
    val itemId: Int,
    @SerializedName("link")
    val link: String,
    @SerializedName("priceSales")
    val priceSales: Int
)