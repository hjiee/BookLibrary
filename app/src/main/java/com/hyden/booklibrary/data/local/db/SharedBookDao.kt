package com.hyden.booklibrary.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import io.reactivex.Completable

@Dao
interface SharedBookDao {
    @Insert
    fun insert(
        sharedEntity: List<SharedBookEntity?>
    ): Completable
}