package com.hyden.booklibrary.view.library

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.library.baseAdapters.BR
import com.google.firebase.firestore.FirebaseFirestore
import com.hyden.base.BaseFragment
import com.hyden.base.BaseRecyclerView
import com.hyden.booklibrary.R
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.remote.network.reponse.BookResponse
import com.hyden.booklibrary.databinding.FragmentLibraryBinding
import com.hyden.booklibrary.databinding.RecyclerItemLibraryBinding
import com.hyden.booklibrary.util.deleteBook
import com.hyden.booklibrary.util.longClickVibrate
import com.hyden.booklibrary.view.detail.SavedDetailActivity
import com.hyden.booklibrary.view.detail.UnSavedDetailActivity
import com.hyden.util.ItemClickListener
import com.hyden.util.ItemLongClickListener
import org.koin.android.ext.android.inject


class LibraryFragment : BaseFragment<FragmentLibraryBinding>(R.layout.fragment_library) {

    private val libraryViewModel by inject<LibraryViewModel>()

    private val itemClickListener by lazy {
        object : ItemClickListener {
            override fun <T> onItemClick(item: T) {
                when (item) { is BookEntity -> moveToActivity(item) }
            }
        }
    }

    private val itemLongClickListener by lazy {
        object : ItemLongClickListener {
            override fun <T> onItemLongClick(item: T): Boolean {
                when (item) {
                    is BookEntity -> {
                        longClickVibrate()
                        context?.deleteBook { deleteBook(item.isbn13) }
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
                adapter = object :
                    BaseRecyclerView.Adapter<BookResponse, RecyclerItemLibraryBinding>(
                        layoutId = R.layout.recycler_item_library,
                        bindingVariableId = BR.response,
                        clickItemEvent = itemClickListener,
                        longClickItemEvent = itemLongClickListener
                    ) {}

            }
            srvlRefresh.apply {
                setOnRefreshListener {
                    isRefreshing = false
                }
            }
        }
    }

    private fun moveToActivity(item : BookEntity) {
        Intent(activity, SavedDetailActivity::class.java).apply {
            putExtra(getString(R.string.book_info), item)
            startActivity(this)
            activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
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