package com.hyden.booklibrary.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.hyden.base.BaseActivity
import com.hyden.booklibrary.R
import com.hyden.booklibrary.databinding.ActivitySplashBinding
import com.hyden.booklibrary.view.login.LoginActivity
import com.hyden.ext.moveToActivity
import com.hyden.ext.moveToActivityForResult
import com.hyden.util.LogUtil.LogW
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    companion object {
        val LOGIN_START = 99
    }
    private val splashViewModel by viewModel<SplashViewModel>()
    private val handler = Handler()
    private val runnable = Runnable {
        Intent(this, LoginActivity::class.java).run {
            val options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@SplashActivity,
                    Pair.create(binding.tvTitleLogo, getString(R.string.title_logo_transition_name))
                )
            moveToActivityForResult(this,LOGIN_START,options.toBundle())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            LOGIN_START -> {
                LogW("splash finish")
                this@SplashActivity.finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashViewModel.showLoading()
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 2000)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        splashViewModel.hideLoading()
    }

    override fun initBind() {
        binding.apply {
            vm = splashViewModel
        }
    }

}