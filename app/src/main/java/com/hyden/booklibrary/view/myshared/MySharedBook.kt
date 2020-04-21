package com.hyden.booklibrary.view.myshared

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyden.base.BaseFragment
import com.hyden.booklibrary.R
import com.hyden.booklibrary.databinding.FragmentMySharedBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MySharedBook : BaseFragment<FragmentMySharedBinding>(R.layout.fragment_my_shared) {

    private val viewModel by viewModel<MySharedBookViewMiodel>()

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            vm = viewModel
        }
        viewModel.loadBook()
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