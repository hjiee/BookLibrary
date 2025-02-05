package com.hyden.booklibrary.view.detail.feed

import android.os.Bundle
import com.hyden.base.BaseActivity
import com.hyden.booklibrary.R
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.databinding.ActivityFeedDetailBinding
import com.hyden.ext.loadUrl
import com.hyden.util.LogUtil.LogW
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedDetailActivity : BaseActivity<ActivityFeedDetailBinding>(R.layout.activity_feed_detail) {

    private val viewModel by viewModel<FeedDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val feedData = intent.getParcelableExtra<BookEntity>(getString(R.string.key_feed_data))

        binding.apply {
            feedData?.let {
                viewModel.initData(feedData)
                ivBookCover.loadUrl(feedData.cover)
                tvContents.text = feedData.bookNote
            }
//            includeToolbar.tvTitle.text = "댓글"
        }
        LogW("$feedData")
    }

    override fun initBind() {

    }
}