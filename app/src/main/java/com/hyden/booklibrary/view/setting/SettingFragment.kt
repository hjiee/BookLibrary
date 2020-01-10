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
import com.hyden.booklibrary.R
import com.hyden.booklibrary.util.*
import com.hyden.booklibrary.view.MainActivity
import com.hyden.booklibrary.view.OpenSourceActivity
import com.hyden.booklibrary.view.login.LoginActivity
import com.hyden.ext.moveToActivity
import org.koin.android.ext.android.inject

class SettingFragment : PreferenceFragmentCompat() {

    private val settingViewModel by inject<SettingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting, rootKey)
        changeTheme()
        changeStartView()
        changeLoginState()
        sendToMail()
        sendToEvaluation()
        sendToDonate()
        infoAppVersion()
        infoOpenSourceLicense()
        preferenceManager.context.setTheme(R.style.PreferenceTheme)
    }

    private fun sendToMail() {
        findPreference<Preference>(getString(R.string.setting_key_question))?.apply {
            setOnPreferenceClickListener {
                Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_EMAIL, resources.getStringArray(R.array.developer_email))
                    putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject))
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "모델명 : ${Build.MODEL}\n" +
                                "OS버전 : ${Build.VERSION.RELEASE}\n" +
                                "앱버전 : ${BuildConfig.VERSION_NAME}\n " +
                                "-----------------------------------------\n\n"
                    )
                    startActivity(this)
                }
                true
            }
        }
    }

    private fun changeTheme() {
        findPreference<ListPreference>(getString(R.string.setting_key_theme))?.apply {
            summary = getPreferenceTheme()
            setOnPreferenceClickListener {
                true
            }
            setOnPreferenceChangeListener { preference, newValue ->
                preference.summary = newValue.toString()
                setPreferenceTheme(newValue.toString())
                Intent(activity,MainActivity::class.java).apply {
                    putExtra("theme",true)
                    moveToActivity(this)
                }
                activity?.finish()
                true
            }
        }
    }

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
    private fun changeLoginState() {
        findPreference<Preference>(getString(R.string.setting_key_logout))?.apply {
            setOnPreferenceClickListener {

                FirebaseAuth.getInstance().apply {
                    context.dialogSimple("로그아웃 하시겠습니까?") {
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

    private fun sendToDonate() {
        findPreference<Preference>(getString(R.string.setting_key_donate))?.apply {
            setOnPreferenceClickListener {
                true
            }
        }
    }

    private fun infoAppVersion() {
        findPreference<Preference>(getString(R.string.setting_key_version))?.apply {
            summary = "앱 버전 : ${BuildConfig.VERSION_NAME}"
            this.context.setTheme(R.style.PreferenceTheme)
        }
    }
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