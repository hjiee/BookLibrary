package com.hyden.booklibrary.view.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.model.*
import com.hyden.booklibrary.data.remote.network.reponse.BookItems
import com.hyden.booklibrary.data.remote.network.reponse.toBookEntity
import com.hyden.booklibrary.data.repository.source.FirebaseDataSource
import com.hyden.booklibrary.util.ConstUtil.Companion.DATABASENAME
import com.hyden.util.LogUtil.LogE
import java.util.*

class FeedViewModel(
    private val firebaseDataSource: FirebaseDataSource
) : BaseViewModel() {

    private val firestore by lazy { FirebaseFirestore.getInstance() }
    lateinit var documents: List<DocumentSnapshot>

    private val _feedItems = MutableLiveData<List<Feed>>()
    val feedItems: LiveData<List<Feed>> get() = _feedItems

    private val _isSharedUser = MutableLiveData<Boolean>()
    val isSharedUser: LiveData<Boolean> get() = _isSharedUser

    // 좋아요 클릭 이벤트 처리
    fun pushLiked(position: Int, isLiked: Boolean) {
        _feedItems.value?.let {
            val documentId =
                it[position].sharedInfo.users.email + "-" + it[position].bookEntity.isbn13
            firebaseDataSource.pushLike(isLiked, documentId)
            if (it[position].sharedInfo.users.email == firebaseDataSource.getLoginEmail()) {
                it[position].bookEntity.isLiked = isLiked
                _isSharedUser.value = true
            } else {
                _isSharedUser.value = false
            }
        }
    }

    fun isContainsUser(users: List<User>): Boolean {
        return firebaseDataSource.isExsitUser(users)
    }

    fun getFireStore() {
        firestore.collection(DATABASENAME).get().addOnCompleteListener {
            documents = it.result?.documents!!
            val temp = mutableListOf<Feed>()
            for (i in documents.indices) {
                temp.add(feed(documents[i].data))
                LogE(temp.toString())
            }
            _feedItems.value = temp
        }
    }

    private fun feed(documents: Map<*, *>?): Feed {
        return documents?.run {
            Feed(
                get("sharedInfo").toSharedInfo(),
                book(get("bookEntity") as HashMap<*, *>),
                get("commentsCount").toString().toLong(),
                get("commentsInfo").toComment(),
                get("likesCount").toString().toLong(),
                get("likesInfo").toLike(),
                get("usersInfo").toUser()
            )
        }?.toFeed()!!
    }

    private fun book(documents: HashMap<*, *>): BookEntity {
        return documents?.run {
            BookItems(
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
        }.toBookEntity()
    }

}