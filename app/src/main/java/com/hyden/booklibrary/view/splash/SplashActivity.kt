package com.hyden.booklibrary.view.splash

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.hyden.base.BaseActivity
import com.hyden.booklibrary.R
import com.hyden.booklibrary.databinding.ActivitySplashBinding
import com.hyden.booklibrary.util.AdsUtil
import com.hyden.booklibrary.util.RemoteConfig
import com.hyden.booklibrary.view.login.LoginActivity
import com.hyden.ext.moveToActivityForResult
import com.hyden.ext.showSimpleDialog
import com.hyden.ext.versionName
import com.hyden.util.LogUtil.LogW
import com.hyden.util.RxBus
import com.hyden.util.RxBusEvent
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    companion object {
        val LOGIN_START = 99
    }

    private val firebaseRemoteConfig by lazy { FirebaseRemoteConfig.getInstance() }
    private val firebaseRemoteSetting by lazy {
        FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(60L).build()
    }
    private val splashViewModel by viewModel<SplashViewModel>()
    private val handler = Handler()
    private val ads = AdsUtil(this@SplashActivity)
    private val finishActivity = RxBus.listen(RxBusEvent.SplashFinish::class.java)
        .subscribe { this@SplashActivity.finish() }

    private val runnableHome = Runnable {
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
        when (requestCode) {
            LOGIN_START -> {
                LogW("splash finish")
                this@SplashActivity.finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseRemoteConfig.setConfigSettingsAsync(firebaseRemoteSetting)
        firebaseRemoteConfig.setDefaultsAsync(R.xml.default_remote_config)
        firebaseRemoteConfig.fetchAndActivate()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    remoteConfig()
                }
            }
        splashViewModel.showLoading()
        density()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnableHome)
    }

    override fun onDestroy() {
        super.onDestroy()
        finishActivity.dispose()
        splashViewModel.hideLoading()
    }

    private fun remoteConfig() {
        val jsonObject = JSONObject(firebaseRemoteConfig.getString("remote"))
        when (jsonObject.getString("type")) {
            RemoteConfig.NOTICE.name -> {
                appNotice(jsonObject)
            }
            RemoteConfig.UPDATE.name -> {
                appUpdate(jsonObject)
            }
            else -> {
                goToLogin()
            }
        }

    }

    /**
     * 업데이트
     */
    private fun appUpdate(jsonObject: JSONObject) {
        if (versionName() != jsonObject.getString("version")) {
            showSimpleDialog(title = "필수 업데이트", message = "필수 업데이트가 있습니다.") {
                Intent(Intent.ACTION_VIEW).apply {
                    try {
                        data = Uri.parse("market://details?id=$packageName")
                        startActivity(this)
                    } catch (e: ActivityNotFoundException) {
                        data =
                            Uri.parse(("https://play.google.com/store/apps/details?id=$packageName"))
                    }
                }
                finish()
            }
        } else {
            goToLogin()
        }
    }

    /**
     * 공지
     */
    private fun appNotice(jsonObject: JSONObject) {
        showSimpleDialog(title = "공지사항", message = "${jsonObject.getString("message")}") {
            goToLogin()
        }
    }

    /**
     * 메인 이동
     */
    private fun goToLogin() {
        ads.loadAds { handler.postDelayed(runnableHome, 500) }
    }

    override fun initBind() {
        binding.apply {
            vm = splashViewModel
        }
    }

}