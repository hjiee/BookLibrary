package com.hyden.booklibrary.util

import androidx.databinding.BindingAdapter
import com.hyden.booklibrary.util.custom.ExpandableTextLayout

@BindingAdapter(value = ["textBinding"])
fun ExpandableTextLayout.textBinding(text: String) {
    setText(text)
}