package com.hyden.ext

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyden.base.BaseRecyclerView
import com.hyden.util.RecyclerItemDecoration
import java.util.Collections.replaceAll

@BindingAdapter(value = ["bindItems"])
fun RecyclerView.bindItems(items : List<Any>?) {
    items?.let {
        (adapter as? BaseRecyclerView.Adapter<Any,*>)?.run {
//            replaceAll(items)
            updateItems(items)
        }
    }
}

@BindingAdapter(value = ["bindDecoration"])
fun RecyclerView.bindDecoration(offset : Int) {
    addItemDecoration(RecyclerItemDecoration(offset))
}