package com.hyden.booklibrary.view.feed.model

import com.hyden.booklibrary.data.model.Feed
import com.hyden.booklibrary.view.feed.FeedViewModel

data class FeedData(
    val feed : Feed?,
    var isExpanded: Boolean = false
)