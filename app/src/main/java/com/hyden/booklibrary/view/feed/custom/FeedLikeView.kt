package com.hyden.booklibrary.view.feed.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.hyden.booklibrary.R
import com.hyden.booklibrary.data.model.Feed
import com.hyden.booklibrary.view.feed.FeedViewModel
import com.hyden.ext.onlyNumber
import kotlinx.android.synthetic.main.recycler_item_feed.view.*

@BindingAdapter(value = ["bindFeedVm", "bindFeed"])
fun ConstraintLayout.bindLike(feedVm: FeedViewModel, feed: Feed) {
    feedVm.isLiked(feed.likesInfo.users) {
        iv_like.isSelected = true
        iv_like.setImageResource(R.drawable.ic_like_on)
    }

    iv_like.setOnClickListener(null)
    iv_like.setOnClickListener {
        iv_like.isSelected = iv_like.isSelected.not()
        feedVm.postLike(feed, iv_like.isSelected)
        if (iv_like.isSelected) {
            iv_like.setImageResource(R.drawable.ic_like_on)
            tv_like_count.text = String.format(resources.getString(R.string.like_count),tv_like_count.text.onlyNumber().toInt() + 1)
            feed.likesCount = feed.likesCount.plus(1)
        } else {
            iv_like.setImageResource(R.drawable.ic_like_off)
            tv_like_count.text = String.format(resources.getString(R.string.like_count),tv_like_count.text.onlyNumber().toInt() - 1)
            feed.likesCount = feed.likesCount.minus(1)
        }
    }
}