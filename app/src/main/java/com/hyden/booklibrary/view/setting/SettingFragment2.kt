package com.hyden.booklibrary.view.setting

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.library.BuildConfig
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.firebase.auth.FirebaseAuth
import com.hyden.base.BaseFragment
import com.hyden.base.BaseRecyclerView
import com.hyden.booklibrary.BR
import com.hyden.booklibrary.R
import com.hyden.booklibrary.databinding.FragmentSetting2Binding
import com.hyden.booklibrary.databinding.RecyclerItemFeedBinding
import com.hyden.booklibrary.databinding.RecyclerItemSettingBinding
import com.hyden.booklibrary.util.*
import com.hyden.booklibrary.view.MainActivity
import com.hyden.booklibrary.view.OpenSourceActivity
import com.hyden.booklibrary.view.profile.ProfileActivity
import com.hyden.booklibrary.view.login.LoginActivity
import com.hyden.ext.showSimpleDialog
import com.hyden.ext.moveToActivity
import com.hyden.util.ItemClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment2 : BaseFragment<FragmentSetting2Binding>(R.layout.fragment_setting2) {

    private val settingViewModel by viewModel<SettingViewModel>()

    private val items = mutableListOf<PreferenceData>()
    private val itemClickListener by lazy {
        object : ItemClickListener {
            override fun <T> onItemClick(item: T) {

            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 기본 설정
        items.add(PreferenceData(PreferenceType.BASIC,getString(R.string.setting_category_base_setting),""))

        // 도움말
        items.add(PreferenceData(PreferenceType.HELP,getString(R.string.setting_category_help),""))

        // 앱 정보
        items.add(PreferenceData(PreferenceType.INFOMATION,getString(R.string.setting_category_infomation),""))
    }

    override fun initBind() {
        binding.apply {
            vm = settingViewModel
            rvSetting.apply {
                adapter = object : BaseRecyclerView.SimpleAdapter<RecyclerItemSettingBinding,PreferenceData>(
                    R.layout.fragment_setting2,
                    items,
                    BR.data,
                    itemClickListener
                ) {}
            }
        }
    }

    companion object {
        fun newInstance() = SettingFragment2().apply {
            arguments = Bundle().apply {

            }
        }
    }
}

data class PreferenceData(
    val type : PreferenceType,
    val title : String,
    val summary : String
)

enum class PreferenceType {
    /**
     * 기본설정
     */
    BASIC,
    /**
     * 도움말
     */
    HELP,
    /**
     * 앱 정보
     */
    INFOMATION,
    /**
     * 하위 카테고리
     */
    SUBCTAGORY
}