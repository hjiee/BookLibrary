package com.hyden.booklibrary.view.feed.custom.adapter

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.TextView

class ExpandCollapseAnimation(
    private val tvView : TextView,
    private val startHeight : Int,
    private val endHeight : Int
) : Animation(), Animation.AnimationListener {
    private var initialHeight = 0
    private var targetHeight = 0

    init {
        setAnimationListener(this)
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        super.applyTransformation(interpolatedTime, t)

        val newHeight = ((endHeight - startHeight) * interpolatedTime + startHeight).toInt()
        tvView.maxHeight = newHeight
        tvView.layoutParams.height = newHeight
        tvView.requestLayout()
    }

    override fun onAnimationStart(animation: Animation?) {
        initialHeight = tvView.height
        tvView.measure(
            View.MeasureSpec.makeMeasureSpec(initialHeight, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        targetHeight = tvView.measuredHeight
    }

    override fun onAnimationRepeat(animation: Animation?) {

    }

    override fun onAnimationEnd(animation: Animation?) {
        tvView.requestLayout()
    }
}