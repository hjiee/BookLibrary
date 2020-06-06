package com.hyden.booklibrary.util

import android.content.Context
import androidx.databinding.BindingAdapter
import com.google.android.gms.ads.*
import com.hyden.booklibrary.BuildConfig
import kotlin.random.Random

/**
 * 전면 광고
 */
class AdsUtil(val context: Context) {
    private var interstitialAd: InterstitialAd = InterstitialAd(context)

    fun loadAds(closed: (() -> Unit)? = null) {
        interstitialAd.adUnitId = BuildConfig.ADMOB_FULL
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

            override fun onAdImpression() {
                super.onAdImpression()
            }

            override fun onAdLeftApplication() {
                super.onAdLeftApplication()
            }

            override fun onAdClicked() {
                super.onAdClicked()
            }

            override fun onAdFailedToLoad(p0: Int) {
                super.onAdFailedToLoad(p0)
                closed?.invoke()
            }

            override fun onAdOpened() {
                super.onAdOpened()
            }
        }
    }

    private fun showAd(closed: (() -> Unit)? = null) {
        if (interstitialAd.isLoaded) {
            val rand = Random(System.nanoTime()).nextInt(Int.MAX_VALUE) % 3
            if (rand == 0) {
                interstitialAd.show()
            } else {
                closed?.invoke()
            }
        }
    }
}

enum class AdsType() {
    BANNER, // 배너 광고
    FULL, // 전면 광고
    FULL_VIDEO, // 전면 동영상 광고
    REWORD_VIDEO, // 보상형 동영상 광고
    NATIVE, // 네이티브 광고 고급형
    NAITVE_VIDEO // 네이티브 동영상 광고 고급형
}

@BindingAdapter(value = ["adId"])
fun com.google.android.gms.ads.AdView.bindAdId(type: AdsType) {
    when (type) {
        AdsType.BANNER -> {
            this.adSize = AdSize.BANNER
            this.adUnitId = BuildConfig.ADMOB_BANNER
        }
        AdsType.FULL -> {

        }
        AdsType.FULL_VIDEO -> {

        }
        AdsType.REWORD_VIDEO -> {

        }
        AdsType.NATIVE -> {

        }
        AdsType.NAITVE_VIDEO -> {

        }
    }
    loadAd(AdRequest.Builder().build())
}
