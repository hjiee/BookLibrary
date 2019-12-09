package com.hyden.booklibrary.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.remote.network.reponse.BookItems
import com.hyden.booklibrary.data.repository.HomeRepository
import com.hyden.booklibrary.util.ConstUtil.Companion.BOOK_BESTSELLER
import com.hyden.booklibrary.util.ConstUtil.Companion.BOOK_BLOGBEST
import com.hyden.booklibrary.util.ConstUtil.Companion.BOOK_ITEMNEW
import com.hyden.booklibrary.util.ConstUtil.Companion.BOOK_ITEMNEWALL
import com.hyden.util.LogUtil.LogE

class HomeViewModel(
    private val homeRepository: HomeRepository
) : BaseViewModel() {

    private val _bookBlogBest = MutableLiveData<List<BookItems>>()
    val bookBlogBest : LiveData<List<BookItems>> get() = _bookBlogBest

    private val _bookBestSeller = MutableLiveData<List<BookItems>>()
    val bookBestSeller: LiveData<List<BookItems>> get() = _bookBestSeller

    private val _bookNew = MutableLiveData<List<BookItems>>()
    val bookNew: LiveData<List<BookItems>> get() = _bookNew

    private val _bookAll = MutableLiveData<List<BookItems>>()
    val bookAll: LiveData<List<BookItems>> get() = _bookAll


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
            homeRepository.loadBook(
                page = page,
                querytype = queryType,
                searchtarget = searchTarget,
                success = { data, query ->
                    when (query) {
                        BOOK_BLOGBEST -> {
                            bookBlohBest(data.item)
                        }
                        BOOK_BESTSELLER -> {
                            bookBestSeller(data.item)
                        }
                        BOOK_ITEMNEW -> {
                            bookNew(data.item)
                        }
                        BOOK_ITEMNEWALL -> {
                            bookAll(data.item)
                        }
                    }
                },
                failure = {
                    LogE("ERROR : $it")
                }
            )
        )
    }

    private fun bookBlohBest(data : List<BookItems>?) {
        _bookBlogBest.value = data
    }

    private fun bookBestSeller(data: List<BookItems>?) {
        if (_isRefreshingBestSeller.value ?: true) {
            _bookBestSeller.value = data ?: emptyList()
        } else {
            _bookBestSeller.value = _bookBestSeller.value?.let {
                it.toMutableList().apply {
                    addAll(data ?: emptyList())
                }
            }
        }
        _isRefreshingBestSeller.value = false
    }

    private fun bookNew(data: List<BookItems>?) {
        if (_isRefreshingNew.value ?: true) {
            _bookNew.value = data ?: emptyList()
        } else {
            _bookNew.value = _bookNew.value?.let {
                it.toMutableList().apply {
                    addAll(data ?: emptyList())
                }
            }
        }
        _isRefreshingNew.value = false
    }

    private fun bookAll(data: List<BookItems>?) {
        if (_isRefreshingAll.value ?: true) {
            _bookAll.value = data ?: emptyList()
        } else {
            _bookAll.value = _bookAll.value?.let {
                it.toMutableList().apply {
                    addAll(data ?: emptyList())
                }
            }
        }
        _isRefreshingAll.value = false
    }
}