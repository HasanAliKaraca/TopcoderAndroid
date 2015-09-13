package com.example.ali.topcoderandroid.ui;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

import com.example.ali.topcoderandroid.R;

/**
 * Created by ali on 11.09.2015.
 */
public class SettingsFragment extends PreferenceFragment {

    private ListPreference mListPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }
}
