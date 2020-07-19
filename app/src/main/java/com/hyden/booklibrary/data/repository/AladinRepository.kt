package com.hyden.booklibrary.data.repository

import com.hyden.booklibrary.data.remote.BookApi
import com.hyden.booklibrary.data.remote.network.response.detail.BookDetailResponse
import com.hyden.booklibrary.data.remote.network.response.BookResponse
import com.hyden.booklibrary.data.remote.network.response.search.SearchResponse
import com.hyden.booklibrary.data.repository.source.AladinDataSource
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AladinRepository(private val bookApi : BookApi) : AladinDataSource {

    /**
     * 책 정보 불러오기
     */
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
        return bookApi.bookInfo(HashMap<String, Any>().apply {
            put("ttbkey", ttbkey)
            put("start", page)
            put("version", version)
            put("searchtarget", searchtarget)
            put("querytype", querytype)
            put("categoryid", 0)
            put("maxresults", maxresults)
            put("output", output)
            put("cover", cover)
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * 책 상세 정보
     */
    override fun detail(
        ttbkey: String,
        itemIdType: String,
        itemId: String,
        output: String,
        version: String
    ): Single<BookDetailResponse> {
        return bookApi.bookDetail(HashMap<String,Any>().apply {
            put("ttbkey", ttbkey)
            put("itemIdType", itemIdType)
            put("itemId", itemId)
            put("output", output)
            put("version", version)
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * 책 정보 검색
     */
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