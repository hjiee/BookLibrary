package com.hyden.booklibrary.view.library

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import com.hyden.base.BaseFragment
import com.hyden.base.BaseItemsApdater
import com.hyden.booklibrary.R
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.remote.network.response.BookItem
import com.hyden.booklibrary.databinding.FragmentLibraryBinding
import com.hyden.booklibrary.databinding.ItemBookImageBinding
import com.hyden.booklibrary.util.longClickVibrate
import com.hyden.booklibrary.view.detail.mysaved.SavedDetailActivity
import com.hyden.ext.showSimpleDialog
import com.hyden.util.ConstValueUtil.Companion.ITEM_DECORATION
import com.hyden.util.ItemClickListener
import com.hyden.util.ItemLongClickListener
import com.hyden.util.RecyclerItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel


class LibraryFragment : BaseFragment<FragmentLibraryBinding>(R.layout.fragment_library) {

    private val libraryViewModel by viewModel<LibraryViewModel>()

    private val itemClickListener by lazy {
        object : ItemClickListener {
            override fun <T> onItemClick(item: T) {
                when (item) { is BookItem ->
                    Intent(activity, SavedDetailActivity::class.java).apply {
                        putExtra(getString(R.string.book_info), item)
                        startActivity(this)
                        activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    }
                }
            }
        }
    }

    private val itemLongClickListener by lazy {
        object : ItemLongClickListener {
            override fun <T> onItemLongClick(item: T): Boolean {
                when (item) {
                    is BookEntity -> {
                        longClickVibrate()
                        context?.showSimpleDialog(getString(R.string.are_you_delete_book_info)) { deleteBook(item.isbn13) }
                    }
                }
                return true
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        libraryViewModel.loadBook()
    }

    override fun initBind() {
        binding.apply {
            vm = libraryViewModel
            rvBookself.apply {
                adapter = BaseItemsApdater(R.layout.item_book_image,BR.book,itemClickListener,itemLongClickListener)
                addItemDecoration(RecyclerItemDecoration(ITEM_DECORATION))
            }
            srvlRefresh.apply {
                setOnRefreshListener {
                    isRefreshing = false
                }
            }
        }
    }

    private fun deleteBook(isbn13 : String) {
        libraryViewModel.deleteBook(isbn13)
    }


    companion object {
        fun newInstance() = LibraryFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}