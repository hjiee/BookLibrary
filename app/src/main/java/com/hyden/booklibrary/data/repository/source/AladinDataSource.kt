package com.hyden.booklibrary.data.repository.source

import com.hyden.booklibrary.BuildConfig
import com.hyden.booklibrary.data.remote.network.response.BookDetailResponse
import com.hyden.booklibrary.data.remote.network.response.BookResponse
import com.hyden.booklibrary.data.remote.network.response.SearchResponse
import io.reactivex.Single

interface AladinDataSource {
    fun loadBook(
        ttbkey: String = BuildConfig.TTBKEY,    // key값
        page: Int = 1,                         // 검색결과 시작 페이지
        version: String = "20131101",           // 검색API의 Version(날짜형식)을 설정. (최신버젼: 20131101)
        searchtarget: String,                   // 조회 대상 Mall
        querytype: String,                      // 리스트 종류
        categoryid: Int = 0,                      //
        maxresults: Int = 30,                   // 검색결과 한 페이지당 최대 출력 개수
        output: String = "js",                  // 출력방법패
        cover : String = "big"                 // 표지크기
    ) : Single<BookResponse>

    fun detail(
        ttbkey : String =  BuildConfig.TTBKEY,
        itemIdType: String ="ISBN",
        itemId : String,
        output : String = "js",
        version : String = "20131101"
    ) : Single<BookDetailResponse>

    fun search(
        ttbkey: String = BuildConfig.TTBKEY,    // key값
        page: Int = 1,                         // 검색결과 시작 페이지
        query : String,                         // 검색어
        querytype: String,                      // 검색어 종류
        version: String = "20131101",           // 검색API의 Version(날짜형식)을 설정. (최신버젼: 20131101)
        searchtarget: String = "book",                   // 조회 대상 Mall
        maxresults: Int = 30,                   // 검색결과 한 페이지당 최대 출력 개수
        output: String = "js",                  // 출력방법
        cover : String = "big"                  // 표지크기
    ): Single<SearchResponse>
}