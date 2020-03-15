package com.hyden.booklibrary.data.local.db

import androidx.room.Entity
import com.hyden.booklibrary.util.ConstUtil.Companion.FIRESTORE_USERS

@Entity(tableName = FIRESTORE_USERS)
data class UserEntity(
    var userEmail : String,
    var userName : String,
    var userNickName : String,
    var userProfile : String
)