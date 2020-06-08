package com.hyden.booklibrary.data.remote.network.response.detail

import com.google.gson.annotations.SerializedName
import com.hyden.booklibrary.data.remote.network.response.detail.BookDetail

data class BookDetailResponse(
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("item")
    val item: List<BookDetail>,
    @SerializedName("itemsPerPage")
    val itemsPerPage: Int,
    @SerializedName("link")
    val link: String,
    @SerializedName("pubDate")
    val pubDate: String,
    @SerializedName("query")
    val query: String,
    @SerializedName("searchCategoryId")
    val searchCategoryId: Int,
    @SerializedName("searchCategoryName")
    val searchCategoryName: String,
    @SerializedName("startIndex")
    val startIndex: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("version")
    val version: String
)