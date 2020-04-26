package com.hyden.booklibrary.view.main

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.hyden.base.BaseActivity
import com.hyden.booklibrary.R
import com.hyden.booklibrary.databinding.ActivityMainBinding
import com.hyden.booklibrary.util.getPreferenceStartView
import com.hyden.booklibrary.util.getPreferenceTheme
import com.hyden.booklibrary.view.feed.FeedFragment
import com.hyden.booklibrary.view.home.HomeFragment
import com.hyden.booklibrary.view.library.LibraryFragment
import com.hyden.booklibrary.view.search.SearchFragment
import com.hyden.booklibrary.view.setting.SettingFragment
import com.hyden.ext.replaceFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private var currentNavigationView: Int = -1
    private var backKeyPressedTime = 0L
    private lateinit var toast: Toast

    private val mainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        initTheme(getPreferenceTheme())
        super.onCreate(savedInstanceState)
        if (intent.getBooleanExtra("theme", false)) {
            replaceFragment(SettingFragment.newInstance(), binding.flContainer.id)
            binding.bnvMenu.selectedItemId = R.id.menu_setting
        } else {
            when (getPreferenceStartView()) {
                "홈" -> {
                    replaceFragment(HomeFragment.newInstance(), binding.flContainer.id)
                    binding.bnvMenu.selectedItemId = R.id.menu_home
                }
                "검색" -> {
                    replaceFragment(SearchFragment.newInstance(), binding.flContainer.id)
                    binding.bnvMenu.selectedItemId = R.id.menu_search
                }
                "감상노트" -> {
                    replaceFragment(FeedFragment.newInstance(), binding.flContainer.id)
                    binding.bnvMenu.selectedItemId = R.id.menu_feed
                }
                "책꽂이" -> {
                    replaceFragment(LibraryFragment.newInstance(), binding.flContainer.id)
                    binding.bnvMenu.selectedItemId = R.id.menu_library
                }
            }
        }
    }

    override fun onBackPressed() {
        if(!supportFragmentManager.popBackStackImmediate()){
            if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                backKeyPressedTime = System.currentTimeMillis()
                toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT)
                toast.show()
                return
            }
        }

        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지나지 않았으면 종료
        // 현재 표시된 Toast 취소
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            super.onBackPressed()
            toast.cancel()
        }

    }

    fun initTheme(themeName: String) {
        when (themeName) {
            "블랙" -> {
                setTheme(R.style.NoActionBarThemeDark)
            }
            "화이트" -> {
                setTheme(R.style.NoActionBarThemeWhite)
            }
            "블루" -> {
            }
            "핑크" -> {
            }
            "그레이" -> {
            }
        }
    }

    override fun initBind() {
        binding.apply {
            vm = mainViewModel
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
