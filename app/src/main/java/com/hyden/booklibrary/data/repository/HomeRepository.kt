package com.hyden.booklibrary.data.repository

import com.hyden.booklibrary.data.remote.BookApi
import com.hyden.booklibrary.data.remote.network.reponse.BookItems
import com.hyden.booklibrary.data.remote.network.reponse.BookResponse
import com.hyden.booklibrary.data.repository.source.HomeDataSource
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
        cover: String,
        success: (List<BookItems>, String) -> Unit,
        failure: (String) -> Unit
    ): Disposable {
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
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val queryTypeName = it.query.split("=",";")[1]
                    if(it == null) success.invoke(emptyList(),queryTypeName)
                    else if(it.item !=null) success.invoke(it.item,queryTypeName)
                    else failure.invoke("Item is Null")
                },
                {
                    failure.invoke(it.toString())
                }
            )
    }
}

