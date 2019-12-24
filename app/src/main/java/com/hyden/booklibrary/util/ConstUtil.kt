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
        const val DATABASENAME = "book"
        const val DATABASELIMIT = 30
        const val BOOK_BLOGBEST = "BLOGBEST"
        const val BOOK_BESTSELLER = "BESTSELLER"
        const val BOOK_ITEMNEW = "ITEMNEWSPECIAL"
        const val BOOK_ITEMNEWALL = "ITEMNEWALL"
        const val BOOK_NOTE_REQUEST_CODE = 1224
    }
}

fun Context.deleteBook(result: () -> Unit) {
    AlertDialog.Builder(this, R.style.DeleteDialog).apply {
        setTitle(getString(R.string.app_name))
        setMessage("책에 저장된 정보가 삭제됩니다.\n정말 삭제하시겠습니까?")
        setPositiveButton("삭제") { _, _ ->
            result.invoke()
        }
        setNegativeButton("취소") { _, _ ->
        }
    }.show()
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

fun Context.dialogSimple(
    message: String,
    result: () -> Unit
) {
    AlertDialog.Builder(this, R.style.DeleteDialog).apply {
        setTitle(getString(R.string.app_name))
        setMessage("$message")
        setPositiveButton("확인") { _, _ ->
            result.invoke()
        }
        setNegativeButton("취소") { _, _ ->
        }
    }.show()
}