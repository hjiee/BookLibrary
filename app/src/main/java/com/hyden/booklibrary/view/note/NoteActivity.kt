package com.hyden.booklibrary.view.note

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.hyden.base.BaseActivity
import com.hyden.booklibrary.R
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.databinding.ActivityBookNoteBinding
import com.hyden.booklibrary.util.ConstUtil.Companion.BOOK_NOTE_REQUEST_CODE
import com.hyden.booklibrary.view.detail.SavedDetailActivity
import com.hyden.ext.showKeyboard
import org.koin.android.ext.android.inject

class NoteActivity : BaseActivity<ActivityBookNoteBinding>(R.layout.activity_book_note) {

    private val noteViewModel by inject<NoteViewModel>()
    private val item by lazy { intent?.getParcelableExtra<BookEntity>(getString(R.string.book_info)) }
//    private val firestore by lazy {
//        FirebaseFirestore.getInstance()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun finish() {
        saveNote()
        super.finish()
    }

    override fun initBind() {
        binding.apply {
            ibBack.setOnClickListener { finish() }
            ibDone.setOnClickListener {
                saveNote()
                Toast.makeText(this@NoteActivity, "저장하였습니다", Toast.LENGTH_SHORT).show()
                finish()
            }
            tvTitle.text = item?.title!!.split(" - ")[0]
            edtNoteContent.apply {
                setText(item?.bookNote)
                showKeyboard(this)
            }
        }
    }

    private fun saveNote() {
//        item?.bookNote = binding.edtNoteContent.text.toString()
//        bookRecordViewModel.save(item)
//        item?.let { firestore.collection("book").document(it.isbn13).set(it) }
        item?.run {
            bookNote = binding.edtNoteContent.text.toString()
            noteViewModel.updateBook(this)
//            noteViewModel.remoteUpdateBook(this)
//            firestore.collection(DATABASENAME).document(this.isbn13).set(this)
        }
        setResult(BOOK_NOTE_REQUEST_CODE, Intent(this@NoteActivity,SavedDetailActivity::class.java).putExtra("data",item))
    }
}