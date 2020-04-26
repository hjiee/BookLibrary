package com.hyden.booklibrary.view.common

import android.view.View
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView

@BindingAdapter(value = ["loading"])
fun LottieAnimationView.animation(isShowing : Boolean) {
    when(isShowing) {
        true -> {
            visibility = View.VISIBLE
            playAnimation()

        }
        false-> {
            visibility = View.GONE
            cancelAnimation()
        }
    }
}