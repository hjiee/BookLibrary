package com.hyden.booklibrary.view.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.hyden.base.BaseViewModel
import com.hyden.booklibrary.data.local.db.BookEntity
import com.hyden.booklibrary.data.model.*
import com.hyden.booklibrary.data.remote.network.reponse.BookItems
import com.hyden.booklibrary.data.remote.network.reponse.toBookEntity
import com.hyden.booklibrary.data.repository.source.FirebaseDataSource
import com.hyden.booklibrary.util.ConstUtil.Companion.DATABASENAME_BOOK
import com.hyden.booklibrary.util.ConstUtil.Companion.FEED_LIMIT
import com.hyden.booklibrary.view.feed.model.FeedData
import com.hyden.util.LogUtil.LogW
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlin.collections.HashMap

class FeedViewModel(
    private val firebaseDataSource: FirebaseDataSource
) : BaseViewModel() {

    private val firestore by lazy { FirebaseFirestore.getInstance() }
    //    lateinit var documents: List<DocumentSnapshot>
    lateinit var lastVisible: DocumentSnapshot

    private val _feedItems = MutableLiveData<List<FeedData>>()
    val feedItems: LiveData<List<FeedData>> get() = _feedItems

    private val _userInfo = MutableLiveData<User>()
    val userInfo: LiveData<User> get() = _userInfo

    private val _isSharedUser = MutableLiveData<Boolean>()
    val isSharedUser: LiveData<Boolean> get() = _isSharedUser

    private val _userProfile = MutableLiveData<String>()
    val userProfile : LiveData<String> get() = _userProfile

    private val _userNickname = MutableLiveData<String>()
    val userNickname : LiveData<String> get() = _userNickname

//    private val _isContainsUser = MutableLiveData<Boolean>()
//    val isContainsUser : LiveData<Boolean> get() = _isContainsUser

    // 좋아요 클릭 이벤트 처리
    /**
     * 좋아요
     */
    fun pushLiked(position: Int, isLiked: Boolean) {
        _feedItems.value?.let {
            val documentId =
                it[position].feed?.sharedInfo?.users?.email + "-" + it[position].feed?.bookEntity?.isbn13
            firebaseDataSource.pushLike(isLiked, documentId)
            if (it[position].feed?.sharedInfo?.users?.email == firebaseDataSource.getLoginEmail()) {
                it[position].feed?.bookEntity?.isLiked = isLiked
                _isSharedUser.value = true
            } else {
                _isSharedUser.value = false
            }
        }
    }
    fun postLike(feed: Feed?,isSelected: Boolean) {
        _feedItems.value?.let {
            val documentId = feed?.sharedInfo?.users?.email + "-" + feed?.bookEntity?.isbn13
            firebaseDataSource.pushLike(isSelected, documentId)
            feed?.bookEntity?.isLiked = isSelected
            if (isSelected) {
                feed?.likesInfo?.users = feed?.likesInfo?.users?.toMutableList()?.apply {
                    add(firebaseDataSource.currentUser)
                }
            } else {

                compositeDisposable.add(
                    Observable.range(0,feed?.likesInfo?.users?.size ?: 0)
                        .subscribeOn(Schedulers.computation())
                        .filter { feed?.likesInfo?.users?.get(it)?.email == firebaseDataSource.getLoginEmail() }
                        .subscribe(
                            {
                                feed?.likesInfo?.users = feed?.likesInfo?.users?.toMutableList()?.apply {
                                    removeAt(it)
                                }
                            },
                            {}
                        )
                )
            }
        }
    }

    fun isContainsUser(users: List<User>): Boolean {
        return firebaseDataSource.isExistUser(users)
    }

    fun isLiked(users: List<User>?, like : () -> Unit) {
        compositeDisposable.add(
            Observable.fromIterable(users)
                .subscribeOn(Schedulers.computation())
                .filter {
                    LogW("${it}")
                    it.email == firebaseDataSource.getLoginEmail()
                }
                .subscribe(
                    { like.invoke() },
                    {}
                )
        )
    }

    /**
     * 피드에 등록된 책정보를 가져온다.
     * 공유된 시간순으로 정렬
     * 페이징 처리
     */
    fun getFireStore() {
        firestore.collection(DATABASENAME_BOOK)
            .orderBy("sharedInfo.sharedDate", Query.Direction.DESCENDING)
            .limit(FEED_LIMIT)
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
                _feedItems.value = temp
            }
    }

    fun loadMore() {
        firestore.collection(DATABASENAME_BOOK)
            .orderBy("sharedInfo.sharedDate", Query.Direction.DESCENDING)
            .limit(FEED_LIMIT)
            .startAfter(lastVisible)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.size() > 0) {
                    lastVisible = documentSnapshot.documents[documentSnapshot.size() - 1]

                    val temp = mutableListOf<FeedData>()
                    for (i in documentSnapshot.documents.indices) {
                        temp.add(FeedData(feed(documentSnapshot.documents[i].data),false))
                    }
                    _feedItems.value = _feedItems.value?.let {
                        it.toMutableList().apply {
                            addAll(temp)
                        }
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