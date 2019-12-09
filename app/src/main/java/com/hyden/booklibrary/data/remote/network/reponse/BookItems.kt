package com.hyden.booklibrary.data.remote.network.reponse

import android.os.Parcel
import android.os.Parcelable
import com.hyden.booklibrary.data.local.db.BookEntity

data class BookItems(
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
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(title)
        writeString(link)
        writeString(author)
        writeString(pubDate)
        writeString(description)
        writeString(isbn)
        writeString(isbn13)
        writeString(itemId)
        writeString(priceSales)
        writeString(priceStandard)
        writeString(mallType)
        writeString(stockStatus)
        writeString(mileage)
        writeString(cover)
        writeString(categoryId)
        writeString(categoryName)
        writeString(publisher)
        writeString(salesPoint)
        writeString(adult)
        writeString(fixedPrice)
        writeString(customerReviewRank)
        writeString(bestRank)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<BookItems> = object : Parcelable.Creator<BookItems> {
            override fun createFromParcel(source: Parcel): BookItems = BookItems(source)
            override fun newArray(size: Int): Array<BookItems?> = arrayOfNulls(size)
        }
    }
}

fun BookItems.toBookEntity(): BookEntity =
    BookEntity(
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
