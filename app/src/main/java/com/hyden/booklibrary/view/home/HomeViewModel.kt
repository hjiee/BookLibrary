package com.hyden.booklibrary.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.remote.network.response.BookItem
import com.hyden.booklibrary.data.remote.network.response.BookResponse
import com.hyden.booklibrary.data.repository.AladinRepository
import com.hyden.booklibrary.util.ConstUtil.Companion.BOOK_BESTSELLER
import com.hyden.booklibrary.util.ConstUtil.Companion.BOOK_BLOGBEST
import com.hyden.booklibrary.util.ConstUtil.Companion.BOOK_ITEMNEW_SPECIAL
import com.hyden.booklibrary.util.ConstUtil.Companion.BOOK_ITEMNEW_ALL
import com.hyden.util.LogUtil.LogE
import io.reactivex.Single
import kotlin.random.Random

class HomeViewModel(
    private val aladinRepository: AladinRepository
) : BaseViewModel() {

    private val _bookBlogBest = MutableLiveData<List<BookItem>>()
    val bookBlogBest: LiveData<List<BookItem>> get() = _bookBlogBest

    private val _bookBestSeller = MutableLiveData<List<BookItem>>()
    val bookBestSeller: LiveData<List<BookItem>> get() = _bookBestSeller

    private val _bookNewSpecial = MutableLiveData<List<BookItem>>()
    val bookNewSpecial: LiveData<List<BookItem>> get() = _bookNewSpecial

    private val _bookNewAll = MutableLiveData<List<BookItem>>()
    val bookNewAll: LiveData<List<BookItem>> get() = _bookNewAll


    private val _isRefreshingBestSeller = MutableLiveData<Boolean>()
    val isRefreshingBestSeller: LiveData<Boolean> get() = _isRefreshingBestSeller

    private val _isRefreshingNew = MutableLiveData<Boolean>()
    val isRefreshingNew: LiveData<Boolean> get() = _isRefreshingNew

    private val _isRefreshingAll = MutableLiveData<Boolean>()
    val isRefreshingAll: LiveData<Boolean> get() = _isRefreshingAll

    private val searchTarget : String = "book"

    fun loadMore(
        page: Int,
        queryType: String
    ) {
        loadBook(page = page, queryType = queryType)
    }

    fun loadBook(
        page: Int = 1,
        queryType: String
    ) {
        compositeDisposable.add(
            aladinRepository.loadBook(
                page = page,
                querytype = queryType,
                searchtarget = searchTarget
            ).subscribe({
                val queryTypeName = it.query.split("=", ";")[1]
                when (queryTypeName) {
                    BOOK_BLOGBEST -> bookBlogBest(it.item)
                    BOOK_BESTSELLER -> bookBestSeller(it.item)
                    BOOK_ITEMNEW_SPECIAL -> bookNewSpecial(it.item)
                    BOOK_ITEMNEW_ALL -> bookNewAll(it.item)
                }
            }, { LogE("$it") })
        )
    }

    fun load(page: Int = 1) {
//        compositeDisposable.add(
//            fetchBlogBest().zipWith(fetchBestSeller(page),fetchItemNew(page),fetchItemNewAll(page))
//        )
    }

    private fun fetchBlogBest(): Single<BookResponse> {
        return aladinRepository.loadBook(
                page = Random.nextInt(1, 4),
                querytype = BOOK_BLOGBEST,
                searchtarget = searchTarget
            )
    }

    private fun fetchBestSeller(page: Int = 1): Single<BookResponse> {
        return aladinRepository.loadBook(
                page = page,
                querytype = BOOK_BESTSELLER,
                searchtarget = searchTarget
            )
    }

    private fun fetchItemNew(page: Int = 1): Single<BookResponse> {
        return aladinRepository.loadBook(
                page = page,
                querytype = BOOK_ITEMNEW_SPECIAL,
                searchtarget = searchTarget
            )
    }

    private fun fetchItemNewAll(page: Int = 1): Single<BookResponse> {
        return aladinRepository.loadBook(
            page = page,
            querytype = BOOK_ITEMNEW_ALL,
            searchtarget = searchTarget
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

    private fun bookNewSpecial(data: List<BookItem>) {
        if (_isRefreshingNew.value ?: true) {
            _bookNewSpecial.value = data ?: emptyList()
        } else {
            _bookNewSpecial.value = _bookNewSpecial.value?.let {
                it.toMutableList().apply {
                    addAll(data)
                }
            }
        }
        _isRefreshingNew.value = false
    }

    private fun bookNewAll(data: List<BookItem>) {
        if (_isRefreshingAll.value ?: true) {
            _bookNewAll.value = data
        } else {
            _bookNewAll.value = _bookNewAll.value?.let {
                it.toMutableList().apply {
                    addAll(data)
                }
            }
        }
        _isRefreshingAll.value = false
    }
}