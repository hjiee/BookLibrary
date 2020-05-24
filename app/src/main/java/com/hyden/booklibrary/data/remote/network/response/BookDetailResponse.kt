package com.hyden.booklibrary.data.remote.network.response

data class BookDetailResponse(
    val imageUrl: String,
    val item: List<BookDetail>,
    val itemsPerPage: Int,
    val link: String,
    val pubDate: String,
    val query: String,
    val searchCategoryId: Int,
    val searchCategoryName: String,
    val startIndex: Int,
    val title: String,
    val totalResults: Int,
    val version: String
)