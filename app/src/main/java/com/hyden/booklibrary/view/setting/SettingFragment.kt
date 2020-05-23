package com.hyden.booklibrary.view.setting

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.firebase.auth.FirebaseAuth
import com.hyden.booklibrary.R
import com.hyden.booklibrary.util.*
import com.hyden.booklibrary.view.OpenSourceActivity
import com.hyden.booklibrary.view.profile.ProfileActivity
import com.hyden.booklibrary.view.login.LoginActivity
import com.hyden.booklibrary.view.myshared.MySharedBookFragment
import com.hyden.ext.*
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment : PreferenceFragmentCompat() {

    private val settingViewModel by viewModel<SettingViewModel>()
    private val CONTACT_DIRECTELY = 22

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            CONTACT_DIRECTELY -> {
                if(resultCode == RESULT_OK){
                    Toast.makeText(context, "문의메일 보내기에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting, rootKey)
        changeMyFeedBook()
        changeStartView()
        changeLoginState()
        changeProfile()
        sendToMail()
        sendToEvaluation()
        infoAppVersion()
        infoOpenSourceLicense()
        preferenceManager.context.setTheme(R.style.PreferenceTheme)
    }

    // 문의 하기
    private fun sendToMail() {
        findPreference<Preference>(getString(R.string.setting_key_question))?.apply {
            setOnPreferenceClickListener {
                Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    setPackage("com.google.android.gm")
                    putExtra(Intent.EXTRA_EMAIL, resources.getStringArray(R.array.developer_email))
                    putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject))
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "모델명 : ${Build.MODEL}\n" +
                                "OS버전 : ${Build.VERSION.RELEASE}\n" +
                                "SDK버전 : ${Build.VERSION.SDK_INT}\n" +
                                "앱버전 : ${context.versionName()}\n " +
                                "-----------------------------------------\n\n"
                    )
                    moveToActivityForResult(this,CONTACT_DIRECTELY)
                }
                true
            }
        }
    }
    
    // 내가 공유한 책 정보
    private fun changeMyFeedBook() {
        findPreference<Preference>(getString(R.string.setting_key_my_feed_book))?.apply {
            setOnPreferenceClickListener {
                replaceFragmentStack(MySharedBookFragment.newInstance(),activity?.fl_container?.id!!)
                true
            }
        }
    }

    // 시작 화면 변경
    private fun changeStartView() {
        findPreference<ListPreference>(getString(R.string.setting_key_start_view))?.apply {
            summary = getPreferenceStartView()

            setOnPreferenceClickListener {
                true
            }
            setOnPreferenceChangeListener { preference, newValue ->
                preference.summary = newValue.toString()
                setPreferenceStartView(newValue.toString())
                true
            }
        }
    }

    // 프로필 설정 변경
    private fun changeProfile() {
        findPreference<Preference>(getString(R.string.setting_key_profile))?.apply {
            setOnPreferenceClickListener {
                moveToActivity(Intent(activity,
                    ProfileActivity::class.java))
                true
            }
        }

    }

    // 로그아웃
    private fun changeLoginState() {
        findPreference<Preference>(getString(R.string.setting_key_logout))?.apply {
            setOnPreferenceClickListener {

                FirebaseAuth.getInstance().apply {
                    context.showSimpleDialog(message = "로그아웃 하시겠습니까?") {
                        settingViewModel.signOut()
                        moveToActivity(Intent(activity,LoginActivity::class.java))
                        activity?.finish()
                    }
                }
                true
            }
        }
    }

    private fun sendToEvaluation() {
    }

    private fun infoAppVersion() {
        findPreference<Preference>(getString(R.string.setting_key_version))?.apply {
            summary = "앱 버전 : ${context.versionName()}"
            this.context.setTheme(R.style.PreferenceTheme)
        }
    }

    // 오픈소스 라이브러리 정보
    private fun infoOpenSourceLicense() {
        findPreference<Preference>(getString(R.string.setting_key_opensource))?.apply {
            setOnPreferenceClickListener {
                moveToActivity(Intent(activity,OpenSourceActivity::class.java))
                true
            }
        }
    }

    companion object {
        fun newInstance() = SettingFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}