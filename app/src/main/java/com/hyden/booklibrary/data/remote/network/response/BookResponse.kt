package com.hyden.booklibrary.data.remote.network.response

data class BookResponse(
    val query : String, // ":"QueryType=ITEMNEWSPECIAL;SearchTarget=book",
    val item : List<BookItem>
)
