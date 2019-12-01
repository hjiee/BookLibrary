package com.hyden.booklibrary.view.setting

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.hyden.booklibrary.BuildConfig
import com.hyden.booklibrary.R

class SettingFragment : PreferenceFragmentCompat() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting, rootKey)
        setSendToMail()
        setSendToEvaluation()
        setSendToDonate()
        preferenceManager.context.setTheme(R.style.PreferenceTheme)
    }

    private fun setSendToMail() {
        findPreference<Preference>(getString(R.string.setting_key_question))?.apply {
            setOnPreferenceClickListener {
                Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_EMAIL, resources.getStringArray(R.array.email))
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

    private fun setSendToEvaluation() {
        findPreference<Preference>(getString(R.string.setting_key_version))?.apply {
            summary = "앱 버전 : ${BuildConfig.VERSION_NAME}"
            this.context.setTheme(R.style.PreferenceTheme)
        }
    }

    private fun setSendToDonate() {
        findPreference<Preference>(getString(R.string.setting_key_donate))?.apply {
            setOnPreferenceClickListener {
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