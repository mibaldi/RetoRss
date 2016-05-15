package com.mibaldi.retorss.Fragments;

import android.app.Activity;
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
import com.mibaldi.retorss.Models.Noticia;
import com.mibaldi.retorss.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

/**
 * A fragment representing a single Noticia detail screen.
 * This fragment is either contained in a {@link NoticiaListActivity}
 * in two-pane mode (on tablets) or a {@link NoticiaDetailActivity}
 * on handsets.
 */
public class NoticiaDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    @Bind(R.id.noticia_detail)
    TextView detail;
    @Bind(R.id.pubdate)
    TextView pubdate;
    @Bind(R.id.noticia_title)
    TextView title;
    @Bind(R.id.button)
    Button button;
    public static final String ARG_ITEM_ID = "item_id";
    private ImageView newsPhoto;
    /**
     * The dummy content this fragment is presenting.
     */
    private Noticia mItem;
    private FragmentActivity activity;

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
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = getArguments().getParcelable(ARG_ITEM_ID);

            activity = this.getActivity();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.noticia_detail, container, false);
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(" ");
        }
        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            if (newsPhoto != null)
                Picasso.with(activity).load(mItem.getImage()).into(newsPhoto);
            title.setText(mItem.getTitle());
            detail.setText(Html.fromHtml(mItem.getDescription()));
        }

        return rootView;
    }
}
