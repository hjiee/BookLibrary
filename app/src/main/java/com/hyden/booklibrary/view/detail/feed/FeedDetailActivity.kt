package com.hyden.booklibrary.view.detail.feed

import android.os.Bundle
import com.hyden.base.BaseActivity
import com.hyden.booklibrary.R
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.databinding.ActivityFeedDetailBinding

class FeedDetailActivity : BaseActivity<ActivityFeedDetailBinding>(R.layout.activity_feed_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.getParcelableExtra<BookEntity>("")
    }

    override fun initBind() {

    }
}