package com.hyden.booklibrary.util

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.hyden.booklibrary.R
import com.hyden.util.LogUtil.LogW
import kotlin.random.Random

/**
 * 전면 광고
 */
class AdsUtil(val context : Context) {
    private var interstitialAd :InterstitialAd = InterstitialAd(context)

    fun loadAds(closed : (() -> Unit)? = null) {
        interstitialAd.adUnitId = context.getString(R.string.ad_test_full)
        interstitialAd.loadAd(AdRequest.Builder().build())
        interstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                super.onAdClosed()
                closed?.invoke()
            }
            override fun onAdLoaded() {
                super.onAdLoaded()
                showAd(closed)
            }
        }
    }

    private fun showAd(closed : (() -> Unit)? = null) {
        if (interstitialAd.isLoaded) {
            val rand = Random(System.nanoTime()).nextInt(Int.MAX_VALUE) % 3
            if(rand == 0) {
                interstitialAd.show()
            } else {
                closed?.invoke()
            }
        }
    }
}
