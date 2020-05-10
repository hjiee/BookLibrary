package com.hyden.booklibrary.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.remote.network.response.BookItem
import com.hyden.booklibrary.data.repository.source.BookDataSource
import com.hyden.booklibrary.data.repository.source.HomeDataSource
import com.hyden.booklibrary.util.ConstUtil.Companion.BOOK_BESTSELLER
import com.hyden.booklibrary.util.ConstUtil.Companion.BOOK_BLOGBEST
import com.hyden.booklibrary.util.ConstUtil.Companion.BOOK_ITEMNEW
import com.hyden.booklibrary.util.ConstUtil.Companion.BOOK_ITEMNEWALL
import com.hyden.util.LogUtil.LogE

class HomeViewModel(
    private val homeDataSource: HomeDataSource,
    private val bookDataSource: BookDataSource
) : BaseViewModel() {

    private val _bookBlogBest = MutableLiveData<List<BookItem>>()
    val bookBlogBest: LiveData<List<BookItem>> get() = _bookBlogBest

    private val _bookBestSeller = MutableLiveData<List<BookItem>>()
    val bookBestSeller: LiveData<List<BookItem>> get() = _bookBestSeller

    private val _bookNew = MutableLiveData<List<BookItem>>()
    val bookNew: LiveData<List<BookItem>> get() = _bookNew

    private val _bookAll = MutableLiveData<List<BookItem>>()
    val bookAll: LiveData<List<BookItem>> get() = _bookAll


    private val _isRefreshingBestSeller = MutableLiveData<Boolean>()
    val isRefreshingBestSeller: LiveData<Boolean> get() = _isRefreshingBestSeller

    private val _isRefreshingNew = MutableLiveData<Boolean>()
    val isRefreshingNew: LiveData<Boolean> get() = _isRefreshingNew

    private val _isRefreshingAll = MutableLiveData<Boolean>()
    val isRefreshingAll: LiveData<Boolean> get() = _isRefreshingAll

    fun loadMore(
        page: Int,
        queryType: String
    ) {
        loadBook(page = page, queryType = queryType)
    }

    fun loadBook(
        page: Int = 1,
        queryType: String,
        searchTarget: String = "book"
    ) {
        compositeDisposable.add(
            homeDataSource.loadBook(
                page = page,
                querytype = queryType,
                searchtarget = searchTarget
            ).subscribe({
                val queryTypeName = it.query.split("=", ";")[1]
                when (queryTypeName) {
                    BOOK_BLOGBEST -> bookBlogBest(it.item)
                    BOOK_BESTSELLER -> bookBestSeller(it.item)
                    BOOK_ITEMNEW -> bookNew(it.item)
                    BOOK_ITEMNEWALL -> bookAll(it.item)
                }
            }, { LogE("$it") })
        )
    }

    private fun bookBlogBest(data: List<BookItem>) {
        _bookBlogBest.value = data
    }

    private fun bookBestSeller(data: List<BookItem>) {
        if (_isRefreshingBestSeller.value ?: true) {
            _bookBestSeller.value = data
        } else {
            _bookBestSeller.value = _bookBestSeller.value?.let {
                it.toMutableList().apply {
                    addAll(data)
                }
            }
        }
        _isRefreshingBestSeller.value = false
    }

    private fun bookNew(data: List<BookItem>) {
        if (_isRefreshingNew.value ?: true) {
            _bookNew.value = data ?: emptyList()
        } else {
            _bookNew.value = _bookNew.value?.let {
                it.toMutableList().apply {
                    addAll(data)
                }
            }
        }
        _isRefreshingNew.value = false
    }

    private fun bookAll(data: List<BookItem>) {
        if (_isRefreshingAll.value ?: true) {
            _bookAll.value = data
        } else {
            _bookAll.value = _bookAll.value?.let {
                it.toMutableList().apply {
                    addAll(data)
                }
            }
        }
        _isRefreshingAll.value = false
    }
}