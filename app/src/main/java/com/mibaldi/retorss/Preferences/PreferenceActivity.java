package com.mibaldi.retorss.Preferences;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mibaldi.retorss.R;
import com.mibaldi.retorss.Utils.BaseActivity;

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
