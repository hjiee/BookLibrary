package com.hyden.booklibrary.data.remote.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.hyden.booklibrary.data.local.db.BookEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BookItem(
    @SerializedName("isSavaed")
    var isSavaed: Boolean = false,
    @SerializedName("isLiked")
    var isLiked: Boolean = false,
    @SerializedName("isShared")
    var isShared: Boolean = false,
    @SerializedName("isReviews")
    var isReviews: Boolean = false,
    @SerializedName("bookNote")
    var bookNote: String?,
    @SerializedName("bookReviews")
    var bookReviews: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("link")
    val link: String?,
    @SerializedName("author")
    val author: String?,
    @SerializedName("pubDate")
    val pubDate: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("isbn")
    val isbn: String?,
    @SerializedName("isbn13")
    val isbn13: String,
    @SerializedName("itemId")
    val itemId: String?,
    @SerializedName("priceSales")
    val priceSales: String?,
    @SerializedName("priceStandard")
    val priceStandard: String?,
    @SerializedName("mallType")
    val mallType: String?,
    @SerializedName("stockStatus")
    val stockStatus: String?,
    @SerializedName("mileage")
    val mileage: String?,
    @SerializedName("cover")
    val cover: String?,
    @SerializedName("categoryId")
    val categoryId: String?,
    @SerializedName("categoryName")
    val categoryName: String?,
    @SerializedName("publisher")
    val publisher: String?,
    @SerializedName("salesPoint")
    val salesPoint: String?,
    @SerializedName("adult")
    val adult: String?,
    @SerializedName("fixedPrice")
    val fixedPrice: String?,
    @SerializedName("customerReviewRank")
    val customerReviewRank: String?,
    @SerializedName("bestRank")
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
