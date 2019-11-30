package com.hyden.booklibrary.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.remote.network.reponse.BookItems
import com.hyden.booklibrary.data.repository.HomeRepository
import com.hyden.util.LogUtil.LogW

class HomeViewModel(
    private val homeRepository: HomeRepository
) : BaseViewModel() {


    private val _bookInfo = MutableLiveData<List<BookItems>>()
    val bookInfo: LiveData<List<BookItems>> get() = _bookInfo

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean> get() = _isRefreshing

    fun loadMore(page : Int) {
        _isRefreshing.value = false
        loadBook(page = page)
    }
    fun loadRefresh() {
        _isRefreshing.value = true
        loadBook()
    }

    fun loadBook(
        page: Int = 1,
        searchTarget: String = "book",
        queryType: String = "bestseller"
    ) {
        compositeDisposable.add(
            homeRepository.loadBook(
                page = page,
                searchtarget = searchTarget,
                querytype = queryType,
                success = { data ->
                    if (_isRefreshing.value ?: true) {
                        _bookInfo.value = data.item
                        _isRefreshing.value = false
                    } else {
                        // 더불러오기
                        _bookInfo.value = _bookInfo.value?.let {
                            it.toMutableList().apply {
                                addAll(data.item)
                            }
                        }
                    }
                    LogW("SUCCESS : ")
                },
                failure = {
                    LogW("ERROR : $it")
                }
            )
        )

    }
}