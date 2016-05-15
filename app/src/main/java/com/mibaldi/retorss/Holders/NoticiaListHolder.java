package com.mibaldi.retorss.Holders;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mibaldi.retorss.Activities.NoticiaDetailActivity;
import com.mibaldi.retorss.Activities.NoticiaListActivity;
import com.mibaldi.retorss.Fragments.NoticiaDetailFragment;
import com.mibaldi.retorss.Models.Noticia;
import com.mibaldi.retorss.R;
import com.squareup.picasso.Picasso;

/**
 * Created by mikelbalducieldiaz on 15/5/16.
 */
public class NoticiaListHolder extends RecyclerView.ViewHolder {
    public ImageView image;
    public TextView title;
    public TextView description;
    public Context context;

    public NoticiaListHolder(View view) {
        super(view);
        context = view.getContext();
        title = (TextView) itemView.findViewById(R.id.newsTitle);
        description = (TextView) itemView.findViewById(R.id.newsDescription);
        image = (ImageView) itemView.findViewById(R.id.imageView);

    }

    public void bindItem(final Noticia noticia) {
        title.setText(noticia.getTitle());
        if (noticia.getDescription() != null){
            description.setText(Html.fromHtml(noticia.getDescription()));
        }
        else{
            description.setText("Sin Descripcion");
        }

        Picasso.with(context).load(noticia.getImage()).placeholder(ContextCompat.getDrawable(context,android.R.drawable.ic_dialog_email)).into(image);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NoticiaListActivity.ismTwoPane()) {
                    NoticiaDetailFragment fragment = NoticiaDetailFragment.newInstance(noticia);
                    ((NoticiaListActivity)context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.noticia_detail_container, fragment)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, NoticiaDetailActivity.class);
                    intent.putExtra(NoticiaDetailFragment.ARG_ITEM_ID, noticia);

                    context.startActivity(intent);
                }
            }
        });
    }
}
