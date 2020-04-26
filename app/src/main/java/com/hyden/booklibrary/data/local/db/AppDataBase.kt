package com.hyden.booklibrary.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hyden.booklibrary.util.ConstUtil.Companion.DATABASE_VERSION

@Database(entities = [BookEntity::class], version = DATABASE_VERSION)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getBookDao() : BookDao

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