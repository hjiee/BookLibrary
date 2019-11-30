package com.hyden.booklibrary.data.repository

import com.hyden.booklibrary.data.remote.BookApi
import com.hyden.booklibrary.data.remote.network.reponse.BookResponse
import com.hyden.booklibrary.data.repository.source.HomeDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomeRepository(
    private val bookApi: BookApi
) : HomeDataSource {

    override fun loadBook(
        ttbkey: String,
        page: Int,
        version: String,
        searchtarget: String,
        querytype: String,
        maxresults: Int,
        output: String,
        cover : String,
        success: (BookResponse) -> Unit,
        failure: (String) -> Unit
    ): Disposable {
        return bookApi.bookInfo(HashMap<String, Any>().apply {
            put("ttbkey", ttbkey)
            put("start", page)
            put("Version", version)
            put("SearchTarget", searchtarget)
            put("QueryType", querytype)
            put("MaxResults", maxresults)
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