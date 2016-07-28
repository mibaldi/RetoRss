package com.mibaldi.retorssandroid.Preferences;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.widget.Toolbar;

import com.mibaldi.retorssandroid.R;
import com.mibaldi.retorssandroid.Utils.NewsFeedType;
import com.mibaldi.retorssandroid.Utils.NewsFeedUtils;

/**
 * Created by mikelbalducieldiaz on 15/5/16.
 */
public class PreferencesFragment  extends PreferenceFragment{
    private Toolbar mToolbar;


    public void setToolbar(Toolbar mToolbar) {
        this.mToolbar = mToolbar;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        final ListPreference listPreference = (ListPreference) findPreference("newsfeed");
        listPreference.setValueIndex(PreferencesManager.getInstance().getSelectedNewsFeed().ordinal());
        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                listPreference.setValueIndex(Integer.parseInt(newValue.toString()));
                PreferencesManager.getInstance().saveNewsFeedPreference(Integer.valueOf(newValue.toString()));
                applyNewsFeed();
                return false;
            }
        });
    }

    private void applyNewsFeed() {
        NewsFeedType newsfeed = PreferencesManager.getInstance().getSelectedNewsFeed();
        NewsFeedUtils.applyNewsFeed(newsfeed);

    }
}
