package com.hyden.booklibrary.data.repository

import com.hyden.booklibrary.data.remote.BookApi
import com.hyden.booklibrary.data.remote.network.reponse.SearchResponse
import com.hyden.booklibrary.data.repository.source.SearchDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
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
        cover : String,
        success: (SearchResponse) -> Unit,
        failure: (String) -> Unit
    ): Disposable {
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
            .subscribe(
                {
                    success.invoke(it)
                },
                {
                    failure.invoke(it.toString())
                }
            )
    }
}