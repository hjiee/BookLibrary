package com.hyden.booklibrary.data.remote

import com.hyden.booklibrary.data.remote.network.reponse.BookResponse
import com.hyden.booklibrary.data.remote.network.reponse.SearchResponse
import io.reactivex.Single
import retrofit2.http.*
import java.util.*
import kotlin.collections.HashMap

interface BookApi {

    /**
     * 상품 리스트
     * Default Parameter
     * @ttbkey
     * @start
     * @Version
     * @SearchTarget
     * @QueryType
     * @MaxResults
     * @output
     */
    @GET("ItemList.aspx")
    fun bookInfo(@QueryMap map : HashMap<String,Any>) : Single<BookResponse>

    /**
     * 상품 검색
     */
    @GET("ItemSearch.aspx")
    fun bookSearch(@QueryMap map : HashMap<String,Any>) : Single<SearchResponse>
}







