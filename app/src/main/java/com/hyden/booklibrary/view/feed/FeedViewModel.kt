package com.hyden.booklibrary.view.feed

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.remote.network.reponse.BookItems
import com.hyden.booklibrary.data.remote.network.reponse.toBookEntity
import com.hyden.booklibrary.util.ConstUtil.Companion.DATABASENAME

class FeedViewModel : BaseViewModel() {

    private val firestore by lazy { FirebaseFirestore.getInstance() }
    lateinit var documents: List<DocumentSnapshot>
    private val temp by lazy { mutableListOf<BookEntity>() }


    private val _feedItems = MutableLiveData<List<BookEntity>>()
    val feedItems : LiveData<List<BookEntity>> get() = _feedItems

    fun pushLiked(position : Int, isLiked : Boolean) {
        _feedItems.value?.let {
            it[position].isLiked = isLiked
            firestore.collection(DATABASENAME).document(it[position].isbn13).set(it[position])

        }
    }
    fun getFireStore() {
        firestore.collection(DATABASENAME).get().addOnCompleteListener {
            documents = it.result?.documents!!
            if(temp.size > 0) temp.clear()
            for (i in documents.indices) {
                temp.add(book(documents[i].data))
            }
            _feedItems.value = temp
        }
    }

    private fun book(documents: Map<String, Any>?): BookEntity {
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
        }?.toBookEntity()!!
    }

}