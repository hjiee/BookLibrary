package com.hyden.booklibrary.view.detail

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.hyden.base.BaseActivity
import com.hyden.booklibrary.R
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.databinding.ActivityDetailUnsavedBinding
import com.hyden.ext.loadUrl
import com.hyden.ext.numberFormatter
import com.hyden.util.ImageTransformType
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class UnSavedDetailActivity : BaseActivity<ActivityDetailUnsavedBinding>(R.layout.activity_detail_unsaved) {

    private val detailViewModel by viewModel<UnSavedDetailViewModel>()

    private val item by lazy { intent?.getParcelableExtra<BookEntity>(getString(R.string.book_info)) }
    private val type by lazy { intent?.getStringExtra(getString(R.string.book_detail_type)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel.bookInfo(item)
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
                        setOnClickListener { detailViewModel.deleteBook(item?.isbn13!!) }
                    }
                    else -> {
                        setOnClickListener { detailViewModel.bookInsert() }
                    }
                }
            }
            ibBack.setOnClickListener { finish() }
            ivBookCover.loadUrl(item?.cover,ImageTransformType.FIT)
            tvTitle.text = item?.title!!.split(" - ")[0] ?: ""
            if(item?.title!!.split(" - ").size > 1)
                tvSubtitle.text = item?.title!!.split(" - ")[1] ?: ""
            tvAuthor.text = getItem(item?.author!!.split(", "))
            tvPublisher.text = item?.publisher
            tvPrice.text = item?.priceSales?.numberFormatter()
            tvDescription.text = item?.description
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