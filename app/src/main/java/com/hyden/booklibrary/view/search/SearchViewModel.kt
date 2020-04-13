package com.hyden.booklibrary.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.remote.network.reponse.BookItems
import com.hyden.booklibrary.data.repository.SearchRepository
import com.hyden.booklibrary.data.repository.source.SearchDataSource
import com.hyden.util.LogUtil

class SearchViewModel(
    private val searchDataSource: SearchDataSource
) : BaseViewModel() {

    private val _searchBookInfo = MutableLiveData<List<BookItems>>()
    val searchBookInfo: LiveData<List<BookItems>> get() = _searchBookInfo

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean> get() = _isRefreshing

    private val _searchFinishing = MutableLiveData<Boolean>()
    val searchFinishing: LiveData<Boolean> get() = _searchFinishing

    private val _isResultEmpty = MutableLiveData<Boolean>()
    val isResultEmpty : LiveData<Boolean> get() = _isResultEmpty

    private val searchTargetVal = "book"
    private val queryTypeVal = "keyword"

    fun searchMore(
        page: Int = 1,
        query: String = "",
        queryType: String = queryTypeVal
    ) {
        _isRefreshing.value = false
        search(page, query, queryType)
    }

    fun searchRefresh(
        page: Int = 1,
        query: String = "",
        queryType: String = queryTypeVal,
        searchTarget: String = searchTargetVal
    ) {
        _isRefreshing.value = true
        search(
            page,
            query,
            queryType,
            searchTarget
        )
    }

    fun search(
        page: Int = 1,
        query: String = "",
        queryType: String = queryTypeVal,
        searchTarget: String = searchTargetVal
    ) {
        _searchFinishing.value = false
        compositeDisposable.add(
            searchDataSource.search(
                page = page,
                query = query,
                querytype = queryType,
                searchtarget = searchTarget,
                success = { data ->
                    when(data.item ?: false) {
                        false -> _isResultEmpty.value = false
                        else -> _isResultEmpty.value = true
                    }

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
//                    LogUtil.LogW("SUCCESS : $data")
                },
                failure = {
                    _isResultEmpty.value = false
//                    LogUtil.LogW("ERROR : $it")
                }
            )
        )
    }
}