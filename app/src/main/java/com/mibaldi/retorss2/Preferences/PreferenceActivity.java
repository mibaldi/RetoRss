package com.mibaldi.retorss2.Preferences;

import android.os.Bundle;

import com.mibaldi.retorss2.R;
import com.mibaldi.retorss2.Utils.BaseActivity;

public class PreferenceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        PreferencesFragment preferencesFragment = new PreferencesFragment();
        preferencesFragment.setToolbar(mToolbar);
        getFragmentManager().beginTransaction().
                replace(R.id.contentPreferences, preferencesFragment).commit();
    }
}
