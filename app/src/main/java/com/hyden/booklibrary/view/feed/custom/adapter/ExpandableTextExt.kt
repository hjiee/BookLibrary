package com.hyden.booklibrary.view.feed.custom.adapter

import androidx.databinding.BindingAdapter
import com.hyden.booklibrary.data.model.Feed
import com.hyden.booklibrary.view.feed.custom.ExpandableTextLayout
import com.hyden.booklibrary.view.feed.FeedViewModel


@BindingAdapter(value = ["bindUserName","bindText","bindFeedVm","bindFeedData"])
fun ExpandableTextLayout.bindText(userName: String?, text: String?,feedVm : FeedViewModel, feedData : Feed?) {
    if(text == "null" || text == null) {
        setText(userName,"",feedVm,feedData)
    } else {
        setText(userName,text,feedVm,feedData)
    }
}