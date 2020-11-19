package com.hyden.ext

import android.content.Intent
import android.view.View
import androidx.databinding.BindingAdapter
import com.hyden.base.BaseActivity
import com.hyden.view.webview.WebViewActivity
import com.hyden.view.webview.WebViewActivity.Companion.WEBVIEW_URL

@BindingAdapter(value = ["webviewUrl"], requireAll = false)
fun View.webViewUrl(url: String?) {
    url?.let {
        setOnClickListener {
            Intent(context, WebViewActivity::class.java).run {
                putExtra(WEBVIEW_URL,url)
                context.startActivity(this)
            }
        }
    }
}
