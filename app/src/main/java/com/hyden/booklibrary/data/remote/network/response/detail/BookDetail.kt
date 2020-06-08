package com.hyden.booklibrary.data.remote.network.response.detail

import com.google.gson.annotations.SerializedName
import com.hyden.booklibrary.data.remote.network.response.Bookinfo

data class BookDetail(
    @SerializedName("author")
    val author: String,
    @SerializedName("bookinfo")
    val bookinfo: Bookinfo,
    @SerializedName("categoryId")
    val categoryId: Int,
    @SerializedName("categoryName")
    val categoryName: String,
    @SerializedName("cover")
    val cover: String,
    @SerializedName("creator")
    val creator: String,
    @SerializedName("customerReviewRank")
    val customerReviewRank: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("isbn")
    val isbn: String,
    @SerializedName("isbn13")
    val isbn13: String,
    @SerializedName("itemId")
    val itemId: Int,
    @SerializedName("link")
    val link: String,
    @SerializedName("mileage")
    val mileage: Int,
    @SerializedName("priceSales")
    val priceSales: Int,
    @SerializedName("priceStandard")
    val priceStandard: Int,
    @SerializedName("pubDate")
    val pubDate: String,
    @SerializedName("publisher")
    val publisher: String,
    @SerializedName("stockStatus")
    val stockStatus: String,
    @SerializedName("title")
    val title: String
)