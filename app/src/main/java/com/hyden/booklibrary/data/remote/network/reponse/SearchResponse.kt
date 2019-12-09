package com.hyden.booklibrary.data.remote.network.reponse

import android.os.Parcel
import android.os.Parcelable

data class SearchResponse(
    val item : List<BookItems>?
)