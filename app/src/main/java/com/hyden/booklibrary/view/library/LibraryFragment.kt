package com.hyden.booklibrary.view.library

import android.content.Context.VIBRATOR_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.library.baseAdapters.BR
import com.hyden.base.BaseFragment
import com.hyden.base.BaseRecyclerView
import com.hyden.booklibrary.R
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.remote.network.reponse.BookResponse
import com.hyden.booklibrary.databinding.FragmentLibraryBinding
import com.hyden.booklibrary.databinding.RecyclerItemLibraryBinding
import com.hyden.booklibrary.util.longClickVibrate
import com.hyden.booklibrary.view.detail.DetailActivity
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
                        AlertDialog.Builder(context!!,R.style.DeleteDialog).apply {
                            setTitle(getString(R.string.app_name))
                            setMessage("책에 저장된 정보가 삭제됩니다.\n정말 삭제하시겠습니까?")
                            setPositiveButton("삭제") { _,_ ->
                                deleteBook(item.isbn13)
                            }
                            setNegativeButton("취소") { _,_ ->

                            }
                        }.show()

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
                    BaseRecyclerView.Adapter<BookResponse, RecyclerItemLibraryBinding, BookEntity>(
                        layoutId = R.layout.recycler_item_library,
                        bindingVariableId = BR.response,
                        clickItemEvent = itemClickListener,
                        longClickItemEvent = itemLongClickListener
                    ) {}

            }
            rvlRefresh.apply {
                setOnRefreshListener {
                    isRefreshing = false
                }
            }
        }
    }

    private fun moveToActivity(item : BookEntity) {
        Intent(activity, DetailActivity::class.java).apply {
            putExtra(getString(R.string.book_info), item)
            putExtra(getString(R.string.book_detail_type), getString(R.string.book_detail_type_value))
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