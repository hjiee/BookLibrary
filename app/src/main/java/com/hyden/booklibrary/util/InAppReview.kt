package com.hyden.booklibrary.util

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import com.google.android.play.core.review.ReviewManagerFactory
import com.hyden.ext.showToast

class InAppReview(private val activity : Activity) {
    fun start(success : (() -> Unit)? = null, failure : (() -> Unit)? = null) {
        ReviewManagerFactory.create(activity).run {
            requestReviewFlow().addOnCompleteListener { request ->
                if(request.isSuccessful) {
                    launchReviewFlow(activity,request.result)
                    success?.invoke()
                } else {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse("market://details?id=com.hyden.booklibrary")
                        activity.startActivity(intent)
                    } catch (e : ActivityNotFoundException) {
                        activity.showToast("구글플레이 스토어가 설치 되어있지 않습니다")
                    }
                    failure?.invoke()
                }
            }
        }
    }
}