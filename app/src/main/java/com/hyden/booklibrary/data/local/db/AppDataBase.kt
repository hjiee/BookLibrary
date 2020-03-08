package com.hyden.booklibrary.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hyden.booklibrary.util.ConstUtil.Companion.DATABASENAME

@Database(entities = [BookEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getBookDao() : BookDao
    abstract fun getUserDao() : UserDao

//    companion object {
//        var INSTANCE: BookDataBase? = null
//        fun getDatabase(context: Context): BookDataBase? {
//            if (INSTANCE == null) {
//                INSTANCE = Room.databaseBuilder(
//                    context,
//                    BookDataBase::class.java,
//                    "${DATABASENAME}.db"
//                )
//                    .build()
//            }
//            return INSTANCE
//        }
//    }
}