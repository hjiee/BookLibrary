package com.hyden.booklibrary.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.remote.network.response.BookItem
import com.hyden.booklibrary.data.repository.AladinRepository
import com.hyden.util.LogUtil.LogE
import com.hyden.util.NetworkStatus
import io.reactivex.rxkotlin.addTo

class SearchViewModel(
    private val aladinRepository: AladinRepository
) : BaseViewModel() {

    private val _searchBookInfo = MutableLiveData<List<BookItem>>()
    val searchBookInfo: LiveData<List<BookItem>> get() = _searchBookInfo

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean> get() = _isRefreshing

    private val _searchFinishing = MutableLiveData<Boolean>()
    val searchFinishing: LiveData<Boolean> get() = _searchFinishing

    private val searchTargetVal = "book"
    private val queryTypeVal = "keyword"

    fun searchMore(
        page: Int = 1,
        query: String = "",
        queryType: String = queryTypeVal
    ) {
        _isRefreshing.value = false
        if(loadingStatus.value != NetworkStatus.LOADING) {
            search(page, query, queryType)
        }
    }

    fun searchRefresh(
        page: Int = 1,
        query: String = "",
        queryType: String = queryTypeVal,
        searchTarget: String = searchTargetVal
    ) {
        _isRefreshing.value = true
        search(page, query, queryType, searchTarget)
    }

    fun search(
        page: Int = 1,
        query: String = "",
        queryType: String = queryTypeVal,
        searchTarget: String = searchTargetVal
    ) {
        _searchFinishing.value = false
        compositeDisposable.clear()
            aladinRepository.search(
                page = page,
                query = query,
                querytype = queryType,
                searchtarget = searchTarget
            ).doOnSubscribe { loadingStatus.value = NetworkStatus.LOADING }
                .doOnSuccess { loadingStatus.value = NetworkStatus.SUCCESS }
                .doOnError { loadingStatus.value = NetworkStatus.FAILURE }
                .subscribe(
                    { data ->
                        if (_isRefreshing.value ?: true) {
                            _searchBookInfo.value = data.item ?: emptyList()
                            _isRefreshing.value = false
                        } else {
                            // 더불러오기
                            _searchBookInfo.value = _searchBookInfo.value?.let {
                                it.toMutableList().apply {
                                    addAll(data.item ?: emptyList())
                                }
                            }
                        }
                        _searchFinishing.value = true
                    },
                    {
                        LogE("$it")
                    }
                ).addTo(compositeDisposable)
    }
}