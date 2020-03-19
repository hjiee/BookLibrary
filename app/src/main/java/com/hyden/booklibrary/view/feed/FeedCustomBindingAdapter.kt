package com.hyden.booklibrary.view.feed

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.hyden.booklibrary.R
import com.hyden.booklibrary.util.custom.ExpandableTextLayout

object FeedCustomBindingAdapter {

/*
    @JvmStatic
    @BindingAdapter("bind:checkSeleted")
    fun checkSeleted(imageView: ImageView, viewmodel: FeedViewModel) {
        imageView.isSelected= viewmodel.getFeedItem()?.let {
            feedViewModel.isContainsUser(it)
        } ?: false
    }*/


    @JvmStatic
    @BindingAdapter(value = ["bind:onClickMoreOption", "bind:pos"])
    fun onClickMoreOption(
        expandableLayout: ExpandableTextLayout,
        viewModel: FeedViewModel?,
        pos: Int
    ) {
        viewModel?.let { vm ->
            var textView: TextView? = expandableLayout.findViewById(R.id.tv_show_more)

            textView?.let {

                var onClickListener = object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        var state = viewModel.toggleStateFeedMoreOption(
                            pos,
                            vm.getStateExpandableState(pos)
                        )
                        expandableLayout.showMore(state)
                    }
                }
                textView.setOnClickListener(null)
                textView.setOnClickListener(onClickListener)
            }
            Log.e("${pos}", "뭐지 ${vm.getStateExpandableState(pos)}")
            var stateAfterClick = expandableLayout.showMore(vm.getStateExpandableState(pos))
        }
    }
}