package com.hyden.booklibrary.view.myshared

import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hyden.base.BaseItemsApdater
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.R
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.repository.source.BookDataSource
import com.hyden.booklibrary.util.SingleLiveEvent
import com.hyden.util.ItemClickListener
import com.hyden.util.LogUtil.LogE
import com.hyden.util.LogUtil.LogW
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MySharedBookViewMiodel(
    private val bookDataSource: BookDataSource
): BaseViewModel() {

    private val _sharedItems = MutableLiveData<List<BookEntity>>()
    val sharedItems : LiveData<List<BookEntity>> get() = _sharedItems
    val singleLiveEvent =  SingleLiveEvent<Any>()


    val clickListener = object : ItemClickListener {
        override fun <T> onItemClick(item: T) {
            singleLiveEvent.call(item)
        }
    }

    fun loadBook() {
        compositeDisposable.add(
            bookDataSource.getSharedBook()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _sharedItems.value = it
                        LogW(it.toString())
                    },
                    {
                        LogE(it.toString())
                    }
                )
        )
    }
    override fun onCleared() {
        super.onCleared()
    }
}