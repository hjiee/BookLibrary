package com.hyden.booklibrary.view.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyden.base.BaseFragment
import com.hyden.booklibrary.R
import com.hyden.booklibrary.databinding.FragmentHomeBinding
import com.hyden.booklibrary.databinding.FragmentLibraryBinding

class LibraryFragment : BaseFragment<FragmentLibraryBinding>(R.layout.fragment_library) {

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object {
        fun newInstance() = LibraryFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}