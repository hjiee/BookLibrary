package com.hyden.booklibrary.data.remote.network.response

import com.google.gson.annotations.SerializedName

data class BookResponse(
    @SerializedName("query")
    val query : String, // ":"QueryType=ITEMNEWSPECIAL;SearchTarget=book",
    @SerializedName("item")
    val item : List<BookItem>
)
