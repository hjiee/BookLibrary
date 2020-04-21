package com.hyden.booklibrary.data.repository

import com.hyden.booklibrary.data.local.db.SharedBookDao
import com.hyden.booklibrary.data.repository.source.BookDataSource

class SharedBookRepository(
    val sharedBookDao: SharedBookDao
) {


}