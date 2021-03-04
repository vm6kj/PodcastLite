package com.kc_hsu.podcastlite.ui.preferences

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.annotation.XmlRes
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.kc_hsu.podcastlite.R

class MainPreferenceFragment : PreferenceFragmentCompat() {

    companion object {
        private const val PREF_KEY_USER_INTERFACE = "preferenceUserInterface"

        fun newInstance() = MainPreferenceFragment()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preference_main)
        setupMainSettingScreen()
    }

    override fun onStart() {
        super.onStart()
        (activity as PreferenceActivity).supportActionBar?.setTitle(R.string.settings_title)
    }

    private fun setupMainSettingScreen() {
        findPreference<Preference>(PREF_KEY_USER_INTERFACE)?.setOnPreferenceClickListener {
            openScreen(R.xml.preference_user_interface)
            true
        }
    }

    private fun openScreen(@XmlRes prefScreenId: Int) {
        getPreferenceFragmentById(prefScreenId)?.let { fragmentWrapper ->
            (activity as PreferenceActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.content, fragmentWrapper.fragment)
                .addToBackStack(getString(fragmentWrapper.titleOfPageRes)).commit()
        }
    }

    private fun getPreferenceFragmentById(@XmlRes prefScreenId: Int): FragmentWrapper? {
        var fragmentWrapper: FragmentWrapper? = null
        when (prefScreenId) {
            R.xml.preference_user_interface -> {
                fragmentWrapper = FragmentWrapper(
                    UserInterfacePreferenceFragment.newInstance(),
                    R.string.user_interface_label
                )
            }
        }
        return fragmentWrapper
    }

    private data class FragmentWrapper(val fragment: PreferenceFragmentCompat, @StringRes val titleOfPageRes: Int)
}
