package com.hyden.booklibrary.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyden.base.BaseFragment
import com.hyden.booklibrary.R
import com.hyden.booklibrary.databinding.FragmentHomeBinding
import com.hyden.booklibrary.databinding.FragmentSearchBinding

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object {
        fun newInstance() = SearchFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}