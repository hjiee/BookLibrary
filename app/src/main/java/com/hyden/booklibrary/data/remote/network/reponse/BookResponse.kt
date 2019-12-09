package com.hyden.booklibrary.data.remote.network.reponse

import android.os.Parcel
import android.os.Parcelable

data class BookResponse(
    val query : String, // ":"QueryType=ITEMNEWSPECIAL;SearchTarget=book",
    val item : List<BookItems>?
)
