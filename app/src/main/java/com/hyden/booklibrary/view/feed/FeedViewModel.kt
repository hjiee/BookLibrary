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
import com.hyden.booklibrary.util.ConstUtil.Companion.DATABASENAME
import com.hyden.booklibrary.util.ConstUtil.Companion.FEED_LIMIT
import com.hyden.util.LogUtil.LogE
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class FeedViewModel(
    private val firebaseDataSource: FirebaseDataSource
) : BaseViewModel() {

    private val firestore by lazy { FirebaseFirestore.getInstance() }
    //    lateinit var documents: List<DocumentSnapshot>
    lateinit var lastVisible: DocumentSnapshot

    var _feedItems = MutableLiveData<List<Feed>>()

    private val _userInfo = MutableLiveData<User>()
    val userInfo: LiveData<User> get() = _userInfo

    private val _isSharedUser = MutableLiveData<Boolean>()
    val isSharedUser: LiveData<Boolean> get() = _isSharedUser

    private val _userProfile = MutableLiveData<String>()
    val userProfile: LiveData<String> get() = _userProfile

    private val _userNickname = MutableLiveData<String>()
    val userNickname: LiveData<String> get() = _userNickname

    // 좋아요 클릭 이벤트 처리
    /**
     * 좋아요
     */
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

    fun isContainsUser(pos: Int): Boolean {
        getFeedItem(pos)?.let {
            it.likesInfo.users?.let {
                return isContainsUser(it)
            }
        }
        return false
    }

//    fun getUser() {
//        firestore.collection(DATABASENAME).document(firebaseDataSource.getLoginEmail()).get()
//            .addOnCompleteListener {
//                val email = it.result?.get("email").toString()
//                val name = it.result?.get("name").toString()
//                val nickName = it.result?.get("nickName").toString()
//                val profile = it.result?.get("profile").toString()
//                _userInfo.value = User(
//                    email = email,
//                    name = name,
//                    nickName = nickName,
//                    profile = profile
//                )
//            }
//    }

    /**
     * 피드에 등록된 책정보를 가져온다.
     * 공유된 시간순으로 정렬
     * 페이징 처리
     */
    fun getFireStore() {
        firestore.collection(DATABASENAME)
            .orderBy("sharedInfo.sharedDate", Query.Direction.DESCENDING)
            .limit(FEED_LIMIT)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                // 파이어베이스 페이징
                if (documentSnapshot.size() >= 1) {
                    lastVisible = documentSnapshot.documents[documentSnapshot.size() - 1]
                }
                val temp = mutableListOf<Feed>()
                for (i in documentSnapshot.documents.indices) {
//                    ((documentSnapshot.documents[i].data?.get("sharedInfo") as HashMap<*,*>).get("users") as HashMap<*,*>)["email"]
//                    ((documentSnapshot.documents[i].data?.get("sharedInfo") as HashMap<*,*>).get("users") as HashMap<*,*>)["name"]
//                    ((documentSnapshot.documents[i].data?.get("sharedInfo") as HashMap<*,*>).get("users") as HashMap<*,*>)["nickName"]
//                    ((documentSnapshot.documents[i].data?.get("sharedInfo") as HashMap<*,*>).get("users") as HashMap<*,*>)["profile"]
                    temp.add(feed(documentSnapshot.documents[i].data))
                }
                _feedItems.value = temp
            }
    }

    fun loadMore() {
        firestore.collection(DATABASENAME)
            .orderBy("sharedInfo.sharedDate", Query.Direction.DESCENDING)
            .limit(FEED_LIMIT)
            .startAfter(lastVisible)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.size() > 0) {
                    lastVisible = documentSnapshot.documents[documentSnapshot.size() - 1]

                    val temp = mutableListOf<Feed>()
                    for (i in documentSnapshot.documents.indices) {
                        temp.add(feed(documentSnapshot.documents[i].data))
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
                get("commentsCount").toString().toLong(),
                get("commentsInfo").toComment(),
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


    fun getFeedItem(pos: Int): Feed? {
        _feedItems.value?.let {
            return it[pos]
        }
        return null
    }

    fun toggleStateFeedMoreOption(pos : Int, state:Boolean):Boolean{
        getFeedItem(pos)?.let {
             it.expandableState= state.not()
            return it.expandableState
        }
        return false
    }


    fun getStateExpandableState(pos : Int):Boolean{
        getFeedItem(pos)?.let {
         return it.expandableState
        }
        return true
    }

}