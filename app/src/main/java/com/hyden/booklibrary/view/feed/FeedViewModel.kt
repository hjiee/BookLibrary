package com.hyden.booklibrary.view.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.model.*
import com.hyden.booklibrary.data.remote.network.reponse.BookItems
import com.hyden.booklibrary.data.remote.network.reponse.toBookEntity
import com.hyden.booklibrary.util.ConstUtil.Companion.DATABASENAME
import com.hyden.booklibrary.util.ConstUtil.Companion.LOGIN_ID
import com.hyden.booklibrary.util.ConstUtil.Companion.LOGIN_NAME
import com.hyden.util.LogUtil.LogE
import java.util.*

class FeedViewModel : BaseViewModel() {

    private val firestore by lazy { FirebaseFirestore.getInstance() }
    lateinit var documents: List<DocumentSnapshot>

    private val _feedItems = MutableLiveData<List<Feed>>()
    val feedItems: LiveData<List<Feed>> get() = _feedItems

    fun pushLiked(position: Int, isLiked: Boolean) {
        _feedItems.value?.let {
            it[position].bookEntity.isLiked = isLiked
//            firestore.collection(DATABASENAME).document(it[position].isbn13).set(it[position])
            if (isLiked) {
                firestore.collection(DATABASENAME).document(it[position].bookEntity.isbn13)
                    .update("likesCount", FieldValue.increment(1))
                firestore.collection(DATABASENAME).document(it[position].bookEntity.isbn13).update("likesInfo.users", FieldValue.arrayUnion(((User(
                    LOGIN_ID, LOGIN_NAME)))))


            } else {
                firestore.collection(DATABASENAME).document(it[position].bookEntity.isbn13)
                    .update("likesCount", FieldValue.increment(-1))
                firestore.collection(DATABASENAME).document(it[position].bookEntity.isbn13).update("likesInfo.users", FieldValue.arrayRemove(((User(
                    LOGIN_ID, LOGIN_NAME)))))

            }


        }
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

    private fun feed(documents: Map<*, *>?) : Feed {
        return documents?.run {
            Feed(get("sharedDate").toDate(),
                    book(get("bookEntity") as HashMap<*, *>),
                    get("commentsCount").toString().toLong(),
                    get("commentsInfo").toComment(),
                    get("likesCount").toString().toLong(),
                    get("likesInfo").toLike(),
                    get("usersInfo").toUser())
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