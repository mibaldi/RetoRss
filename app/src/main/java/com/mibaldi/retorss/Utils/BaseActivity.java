package com.mibaldi.retorss.Utils;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mibaldi.retorss.Preferences.PreferencesManager;
import com.mibaldi.retorss.R;

/**
 * Created by mikelbalducieldiaz on 15/5/16.
 */
public class BaseActivity extends AppCompatActivity {
    protected Toolbar mToolbar;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        applySelectedNewsFeed();
    }


    private void applySelectedNewsFeed() {
        PreferencesManager.getInstance().getSelectedNewsFeed();
    }
}
