package com.mibaldi.retorss.Preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.mibaldi.retorss.Activities.NoticiaListActivity;
import com.mibaldi.retorss.Utils.NewsFeedType;

/**
 * Created by mikelbalducieldiaz on 15/5/16.
 */
public class PreferencesManager {
    private static final String SHARED_PREFS_FILE = "RSSPrefs";
    private static final String FEED_PREF_KEY = "newsfeed";
    private static PreferencesManager instance;
    private SharedPreferences sharedPreferences;
    private Context mContext;
    private Integer urlPreference;
    public static PreferencesManager getInstance() {
        if(instance == null) {
            instance = new PreferencesManager();
        }
        return instance;
    }

    private PreferencesManager() {}

    public static @Nullable
    PreferencesManager setContext(Context context) {
        if(instance == null) {
            return null;
        }
        instance.mContext = context;
        instance.sharedPreferences = instance.getSharedPreferences();
        return instance;
    }

    private SharedPreferences getSharedPreferences() {
        return mContext.getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
    }
    public void saveNewsFeedPreference(int newsFeed) {
        this.urlPreference = newsFeed;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(FEED_PREF_KEY, newsFeed);
        editor.apply();
    }

    public NewsFeedType getSelectedNewsFeed() {
        if(urlPreference != null) {
            return NewsFeedType.NewsFeedTypeFromInt(urlPreference);
        }
        urlPreference = sharedPreferences.getInt(FEED_PREF_KEY,0);
        return NewsFeedType.NewsFeedTypeFromInt(urlPreference);
    }
}
