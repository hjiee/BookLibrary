package com.hyden.booklibrary.data.model

import com.hyden.booklibrary.data.local.db.BookEntity
import java.util.*

data class Feed(
    val sharedDate: Date,
    val bookEntity: BookEntity,
    val commentsCount: Long,
    val commentsInfo: Comment,
    val likesCount: Long,
    val likesInfo: Like,
    val usersInfo: User
)

fun Feed.toFeed(): Feed {
    return Feed(
        sharedDate,
        bookEntity,
        commentsCount,
        commentsInfo,
        likesCount,
        likesInfo,
        usersInfo
    )
}

fun <T> T.toDate(): Date {
    when (this) {
        is HashMap<*, *> -> {
            return this["sharedDate"] as Date
        }
    }
    return Date()
}

data class Comment(
    val users: List<User>?
)

fun <T> T.toComment(): Comment {
    return Comment(getUser())
}

data class Like(
    val users: List<User>?
)

fun <T> T.toLike(): Like {
    return Like(getUser())
}

data class User(
    val id: String,
    val name: String
)

fun <T> T.toUser(): User {

    return getUser()[0] ?: User("", "")
}

fun <T> T.getUser(): List<User> {
    val temp = mutableListOf<User>()
    when (this) {
        is HashMap<*, *> -> {
            (this["users"] as? ArrayList<HashMap<*, *>>)?.run {
                for (i in indices) {
                    temp.add(User(id = get(i)["id"].toString(), name = get(i)["name"].toString()))
                }
            } ?: temp.add( User(id = this["id"].toString(), name = this["name"].toString()))
        }
    }
    return temp
}