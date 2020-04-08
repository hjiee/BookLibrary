package com.hyden.booklibrary.data.local.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hyden.booklibrary.util.ConstUtil.Companion.DATABASENAME_BOOK
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = DATABASENAME_BOOK)
data class BookEntity(
    var isLiked : Boolean? = false,
    var isShared : Boolean? = false,
    var isReviews : Boolean? = false,
    var bookNote : String? = "",
    var bookReviews : String? = "",
    val title: String? ="",
    val link: String? ="",
    val author: String? ="",
    val pubDate: String? ="",
    val description: String? ="",
    val isbn: String? ="",
    @PrimaryKey val isbn13: String,
    val itemId: String? ="",
    val priceSales: String? ="",
    val priceStandard: String? ="",
    val mallType: String? ="",
    val stockStatus: String? ="",
    val mileage: String? ="",
    val cover: String? ="",
    val categoryId: String? ="",
    val categoryName: String? ="",
    val publisher: String? ="",
    val salesPoint: String? ="",
    val adult: String? ="",
    val fixedPrice: String? ="",
    val customerReviewRank: String? ="",
    val bestRank : String? =""
) : Parcelable






















