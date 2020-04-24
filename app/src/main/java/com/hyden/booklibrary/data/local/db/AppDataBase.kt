package com.hyden.booklibrary.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hyden.booklibrary.util.ConstUtil.Companion.DATABASE_VERSION

@Database(entities = [BookEntity::class,SharedBookEntity::class], version = DATABASE_VERSION)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getBookDao() : BookDao
    abstract fun getUserDao() : UserDao
    abstract fun getSharedBookDao() : SharedBookDao

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