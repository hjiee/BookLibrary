package com.hyden.booklibrary.view

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.hyden.base.BaseActivity
import com.hyden.booklibrary.R
import com.hyden.booklibrary.databinding.ActivityMainBinding
import com.hyden.booklibrary.view.feed.FeedFragment
import com.hyden.booklibrary.view.home.HomeFragment
import com.hyden.booklibrary.view.library.LibraryFragment
import com.hyden.booklibrary.view.search.SearchFragment
import com.hyden.booklibrary.view.setting.SettingFragment
import com.hyden.ext.replaceFragment

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private var currentNavigationView: Int = -1
    private var backKeyPressedTime = 0L
    private lateinit var toast: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replaceFragment(HomeFragment.newInstance(), binding.flContainer.id)
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis()
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT)
            toast.show()
            return
        }
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지나지 않았으면 종료
        // 현재 표시된 Toast 취소
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            super.onBackPressed()
            toast.cancel()
        }

    }

    override fun initBind() {
        binding.apply {
            bnvMenu.apply {
                setOnNavigationItemSelectedListener {
                    if (currentNavigationView != it.itemId) {
                        currentNavigationView = it.itemId
                        when (it.itemId) {
                            R.id.menu_home -> replaceFragment(
                                HomeFragment.newInstance(),
                                binding.flContainer.id
                            )
                            R.id.menu_search -> replaceFragment(
                                SearchFragment.newInstance(),
                                binding.flContainer.id
                            )
                            R.id.menu_feed -> replaceFragment(
                                FeedFragment.newInstance(),
                                binding.flContainer.id
                            )
                            R.id.menu_library -> replaceFragment(
                                LibraryFragment.newInstance(),
                                binding.flContainer.id
                            )
                            R.id.menu_setting -> replaceFragment(
                                SettingFragment.newInstance(),
                                binding.flContainer.id
                            )
                        }
                    }
                    true
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

        }
        return super.onOptionsItemSelected(item)
    }
}
