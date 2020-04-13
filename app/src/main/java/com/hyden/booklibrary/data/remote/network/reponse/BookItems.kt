package com.hyden.booklibrary.data.remote.network.reponse

import android.os.Parcelable
import com.hyden.booklibrary.data.local.db.BookEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BookItems(
    var isSavaed: Boolean = false,
    var isLiked: Boolean = false,
    var isShared: Boolean = false,
    var isChated: Boolean = false,
    var bookNote: String?,
    var bookReviews: String?,
    val title: String?,
    val link: String?,
    val author: String?,
    val pubDate: String?,
    val description: String?,
    val isbn: String?,
    val isbn13: String?,
    val itemId: String?,
    val priceSales: String?,
    val priceStandard: String?,
    val mallType: String?,
    val stockStatus: String?,
    val mileage: String?,
    val cover: String?,
    val categoryId: String?,
    val categoryName: String?,
    val publisher: String?,
    val salesPoint: String?,
    val adult: String?,
    val fixedPrice: String?,
    val customerReviewRank: String?,
    val bestRank: String?
) : Parcelable

fun BookItems.toBookEntity(): BookEntity =
    BookEntity(
        isLiked,
        isShared,
        isChated,
        bookNote,
        bookReviews,
        title,
        link,
        author,
        pubDate,
        description,
        isbn,
        isbn13!!,
        itemId,
        priceSales,
        priceStandard,
        mallType,
        stockStatus,
        mileage,
        cover,
        categoryId,
        categoryName,
        publisher,
        salesPoint,
        adult,
        fixedPrice,
        customerReviewRank,
        bestRank
    )
