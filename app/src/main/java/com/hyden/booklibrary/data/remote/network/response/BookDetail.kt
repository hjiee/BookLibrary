package com.hyden.booklibrary.data.remote.network.response

data class BookDetail(
    val author: String,
    val bookinfo: Bookinfo,
    val categoryId: Int,
    val categoryName: String,
    val cover: String,
    val creator: String,
    val customerReviewRank: Int,
    val description: String,
    val isbn: String,
    val isbn13: String,
    val itemId: Int,
    val link: String,
    val mileage: Int,
    val priceSales: Int,
    val priceStandard: Int,
    val pubDate: String,
    val publisher: String,
    val stockStatus: String,
    val title: String
)