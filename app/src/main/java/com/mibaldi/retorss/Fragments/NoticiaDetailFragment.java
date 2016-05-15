package com.mibaldi.retorss.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mibaldi.retorss.Activities.NoticiaDetailActivity;
import com.mibaldi.retorss.Activities.NoticiaListActivity;
import com.mibaldi.retorss.DB.NoticiasSQLiteHelper;
import com.mibaldi.retorss.Models.Noticia;
import com.mibaldi.retorss.R;
import com.mibaldi.retorss.Utils.DateFormatter;
import com.squareup.picasso.Picasso;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A fragment representing a single Noticia detail screen.
 * This fragment is either contained in a {@link NoticiaListActivity}
 * in two-pane mode (on tablets) or a {@link NoticiaDetailActivity}
 * on handsets.
 */
public class NoticiaDetailFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.noticia_detail)
    TextView detail;
    @Bind(R.id.pubdate)
    TextView pubdate;
    @Bind(R.id.noticia_title)
    TextView title;
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.imageView2)
    ImageView photo;
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    private ImageView newsPhoto;

    private Noticia mItem;
    private FragmentActivity activity;
    private SQLiteDatabase db;

    public static NoticiaDetailFragment newInstance(Noticia news) {
        NoticiaDetailFragment noticiaDetailFragment = new NoticiaDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ITEM_ID, news);
        noticiaDetailFragment.setArguments(args);
        return noticiaDetailFragment;
    }
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NoticiaDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            mItem = getArguments().getParcelable(ARG_ITEM_ID);

            activity = this.getActivity();
            NoticiasSQLiteHelper newsSQLH = new NoticiasSQLiteHelper(activity, NoticiasSQLiteHelper.DATABASE_NAME,
                    null, NoticiasSQLiteHelper.DATABASE_VERSION);

            db = newsSQLH.getWritableDatabase();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.noticia_detail, container, false);
        ButterKnife.bind(this,rootView);
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(" ");
        }
        newsPhoto = (ImageView) activity.findViewById(R.id.newsPhoto);

        if (mItem != null) {
            button.setOnClickListener(this);
            if (newsPhoto != null){
                Picasso.with(activity).load(mItem.getImage()).into(newsPhoto);
                photo.setVisibility(View.GONE);
            }else{
                Picasso.with(activity).load(mItem.getImage()).into(photo);
            }

            title.setText(mItem.getTitle());
            detail.setText(Html.fromHtml(mItem.getDescription()));
            Date fecha = mItem.getPubDate();
            pubdate.setText(DateFormatter.convertDateToString(fecha));
            if(!NoticiasSQLiteHelper.CheckExist(db,mItem.getUrl()))
                NoticiasSQLiteHelper.insertNoticia(db,mItem);
        }

        return rootView;
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(mItem.getUrl()));
                activity.startActivity(intent);
        }
    }
}
