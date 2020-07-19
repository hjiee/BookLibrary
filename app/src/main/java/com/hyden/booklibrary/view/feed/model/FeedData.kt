package com.hyden.booklibrary.view.feed.model

import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.model.Feed
import com.hyden.booklibrary.data.model.toFeed
import com.hyden.booklibrary.data.model.toLike
import com.hyden.booklibrary.data.model.toSharedInfo
import com.hyden.booklibrary.data.remote.network.response.BookItem
import com.hyden.booklibrary.data.remote.network.response.convertToBookEntity

data class FeedData(
    val feed : Feed?,
    var isExpanded: Boolean = false
)

// 데이터 파싱
fun convertToFeed(documents: Map<*, *>?): Feed {
    return documents?.run {
        Feed(
            get("sharedInfo").toSharedInfo(),
            convertToBook(get("bookEntity") as HashMap<*, *>),
            get("likesCount").toString().toLong(),
            get("likesInfo").toLike()
        )
    }?.toFeed()!!
}

fun convertToBook(documents: HashMap<*, *>): BookEntity {
    return documents?.run {
        BookItem(
            get("savaed") as Boolean? ?: false,
            get("liked") as Boolean? ?: false,
            get("shared") as Boolean? ?: false,
            get("chated") as Boolean? ?: false,
            get("bookNote").toString(),
            get("bookReviews").toString(),
            get("title").toString(),
            get("link").toString(),
            get("author").toString(),
            get("pubDate").toString(),
            get("description").toString(),
            get("isbn").toString(),
            get("isbn13").toString(),
            get("itemId").toString(),
            get("priceSales").toString(),
            get("priceStandard").toString(),
            get("mallType").toString(),
            get("stockStatus").toString(),
            get("mileage").toString(),
            get("cover").toString(),
            get("categoryId").toString(),
            get("categoryName").toString(),
            get("publisher").toString(),
            get("salesPoint").toString(),
            get("adult").toString(),
            get("fixedPrice").toString(),
            get("customerReviewRank").toString(),
            get("bestRank").toString()
        )
    }.convertToBookEntity()
}