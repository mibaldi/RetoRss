package com.mibaldi.retorss.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.mibaldi.retorss.Adapters.NoticiasRecyclerViewAdapter;
import com.mibaldi.retorss.Fragments.NoticiaDetailFragment;
import com.mibaldi.retorss.Models.Noticia;
import com.mibaldi.retorss.R;
import com.mibaldi.retorss.Rss.ParseadorRSSXML;
import com.mibaldi.retorss.Utils.CustomComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * An activity representing a list of Noticias. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link NoticiaDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class NoticiaListActivity extends AppCompatActivity {


    private static final String URL2 = "http://estaticos.elmundo.es/elmundo/rss/portada.xml";
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private static boolean mTwoPane;
    private List<Noticia> noticias = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    private NoticiasRecyclerViewAdapter noticiasRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        mProgressDialog = new ProgressDialog(this);
        View recyclerView = findViewById(R.id.noticia_list);
        assert recyclerView != null;


        if (findViewById(R.id.noticia_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }else {
            mTwoPane = false;
        }
        setupRecyclerView((RecyclerView) recyclerView);
        MyXmlAsyncTask myXmlAsyncTask = new MyXmlAsyncTask();
        myXmlAsyncTask.execute(URL2);

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
         noticiasRecyclerViewAdapter = new NoticiasRecyclerViewAdapter(noticias);
        recyclerView.setAdapter(noticiasRecyclerViewAdapter);
    }
    public static boolean ismTwoPane() {
        return mTwoPane;
    }
    private class MyXmlAsyncTask extends AsyncTask<String, Void, List<Noticia>> {

        @Override
        protected void onPreExecute() {

           mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage(getString(R.string.progressDialogMessage));
            mProgressDialog.show();
        }

        @Override
        protected List<Noticia> doInBackground(String... params) {
            ParseadorRSSXML saxparser = new ParseadorRSSXML(params[0]);
            return saxparser.parse();
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
