package com.hyden.booklibrary.view.myshared

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyden.base.BaseFragment
import com.hyden.booklibrary.R
import com.hyden.booklibrary.databinding.FragmentMySharedBinding

class MySharedBook : BaseFragment<FragmentMySharedBinding>(R.layout.fragment_my_shared) {

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initBind() {

    }

    companion object {
        fun newInstance() = MySharedBook().apply {
            arguments = Bundle().apply {

            }
        }
    }
}