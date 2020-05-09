package com.hyden.booklibrary.view.detail.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.hyden.base.BaseItemsApdater
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.R
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.model.*
import com.hyden.booklibrary.data.remote.network.response.BookItem
import com.hyden.booklibrary.data.remote.network.response.convertToBookEntity
import com.hyden.booklibrary.util.ConstUtil
import com.hyden.booklibrary.view.feed.model.FeedData

class FeedDetailViewModel() : BaseViewModel() {

    private val firestore by lazy { FirebaseFirestore.getInstance() }
    lateinit var lastVisible: DocumentSnapshot
    var adapter = BaseItemsApdater(R.layout.recycler_item_comment)

    private val _feedItem = MutableLiveData<List<FeedData>>()
    val feedItem : LiveData<List<FeedData>> get() = _feedItem

    fun initData(feedData: BookEntity) {
//        adapter.addItems(feedData.)
        firestore.collection(ConstUtil.DATABASENAME_BOOK)
//            .orderBy("commentInfo", Query.Direction.DESCENDING)
            .whereEqualTo("bookEntity.isbn13",feedData.isbn13)
            .limit(ConstUtil.FEED_LIMIT)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                // 파이어베이스 페이징
                if(documentSnapshot.size() >= 1) {
                    lastVisible = documentSnapshot.documents[documentSnapshot.size() - 1]
                }
                val temp = mutableListOf<FeedData>()
                for (i in documentSnapshot.documents.indices) {
//                    ((documentSnapshot.documents[i].data?.get("sharedInfo") as HashMap<*,*>).get("users") as HashMap<*,*>)["email"]
//                    ((documentSnapshot.documents[i].data?.get("sharedInfo") as HashMap<*,*>).get("users") as HashMap<*,*>)["name"]
//                    ((documentSnapshot.documents[i].data?.get("sharedInfo") as HashMap<*,*>).get("users") as HashMap<*,*>)["nickName"]
//                    ((documentSnapshot.documents[i].data?.get("sharedInfo") as HashMap<*,*>).get("users") as HashMap<*,*>)["profile"]
                    temp.add(FeedData(feed(documentSnapshot.documents[i].data), false))
                }
                _feedItem.value = temp
            }
    }

    fun loadMore() {
        firestore.collection(ConstUtil.DATABASENAME_BOOK)
            .orderBy("commentInfo", Query.Direction.DESCENDING)
            .limit(ConstUtil.FEED_LIMIT)
            .startAfter(lastVisible)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.size() > 0) {
                    lastVisible = documentSnapshot.documents[documentSnapshot.size() - 1]

                    val temp = mutableListOf<FeedData>()
                    for (i in documentSnapshot.documents.indices) {
                        temp.add(FeedData(feed(documentSnapshot.documents[i].data),false))
                    }

                }
            }
    }

    // 데이터 파싱
    private fun feed(documents: Map<*, *>?): Feed {
        return documents?.run {
            Feed(
                get("sharedInfo").toSharedInfo(),
                book(get("bookEntity") as HashMap<*, *>),
                get("likesCount").toString().toLong(),
                get("likesInfo").toLike()
            )
        }?.toFeed()!!
    }

    private fun book(documents: HashMap<*, *>): BookEntity {
        return documents?.run {
            BookItem(
                get("savaed") as Boolean? ?: false,
                get("liked") as Boolean? ?: false,
                get("shared") as Boolean? ?: false,
                get("chated") as Boolean? ?: false,
                get("bookNote").toString(),
                get("bookReviews").toString(),
                get("title").toString(),
                get("link").toString(),
                get("author").toString(),
                get("pubDate").toString(),
                get("description").toString(),
                get("isbn").toString(),
                get("isbn13").toString(),
                get("itemId").toString(),
                get("priceSales").toString(),
                get("priceStandard").toString(),
                get("mallType").toString(),
                get("stockStatus").toString(),
                get("mileage").toString(),
                get("cover").toString(),
                get("categoryId").toString(),
                get("categoryName").toString(),
                get("publisher").toString(),
                get("salesPoint").toString(),
                get("adult").toString(),
                get("fixedPrice").toString(),
                get("customerReviewRank").toString(),
                get("bestRank").toString()
            )
        }.convertToBookEntity()
    }


    override fun onCleared() {
        super.onCleared()
    }
}