package com.kc_hsu.podcastlite.screen.preferences

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.kc_hsu.podcastlite.R
import com.kc_hsu.podcastlite.preferences.UserPreferences

class UserInterfacePreferenceFragment : PreferenceFragmentCompat() {
    companion object {
        fun newInstance() = UserInterfacePreferenceFragment()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preference_user_interface)
        setupUserInterfaceScreen()
    }

    override fun onStart() {
        super.onStart()
        (activity as PreferenceActivity).supportActionBar?.setTitle(R.string.user_interface_label)
    }

    private fun setupUserInterfaceScreen() {
        findPreference<Preference>(UserPreferences.PREF_THEME)
            ?.setOnPreferenceChangeListener { _, _ ->
                activity?.recreate()
                true
            }
    }
}
