package com.hyden.booklibrary.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.hyden.base.BaseActivity
import com.hyden.booklibrary.R
import com.hyden.booklibrary.databinding.ActivitySplashBinding
import com.hyden.booklibrary.view.MainActivity
import com.hyden.booklibrary.view.login.LoginActivity
import com.hyden.booklibrary.view.login.LoginViewModel
import com.hyden.ext.moveToActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: 2020-01-02 로그인 상태를 확인해서 로그인중이면 Main 아니면 Login 화면으로 전환
        Intent(this,LoginActivity::class.java).run {
            Handler().postDelayed({
                moveToActivity(this)
                finish()
            },2000)

        }

    }

    override fun initBind() {

    }

}