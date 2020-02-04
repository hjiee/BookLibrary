package com.hyden.booklibrary.data.repository

import com.hyden.booklibrary.data.remote.BookApi
import com.hyden.booklibrary.data.remote.network.reponse.BookItems
import com.hyden.booklibrary.data.remote.network.reponse.BookResponse
import com.hyden.booklibrary.data.repository.source.HomeDataSource
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomeRepository(
    private val bookApi: BookApi
) : HomeDataSource {

    private var queryTypeName = ""

    override fun loadBook(
        ttbkey: String,
        page: Int,
        version: String,
        searchtarget: String,
        querytype: String,
        categoryid : Int,
        maxresults: Int,
        output: String,
        cover: String
    ): Single<BookResponse> {
        queryTypeName = querytype
        return bookApi.bookInfo(HashMap<String, Any>().apply {
            put("ttbkey", ttbkey)
            put("start", page)
            put("version", version)
            put("searchtarget", searchtarget)
            put("querytype", queryTypeName)
            put("categoryid", 0)
            put("maxresults", maxresults)
            put("output", output)
            put("cover", cover)
        })
    }
}

