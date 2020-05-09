package com.hyden.booklibrary.data.remote.network.response

import android.os.Parcelable
import com.hyden.booklibrary.data.local.db.BookEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BookItem(
    var isSavaed: Boolean = false,
    var isLiked: Boolean = false,
    var isShared: Boolean = false,
    var isReviews: Boolean = false,
    var bookNote: String?,
    var bookReviews: String?,
    val title: String?,
    val link: String?,
    val author: String?,
    val pubDate: String?,
    val description: String?,
    val isbn: String?,
    val isbn13: String,
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

fun BookItem.convertToBookEntity(): BookEntity =
    BookEntity(
        isLiked = isLiked,
        isShared = isShared,
        isReviews = isReviews,
        bookNote = bookNote,
        bookReviews = bookReviews,
        title = title,
        link = link,
        author = author,
        pubDate = pubDate,
        description = description,
        isbn = isbn,
        isbn13 = isbn13!!,
        itemId = itemId,
        priceSales = priceSales,
        priceStandard = priceStandard,
        mallType = mallType,
        stockStatus = stockStatus,
        mileage = mileage,
        cover = cover,
        categoryId = categoryId,
        categoryName = categoryName,
        publisher = publisher,
        salesPoint = salesPoint,
        adult = adult,
        fixedPrice = fixedPrice,
        customerReviewRank = customerReviewRank,
        bestRank = bestRank
    )
