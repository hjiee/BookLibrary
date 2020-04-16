package com.hyden.booklibrary.util

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.hyden.booklibrary.R
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.ext.numberFormatter
import kotlinx.android.synthetic.main.dialog_book_info.view.*

class ConstUtil {
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASENAME_BOOK = "book"
        const val DATABASENAME_SHARED = "sharedbook"
        const val FIRESTORE_USERS = "users"
        const val FIRESTORE_PERSONAL = "personal"
        const val DATABASELIMIT = 30
        const val FEED_LIMIT = 6L
        const val BOOK_BLOGBEST = "BLOGBEST"
        const val BOOK_BESTSELLER = "BESTSELLER"
        const val BOOK_ITEMNEW = "ITEMNEWSPECIAL"
        const val BOOK_ITEMNEWALL = "ITEMNEWALL"
        const val BOOK_NOTE_REQUEST_CODE = 1224
        const val DEFAULT_COLLAPSEDLINES = 3
//        const val LOGIN_ID = "hjiee"
//        const val LOGIN_NAME = "hyojin"
    }
}

fun Context.dialogBookInfo(info: BookEntity?) {
    AlertDialog.Builder(this, R.style.DeleteDialog).apply {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_book_info, null, false)

        view.tv_author.text = info?.author
        view.tv_publisher.text = info?.publisher
        view.tv_price.text = info?.priceSales?.numberFormatter()
        view.tv_description.text = info?.description

        setTitle(info?.title)
        setIcon(R.drawable.round_menu_book_24)
        setView(view)
        setPositiveButton("확인") { _, _ -> }
    }.show()
}

