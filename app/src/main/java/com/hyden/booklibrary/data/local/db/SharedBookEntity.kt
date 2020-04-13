package com.hyden.booklibrary.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hyden.booklibrary.util.ConstUtil.Companion.DATABASENAME_SHARED
import java.util.*

@Entity(tableName = DATABASENAME_SHARED)
data class SharedBookEntity(
    var isLiked : Boolean = false,
    var isShared : Boolean = false,
    var isReviews : Boolean = false,
    var bookNote : String? = null,
    var bookReviews : String? = null,
    val title: String? =null,
    val link: String? =null,
    val author: String? =null,
    val pubDate: String? =null,
    val description: String? =null,
    val isbn: String? =null,
    @PrimaryKey val isbn13: String,
    val itemId: String? =null,
    val priceSales: String? =null,
    val priceStandard: String? =null,
    val mallType: String? =null,
    val stockStatus: String? =null,
    val mileage: String? =null,
    val cover: String? =null,
    val categoryId: String? =null,
    val categoryName: String? =null,
    val publisher: String? =null,
    val salesPoint: String? =null,
    val adult: String? =null,
    val fixedPrice: String? =null,
    val customerReviewRank: String? =null,
    val bestRank : String? =null
)