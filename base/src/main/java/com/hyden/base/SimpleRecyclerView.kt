package com.hyden.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SimpleRecyclerView {
    abstract class SimpleRecyclerAdapter(
        private val layoutId : Int,
        private val list : List<String>
    ) : RecyclerView.Adapter<SimpleViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
            val holder =  object : SimpleViewHolder(layoutId,parent) { }
            return holder
        }

        override fun getItemCount(): Int = list.size

        override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
            holder.bind(list[position])
        }
    }

    abstract class SimpleViewHolder(
        layoutId: Int,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(layoutId,parent,false)
    ) {
        fun bind(list : String) {

        }
    }
}