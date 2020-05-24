package com.hyden.booklibrary.view.detail.mysaved

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.hyden.base.BaseActivity
import com.hyden.booklibrary.R
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.remote.network.response.BookItem
import com.hyden.booklibrary.databinding.ActivityDetailSavedBinding
import com.hyden.booklibrary.util.ConstUtil.Companion.BOOK_NOTE_REQUEST_CODE
import com.hyden.booklibrary.util.dialogBookInfo
import com.hyden.booklibrary.view.feed.FeedViewModel
import com.hyden.booklibrary.view.note.NoteActivity
import com.hyden.ext.showSimpleDialog
import com.hyden.ext.isTimeAutomatic
import com.hyden.ext.loadUrl
import com.hyden.ext.moveToActivityForResult
import com.hyden.util.ImageTransformType
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SavedDetailActivity : BaseActivity<ActivityDetailSavedBinding>(R.layout.activity_detail_saved) {

    private val savedDetailViewModel by viewModel<SavedDetailViewModel>()
    private val feedViewModel by inject<FeedViewModel>()

    val item by lazy { intent?.getParcelableExtra<BookItem>(getString(R.string.book_info)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item?.isbn13?.let {
            savedDetailViewModel.loadBookDetail(it)
        }
    }
    override fun onResume() {
        super.onResume()
        savedDetailViewModel.bookReLoad(item?.isbn13!!)
    }

    override fun observing() {
        super.observing()

        savedDetailViewModel.isContain.observe(
            this@SavedDetailActivity,
            Observer {
                when (it) {
                    true -> Toast.makeText(
                        this@SavedDetailActivity,
                        "책장에 담았습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    false -> Toast.makeText(
                        this@SavedDetailActivity,
                        "이미 책장에 있습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
        // 삭제
        savedDetailViewModel.isDelete.observe(this,
            Observer {
                when (it) {
                    true -> {
                        Toast.makeText(this@SavedDetailActivity, "삭제 하였습니다.", Toast.LENGTH_SHORT)
                            .show()
                        finish()
                    }
                    false -> Toast.makeText(
                        this@SavedDetailActivity,
                        "삭제에 실패하였습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    override fun initBind() {
        savedDetailViewModel.bookInfo(item)
        binding.apply {
            vm = savedDetailViewModel
            ibDelete.apply {
                setOnClickListener {
                    showSimpleDialog(message = getString(R.string.are_you_delete_book_info)) { savedDetailViewModel.deleteBook(item?.isbn13!!) }
                }
            }
            ibBack.setOnClickListener { finish() }
            ibInfo.apply {
                setOnClickListener {
                    dialogBookInfo(item)
                }
            }
            ivLike.apply {
                this.isSelected = item?.isLiked ?: false
                setOnClickListener {
                    this.isSelected = this.isSelected.not()
                    item?.isLiked = this.isSelected
                    savedDetailViewModel.bookUpdate(item!!)
                    savedDetailViewModel.pushLike(this.isSelected, item!!)
                }
            }

            // 피드에 책 등록
            ivShared.apply {
                this.isSelected = item?.isShared ?: false
                setOnClickListener {
                    if (!isSelected) {
                        isTimeAutomatic {
                            showSimpleDialog(message = "감상노트를 공유 하시겠습니까?") {
                                this.isSelected = this.isSelected.not()
                                sharedCheck(isSelected = isSelected)
                                item?.let { savedDetailViewModel.pushShare(it) }
                            }
                        }
//                        if (isTimeAutomatic()) {
//                            dialogSimple("시스템시간을 네트워크시간으로 설정하겠습니까?") {
//                                startActivity(Intent(Settings.ACTION_DATE_SETTINGS))
//                            }
//                            Toast.makeText(context, "시간설정을 네트워크 자동 시간으로 설정해주세요", Toast.LENGTH_SHORT).show()
//
//                        } else {
//                            dialogSimple("감상노트를 공유 하시겠습니까?") {
//                                this.isSelected = this.isSelected.not()
//                                sharedCheck(isSelected = isSelected)
//                                item?.let { savedDetailViewModel.pushShare(it) }
//                            }
//                        }
                    } else {
                        showSimpleDialog(message = "공유한 책정보를 해제하시겠습니까?") {
                            this.isSelected = this.isSelected.not()
                            sharedCheck(isSelected = this.isSelected)
                            item?.let { savedDetailViewModel.pushDelete(it.isbn13) }
                        }
                    }
                }
            }
            ibEdit.apply {
                setOnClickListener {
                    Intent(this@SavedDetailActivity, NoteActivity::class.java).apply {
                        putExtra(getString(R.string.book_info), item)
                        moveToActivityForResult(this)
                    }
//                    Toast.makeText(context, "감상글을 수정합니다.", Toast.LENGTH_SHORT).show()
                }
            }
            tvTitle.text = item?.title!!.split(" - ")[0]
//            includeBookCover.ivBookCover?.loadUrl(item?.cover, ImageTransformType.ROUND,resources.getInteger(R.integer.book_image_radius))

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            BOOK_NOTE_REQUEST_CODE -> {
                item?.bookNote = (data?.getParcelableExtra("data") as? BookItem)?.bookNote
            }
        }
    }

    private fun sharedCheck(isSelected: Boolean) {
        item?.isShared = isSelected
        savedDetailViewModel.bookUpdate(item!!)
    }

}