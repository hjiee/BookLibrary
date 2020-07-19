package com.hyden.booklibrary.view.myshared

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import com.hyden.base.BaseFragment
import com.hyden.base.BaseItemsApdater
import com.hyden.booklibrary.R
import com.hyden.booklibrary.data.remote.network.response.BookItem
import com.hyden.booklibrary.databinding.FragmentMySharedBinding
import com.hyden.booklibrary.view.detail.mysaved.SavedDetailActivity
import com.hyden.ext.moveToActivity
import com.hyden.util.ItemClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class MySharedBookFragment : BaseFragment<FragmentMySharedBinding>(R.layout.fragment_my_shared) {

    private val viewModel by viewModel<MySharedBookViewMiodel>()
    private val itemClickListener by lazy {
        object : ItemClickListener {
            override fun <T> onItemClick(item: T) {
                when (item) {
                    is BookItem -> {
                        Intent(context, SavedDetailActivity::class.java).run {
                            putExtra(getString(R.string.book_info), item)
                            moveToActivity(this)
                        }
                    }
                }
            }
        }
    }
    private val adapter by lazy {
        BaseItemsApdater(
            R.layout.item_book_image,
            BR.book,
            itemClickListener
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            vm = this@MySharedBookFragment.viewModel
            adapter = this@MySharedBookFragment.adapter
        }
        viewModel.loadBook()
    }

    override fun initBind() {
    }

    companion object {
        fun newInstance() = MySharedBookFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}