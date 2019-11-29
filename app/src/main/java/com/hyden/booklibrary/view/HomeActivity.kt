package com.hyden.booklibrary.view

import android.os.Bundle
import com.hyden.base.BaseActivity
import com.hyden.booklibrary.R
import com.hyden.booklibrary.databinding.ActivityMainBinding

class HomeActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
