package com.hyden.booklibrary.view.setting

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
import com.hyden.booklibrary.BuildConfig
import com.hyden.booklibrary.R
import com.hyden.booklibrary.view.OpenSourceActivity
import com.hyden.ext.moveToActivity

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
        changeTheme()
        setSendToMail()
        setSendToEvaluation()
        setSendToDonate()
        infoAppVersion()
        infoOpenSourceLicense()
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

    private fun changeTheme() {
        findPreference<ListPreference>(getString(R.string.setting_key_theme))?.apply {
            summary = this.value
            setOnPreferenceClickListener {
                true
            }
            setOnPreferenceChangeListener { preference, newValue ->
                preference.summary = newValue.toString()
                true
            }
        }
    }
    private fun setSendToEvaluation() {
    }

    private fun setSendToDonate() {
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