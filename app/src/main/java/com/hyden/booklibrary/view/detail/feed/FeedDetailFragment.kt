package com.hyden.booklibrary.view.detail.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyden.base.BaseFragment
import com.hyden.booklibrary.R
import com.hyden.booklibrary.databinding.FragmentFeedDetailBinding

class FeedDetailFragment : BaseFragment<FragmentFeedDetailBinding>(R.layout.fragment_feed_detail) {

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initBind() {

    }

    companion object {
        fun newInstance() = FeedDetailFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}