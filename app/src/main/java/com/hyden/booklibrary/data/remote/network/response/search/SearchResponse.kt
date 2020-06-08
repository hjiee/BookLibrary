package com.hyden.booklibrary.data.remote.network.response.search

import com.google.gson.annotations.SerializedName
import com.hyden.booklibrary.data.remote.network.response.BookItem

data class SearchResponse(
    @SerializedName("item")
    val item : List<BookItem>?
)