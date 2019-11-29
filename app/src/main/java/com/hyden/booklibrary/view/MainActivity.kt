package com.hyden.booklibrary.view

import android.os.Bundle
import com.hyden.base.BaseActivity
import com.hyden.booklibrary.R
import com.hyden.booklibrary.databinding.ActivityMainBinding
import com.hyden.booklibrary.view.home.HomeFragment
import com.hyden.booklibrary.view.library.LibraryFragment
import com.hyden.booklibrary.view.search.SearchFragment
import com.hyden.booklibrary.view.setting.SettingFragment
import com.hyden.ext.replaceFragment
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replaceFragment(HomeFragment.newInstance(), binding.flContainer.id)
    }

    override fun initBind() {
        binding.apply {
            bnvMenu.apply {
                setOnNavigationItemSelectedListener {
                    when (it.itemId) {
                        R.id.menu_home -> replaceFragment(
                            HomeFragment.newInstance(),
                            binding.flContainer.flContainer.id
                        )
                        R.id.menu_search -> replaceFragment(
                            SearchFragment.newInstance(),
                            binding.flContainer.flContainer.id
                        )
                        R.id.menu_library -> replaceFragment(
                            LibraryFragment.newInstance(),
                            binding.flContainer.flContainer.id
                        )
                        R.id.menu_setting -> replaceFragment(
                            SettingFragment.newInstance(),
                            binding.flContainer.flContainer.id
                        )
                    }
                    true
                }
            }
        }
    }
}
