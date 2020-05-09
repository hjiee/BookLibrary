package com.hyden.booklibrary.view.detail

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.hyden.base.BaseActivity
import com.hyden.booklibrary.R
import com.hyden.booklibrary.data.remote.network.response.BookItem
import com.hyden.booklibrary.databinding.ActivityDetailUnsavedBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UnSavedDetailActivity : BaseActivity<ActivityDetailUnsavedBinding>(R.layout.activity_detail_unsaved) {

    private val detailViewModel by viewModel<UnSavedDetailViewModel>()

    private val book by lazy { intent?.getParcelableExtra<BookItem>(getString(R.string.book_info)) }
    private val type by lazy { intent?.getStringExtra(getString(R.string.book_detail_type)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel.bookInfo(book)
        detailViewModel.isContain.observe(
            this@UnSavedDetailActivity,
            Observer {
                when(it) {
                    true -> Toast.makeText(this@UnSavedDetailActivity, "책장에 담았습니다.", Toast.LENGTH_SHORT).show()
                    false -> Toast.makeText(this@UnSavedDetailActivity, "이미 책장에 있습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        )
        detailViewModel.isDelete.observe(this,
            Observer {
                when(it) {
                    true -> {
                        Toast.makeText(this@UnSavedDetailActivity, "삭제 하였습니다.", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    false -> Toast.makeText(this@UnSavedDetailActivity, "삭제에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun initBind() {
        binding.apply {
            vm = detailViewModel
            ibDownload.apply {
                when(type) {
                    getString(R.string.book_detail_type_value) -> {
                        setBackgroundResource(R.drawable.ic_delete)
                        setOnClickListener { detailViewModel.deleteBook(book?.isbn13!!) }
                    }
                    else -> {
                        setOnClickListener { detailViewModel.bookInsert() }
                    }
                }
            }
            book = this@UnSavedDetailActivity.book
            ibBack.setOnClickListener { finish() }
        }
    }

    fun getItem(item : List<String>) : String {
        var buffer = StringBuffer()
        item.map {
            buffer.append("$it\n")
        }
        return buffer.substring(0,buffer.length-1)
    }
}