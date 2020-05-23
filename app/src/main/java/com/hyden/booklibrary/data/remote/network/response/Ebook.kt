package com.hyden.booklibrary.data.remote.network.response

data class Ebook(
    val isbn: String,
    val itemId: Int,
    val link: String,
    val priceSales: Int
)