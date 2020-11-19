package com.hyden.util

interface ItemClickListener {
    fun <T> onItemClick(item : T)
}

interface ItemLongClickListener {
    fun <T> onItemLongClick(item : T) : Boolean?
}