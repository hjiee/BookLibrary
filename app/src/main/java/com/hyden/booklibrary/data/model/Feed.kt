package com.hyden.booklibrary.data.model

import com.hyden.booklibrary.data.local.db.BookEntity
import java.text.SimpleDateFormat
import java.util.*

data class Feed(
    val sharedInfo: SharedInfo,
    val bookEntity: BookEntity,
    var commentsCount: Long,
    val commentsInfo: Comment,
    var likesCount: Long,
    val likesInfo: Like
//    val usersInfo: User
)

data class SharedInfo(
    val sharedDate: Date,
    val users: User
)

data class Comment(
    val users: List<User>?
)

data class Like(
    val users: List<User>?
)

data class User(
    val email: String,
    val name: String,
    val nickName: String = "",
    val profile: String = "",
    val updateAt : Date
)

fun Feed.toFeed(): Feed {
    return Feed(
        sharedInfo,
        bookEntity,
        commentsCount,
        commentsInfo,
        likesCount,
        likesInfo
//        usersInfo
    )
}


fun <T> T.toSharedInfo(): SharedInfo = SharedInfo(getDate(), getSaredUser() ?: User("", "",updateAt = Date()))
fun <T> T.toComment(): Comment = Comment(getUser())
fun <T> T.toLike(): Like = Like(getUser())
fun <T> T.toUser(): User = getUser()[0] ?: User("", "",updateAt = Date())
fun <T> T.getUser(): List<User> {
    val temp = mutableListOf<User>()
    when (this) {
        is HashMap<*, *> -> {
            (this["users"] as? ArrayList<HashMap<*, *>>)?.run {
                for (i in indices) {
                    temp.add(
                        User(
                            email = get(i)["email"].toString(),
                            name = get(i)["name"].toString(),
                            nickName = get(i)["nickName"].toString(),
                            profile = get(i)["profile"].toString(),
                            updateAt = Date()
                        )
                    )
                }
            } ?: temp.add(
                User(
                    email = this["email"].toString(),
                    name = this["name"].toString(),
                    nickName = this["nickName"].toString(),
                    profile = this["profile"].toString(),
                    updateAt = Date()
                )
            )
        }
    }
    return temp
}

fun <T> T.getDate(): Date {
    when (this) {
        is HashMap<*, *> -> {
            val timestamp = this["sharedDate"] as com.google.firebase.Timestamp
            val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
            val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
            val netDate = Date(milliseconds)
            val date = sdf.format(netDate).toString()
            return netDate
        }
    }
    return Date()
}

fun <T> T.getSaredUser(): User {
    var user = User("", "",updateAt = Date())
    when (this) {
        is HashMap<*, *> -> {
            user = User(
                email = (this["users"] as HashMap<*, *>)["email"].toString(),
                name = (this["users"] as HashMap<*, *>)["name"].toString(),
                nickName = (this["users"] as HashMap<*, *>)["nickName"].toString(),
                profile = (this["users"] as HashMap<*, *>)["profile"].toString(),
                updateAt = Date()
            )
        }
    }
    return user

}
