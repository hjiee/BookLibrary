package com.hyden.booklibrary.view.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.hyden.base.BaseActivity
import com.hyden.booklibrary.R
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.databinding.ActivityDetailBinding
import com.hyden.ext.loadUrl
import com.hyden.util.ImageTransformType
import org.koin.android.ext.android.inject

class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {

    private val detailViewModel by inject<DetailViewModel>()

    private val item by lazy { intent?.getParcelableExtra<BookEntity>(getString(R.string.book_info)) }
    private val type by lazy { intent?.getStringExtra(getString(R.string.book_detail_type)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel.bookInfo(item)
        detailViewModel.isContain.observe(
            this@DetailActivity,
            Observer {
                when(it) {
                    true -> Toast.makeText(this@DetailActivity, "책장에 담았습니다.", Toast.LENGTH_SHORT).show()
                    false -> Toast.makeText(this@DetailActivity, "이미 책장에 있습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        )
        detailViewModel.isDelete.observe(this,
            Observer {
                when(it) {
                    true -> {
                        Toast.makeText(this@DetailActivity, "삭제 하였습니다.", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    false -> Toast.makeText(this@DetailActivity, "삭제에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
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
            tvPublisher.text = item?.publisher!!
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