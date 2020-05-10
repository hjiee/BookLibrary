package com.hyden.booklibrary.data.repository

import com.hyden.booklibrary.data.remote.BookApi
import com.hyden.booklibrary.data.remote.network.response.SearchResponse
import com.hyden.booklibrary.data.repository.source.SearchDataSource
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchRepository(
    private val bookApi: BookApi
) : SearchDataSource {


    override fun search(
        ttbkey: String,
        page: Int,
        query : String,
        querytype: String,
        version: String,
        searchtarget: String,
        maxresults: Int,
        output: String,
        cover : String
    ): Single<SearchResponse> {
        return bookApi.bookSearch(HashMap<String, Any>().apply {
            put("ttbkey", ttbkey)
            put("start", page)
            put("version", version)
            put("searchtarget", searchtarget)
            put("querytype", querytype)
            put("query", query)
            put("maxresults", maxresults)
            put("output", output)
            put("cover", cover)
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}