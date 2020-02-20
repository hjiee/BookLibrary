package com.hyden.booklibrary.util

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
    private var isAnimating = false

    init {
        setAnimationListener(this)
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        super.applyTransformation(interpolatedTime, t)

        val newHeight = ((endHeight - startHeight) * interpolatedTime + startHeight).toInt()
        tvView.maxHeight = newHeight
        tvView.layoutParams.height = newHeight
        tvView.requestLayout()

        if (isAnimating) {
//            val newHeight = ((endHeight - startHeight) * interpolatedTime + startHeight).toInt()
//            tvView.maxHeight = newHeight
//            tvView.layoutParams.height = newHeight
//            tvView.requestLayout()
//            view.layoutParams.height =
//                (((targetHeight - initialHeight) * interpolatedTime) + initialHeight).toInt()
//            view.requestLayout()
        }
    }

    override fun onAnimationStart(animation: Animation?) {
        initialHeight = tvView.height
        tvView.measure(
            View.MeasureSpec.makeMeasureSpec(initialHeight, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        targetHeight = tvView.measuredHeight
        isAnimating = true
    }

    override fun onAnimationRepeat(animation: Animation?) {

    }

    override fun onAnimationEnd(animation: Animation?) {
        isAnimating = false
        tvView.requestLayout()
    }
}