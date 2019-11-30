package com.hyden.booklibrary.data.remote.network.reponse

/**
 * title : 제목
 * cover : 표자
 */
data class BookResponse(
    val item : List<BookItems>
)
data class BookItems(
    val title : String,
    val cover : String
)