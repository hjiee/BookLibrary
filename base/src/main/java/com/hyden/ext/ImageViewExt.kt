package com.hyden.ext

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.hyden.base.R
import com.hyden.util.ImageTransformType
import com.hyden.util.LogUtil.LogD
import com.hyden.util.LogUtil.LogW
import com.hyden.util.toPx
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

//@BindingAdapter(value = ["loadUrl"])
//fun ImageView.loadUrl(url: String?) {
//    url?.let {
//        Glide.with(this)
//            .load(it)
//            .override(450,650)
//            .apply(RequestOptions.fitCenterTransform().centerCrop())
//            .into(this)
//    }
//}
@BindingAdapter(value = ["loadUrl", "tranformType","radius","loadingDuration"], requireAll = false)
fun ImageView.loadUrl(
    url: String?,
    type: ImageTransformType? = null,
    radius : Int? = 14,
    loadingDuration : Int = 500
) {
    url?.let { strUrl ->
        Glide.with(this)
            .load(strUrl)
//            .placeholder(R.color.colorLightBlack3)
//            .listener(createLoggerListener(strUrl))
            .error(R.drawable.book)
            .apply {
                when (type) {
                    ImageTransformType.ROUND -> {
                        val multiTransformation = MultiTransformation(
                            CenterCrop(),
                            FitCenter(),
                            radius?.toFloat()?.let { RoundedCornersTransformation(it.toPx(context), 0) } ?: RoundedCornersTransformation(14f.toPx(context),0)
                        )
                        // 이미지 로딩 애니메이션
                        transition(DrawableTransitionOptions.withCrossFade(loadingDuration))
                        apply(RequestOptions.bitmapTransform(multiTransformation))
                    }
                    ImageTransformType.FIT -> {
                        override(Target.SIZE_ORIGINAL)
                        val multiTransformation = MultiTransformation<Bitmap>(
                            CenterCrop(),
                            FitCenter()
                        )
//                        apply(RequestOptions.fitCenterTransform().centerCrop())
                        transition(DrawableTransitionOptions.withCrossFade(500))
                        apply(RequestOptions.bitmapTransform(multiTransformation))
                    }
                    ImageTransformType.CIRCLE -> {
                        thumbnail(0.5f)
                        apply(RequestOptions.circleCropTransform())

                    }
                    else -> apply(RequestOptions.fitCenterTransform().centerCrop())
                }

            }.into(this)
    }

}
fun ImageView.loadBitmap(bitmap: Bitmap?) {
    bitmap?.let {
        Glide.with(this)
            .load(bitmap)
//            .listener(createLoggerListener("test"))
            .error(R.drawable.book)
            .apply {
                val multiTransformation = MultiTransformation<Bitmap>(
                    CenterCrop(),
                    FitCenter(),
                    CircleCrop()
                )
                // 이미지 로딩 애니메이션
                transition(DrawableTransitionOptions.withCrossFade(1000))
                apply(RequestOptions.bitmapTransform(multiTransformation))
            }.into(this)
    }

}

/**
 * 불러온 이미지의 사이즈를 구한다.
 */
private fun createLoggerListener(name: String): RequestListener<Drawable> {
    return object : RequestListener<Drawable> {
        override fun onLoadFailed(e: GlideException?,
                                  model: Any?,
                                  target: com.bumptech.glide.request.target.Target<Drawable>?,
                                  isFirstResource: Boolean): Boolean {
            return false
        }

        override fun onResourceReady(resource: Drawable?,
                                     model: Any?,
                                     target: com.bumptech.glide.request.target.Target<Drawable>?,
                                     dataSource: DataSource?,
                                     isFirstResource: Boolean): Boolean {
            if (resource is BitmapDrawable) {
                val bitmap = resource.bitmap
                LogD(
                    String.format("\nReady %s\nbitmap %d bytes\nsize: %d x %d",
                        name,
                        bitmap.byteCount,
                        bitmap.width,
                        bitmap.height),"glideImage")
            }
            return false
        }
    }
}