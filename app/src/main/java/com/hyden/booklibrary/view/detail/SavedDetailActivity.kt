package com.hyden.booklibrary.view.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.firebase.firestore.FirebaseFirestore
import com.hyden.base.BaseActivity
import com.hyden.booklibrary.R
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.databinding.ActivityDetailSavedBinding
import com.hyden.booklibrary.util.ConstUtil.Companion.BOOK_NOTE_REQUEST_CODE
import com.hyden.booklibrary.util.ConstUtil.Companion.DATABASENAME
import com.hyden.booklibrary.util.deleteBook
import com.hyden.booklibrary.util.dialogBookInfo
import com.hyden.booklibrary.util.dialogSimple
import com.hyden.booklibrary.view.feed.FeedViewModel
import com.hyden.booklibrary.view.note.NoteActivity
import com.hyden.ext.loadUrl
import com.hyden.ext.moveToActivityForResult
import com.hyden.util.ImageTransformType
import org.koin.android.ext.android.inject

class SavedDetailActivity :
    BaseActivity<ActivityDetailSavedBinding>(R.layout.activity_detail_saved) {

    private val savedDetailViewModel by inject<SavedDetailViewModel>()
    private val feedViewModel by inject<FeedViewModel>()

    //    private val item by lazy { intent?.getParcelableExtra<BookEntity>(getString(R.string.book_info)) }
    private val firestore by lazy {
        FirebaseFirestore.getInstance()
    }
    var item: BookEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedDetailViewModel.bookInfo(item)
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
        item = intent?.getParcelableExtra(getString(R.string.book_info))
        binding.apply {
            vm = savedDetailViewModel
            ibDelete.apply {
                setOnClickListener {
                    deleteBook { savedDetailViewModel.deleteBook(item?.isbn13!!) }
                }
            }
            ibBack.setOnClickListener { finish() }
            ibInfo.apply {
                setOnClickListener {
                    dialogBookInfo(item)
                }
            }
            ivLike.apply {
                isSelected = item?.isLiked ?: false
                setOnClickListener {
                    isSelected = isSelected.not()
                    item?.isLiked = isSelected
                    savedDetailViewModel.bookUpdate(item!!)
                    if(ivShared.isSelected) {
                        firestore.collection(DATABASENAME).document(item?.isbn13!!).set(item!!)
                    }
                }
            }
            ivChat.apply {
                isSelected = item?.isReviews ?: false
                setOnClickListener {
                    isSelected = isSelected.not()
                    item?.isReviews = isSelected
                    savedDetailViewModel.bookUpdate(item!!)
                }
            }
            ivShared.apply {
                isSelected = item?.isShared ?: false
                setOnClickListener {
                    if (!isSelected) {
                        dialogSimple("감상노트를 공유 하시겠습니까?") {
                            isSelected = isSelected.not()
                            sharedCheck(isSelected = isSelected)
                            item?.let {
                                firestore.collection(DATABASENAME).document(it.isbn13).set(it)
                            }
                        }
                    } else {
                        dialogSimple("공유한 책정보를 해제하시겠습니까?") {
                            isSelected = isSelected.not()
                            sharedCheck(isSelected = isSelected)
                            item?.let {
                                firestore.collection(DATABASENAME).document(it.isbn13).delete()
                            }
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
            ivBookCover.loadUrl(item?.cover, ImageTransformType.FIT)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            BOOK_NOTE_REQUEST_CODE -> {
                item = data?.getParcelableExtra("data") as? BookEntity
                binding.tvNoteContent.text = item?.bookNote
            }
        }
    }

    private fun writeReviews() {

    }

    private fun sharedCheck(isSelected: Boolean) {
        item?.isShared = isSelected
        savedDetailViewModel.bookUpdate(item!!)
    }

}