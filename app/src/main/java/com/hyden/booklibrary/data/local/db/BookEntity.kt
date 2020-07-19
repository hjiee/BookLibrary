package com.hyden.booklibrary.data.local.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hyden.booklibrary.data.remote.network.response.BookItem
import com.hyden.booklibrary.util.ConstUtil.Companion.DATABASENAME_BOOK
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = DATABASENAME_BOOK)
data class BookEntity(
    var isLiked: Boolean = false,
    var isShared: Boolean = false,
    var isReviews: Boolean = false,
    var bookNote: String? = null,
    var bookReviews: String? = null,
    val title: String? = null,
    val link: String? = null,
    val author: String? = null,
    val pubDate: String? = null,
    val description: String? = null,
    val isbn: String? = null,
    @PrimaryKey val isbn13: String,
    val itemId: String? = null,
    val priceSales: String? = null,
    val priceStandard: String? = null,
    val mallType: String? = null,
    val stockStatus: String? = null,
    val mileage: String? = null,
    val cover: String? = null,
    val categoryId: String? = null,
    val categoryName: String? = null,
    val publisher: String? = null,
    val salesPoint: String? = null,
    val adult: String? = null,
    val fixedPrice: String? = null,
    val customerReviewRank: String? = null,
    val bestRank: String? = null
) : Parcelable

fun List<BookEntity>.convertToBookItems(): List<BookItem> {
    val items = arrayListOf<BookItem>()
    this.forEach {
        items.add(
            BookItem(
                isLiked = it.isLiked,
                isShared = it.isShared,
                isReviews = it.isReviews,
                bookNote = it.bookNote,
                bookReviews = it.bookReviews,
                title = it.title,
                link = it.link,
                author = it.author,
                pubDate = it.pubDate,
                description = it.description,
                isbn = it.isbn,
                isbn13 = it.isbn13,
                itemId = it.itemId,
                priceSales = it.priceSales,
                priceStandard = it.priceStandard,
                mallType = it.mallType,
                stockStatus = it.stockStatus,
                mileage = it.mileage,
                cover = it.cover,
                categoryId = it.categoryId,
                categoryName = it.categoryName,
                publisher = it.publisher,
                salesPoint = it.salesPoint,
                adult = it.adult,
                fixedPrice = it.fixedPrice,
                customerReviewRank = it.customerReviewRank,
                bestRank = it.bestRank
            )
        )
    }
    return items
}

fun BookEntity.convertToBookItem() : BookItem {
    return BookItem(
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
        isbn13 = isbn13,
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
}