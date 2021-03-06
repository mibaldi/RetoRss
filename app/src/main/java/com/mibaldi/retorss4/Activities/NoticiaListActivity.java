package com.mibaldi.retorss4.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import com.crashlytics.android.Crashlytics;
import com.mibaldi.retorss4.Adapters.NoticiasRecyclerViewAdapter;
import com.mibaldi.retorss4.DB.NoticiasSQLiteHelper;
import com.mibaldi.retorss4.Models.Noticia;
import com.mibaldi.retorss4.Preferences.PreferenceActivity;
import com.mibaldi.retorss4.Preferences.PreferencesManager;
import com.mibaldi.retorss4.R;
import com.mibaldi.retorss4.Rss.ParseadorRSSXML;
import com.mibaldi.retorss4.Utils.CustomComparator;
import com.mibaldi.retorss4.Utils.NetworkHelper;
import com.mibaldi.retorss4.Utils.NewsFeedType;
import com.mibaldi.retorss4.Utils.NewsFeedUtils;

import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



/**
 * An activity representing a list of Noticias. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link NoticiaDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class NoticiaListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {


    public static String URL2 = "http://estaticos.elmundo.es/elmundo/rss/portada.xml";
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private static boolean mTwoPane;
    private List<Noticia> noticias = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    private NoticiasRecyclerViewAdapter noticiasRecyclerViewAdapter;
    private SQLiteDatabase db;
    private MenuItem myActionMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        PreferencesManager.getInstance().setContext(getApplicationContext());
        setContentView(R.layout.activity_noticia_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (toolbar != null)
        toolbar.setTitle(getTitle());

        mProgressDialog = new ProgressDialog(this);
        View recyclerView = findViewById(R.id.noticia_list);
        assert recyclerView != null;


        mTwoPane=(findViewById(R.id.noticia_detail_container) != null);

        setupRecyclerView((RecyclerView) recyclerView);



    }

    @Override
    protected void onResume() {
        super.onResume();
        applyPreferences();

        populateList();
    }

    private void applyPreferences() {
        NewsFeedType newsfeed = PreferencesManager.getInstance().getSelectedNewsFeed();
        NewsFeedUtils.applyNewsFeed(newsfeed);
        switch (newsfeed){
            case PORTADA:
                setTitle(getString(R.string.Portada));
                break;
            case ESPAÑA:
                setTitle(getString(R.string.España));
                break;
            case DEPORTE:
                setTitle(getString(R.string.Deportes));
                break;
        }
    }

    private void populateList() {
        noticias.clear();
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        if (NetworkHelper.getInstance(this).isConnected()){

            myAsyncTask.execute(URL2);

        }else{
            setTitle(getString(R.string.offline));
            NoticiasSQLiteHelper newsSQLH = new NoticiasSQLiteHelper(NoticiaListActivity.this, NoticiasSQLiteHelper.DATABASE_NAME,
                    null, NoticiasSQLiteHelper.DATABASE_VERSION);

            db = newsSQLH.getWritableDatabase();
            myAsyncTask.execute("");

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        myActionMenuItem = menu.findItem(R.id.action_search);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(getApplicationContext(), PreferenceActivity.class);
                startActivity(intent);
               // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                return true;
            case R.id.action_search:

                SearchView searchView = (SearchView) myActionMenuItem.getActionView();
                searchView.setOnQueryTextListener(this);

                MenuItemCompat.setOnActionExpandListener(myActionMenuItem, new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        return true;
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Noticia> filteredModelList = filter(noticias, newText);
        noticiasRecyclerViewAdapter.setFilter(filteredModelList);
        return false;
    }

    private List<Noticia> filter(List<Noticia> noticias, String newText) {
        newText = newText.toLowerCase();
        final ArrayList<Noticia> filteredNoticias = new ArrayList<>();
        for (Noticia noticia : noticias) {
            final String title = noticia.getTitle().toLowerCase();
            if (title.contains(newText)) {
                filteredNoticias.add(noticia);
            }
        }
        return filteredNoticias;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
         noticiasRecyclerViewAdapter = new NoticiasRecyclerViewAdapter(noticias);
        recyclerView.setAdapter(noticiasRecyclerViewAdapter);
    }
    public static boolean ismTwoPane() {
        return mTwoPane;
    }



    private class MyAsyncTask extends AsyncTask<String, Void, List<Noticia>> {

        @Override
        protected void onPreExecute() {

           mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage(getString(R.string.progressDialogMessage));
            mProgressDialog.show();
        }

        @Override
        protected List<Noticia> doInBackground(String... params) {
            if (!params[0].isEmpty()){
                ParseadorRSSXML saxparser = new ParseadorRSSXML(params[0]);
                return saxparser.parse();
            }else{
                return Noticia.verMisNoticias(db);
            }

        }

        @Override
        protected void onPostExecute(List<Noticia> items) {
            if (mProgressDialog.isShowing())
                mProgressDialog.dismiss();
            noticias.addAll(items);
            Collections.sort(noticias, new CustomComparator());
            noticiasRecyclerViewAdapter.notifyDataSetChanged();
        }
    }


}
