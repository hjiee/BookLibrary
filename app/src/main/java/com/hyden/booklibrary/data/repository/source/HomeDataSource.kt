package com.hyden.booklibrary.data.repository.source

import com.hyden.booklibrary.BuildConfig
import com.hyden.booklibrary.data.remote.network.response.BookResponse
import io.reactivex.Single

interface HomeDataSource {
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
}