package com.mibaldi.retorss.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mibaldi.retorss.Holders.NoticiaListHolder;
import com.mibaldi.retorss.Models.Noticia;
import com.mibaldi.retorss.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikelbalducieldiaz on 15/5/16.
 */
public class NoticiasRecyclerViewAdapter extends RecyclerView.Adapter<NoticiaListHolder> {
    private List<Noticia> listaNoticias;
    public NoticiasRecyclerViewAdapter(List<Noticia> noticias) {listaNoticias = noticias;}

    @Override
    public NoticiaListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.noticia_list_content,parent,false);

        return new NoticiaListHolder(view);
    }

    @Override
    public void onBindViewHolder(NoticiaListHolder holder, int position) {
        holder.bindItem(listaNoticias.get(position));
    }

    @Override
    public int getItemCount() {
        return listaNoticias.size();
    }

    public void setFilter(List<Noticia> filteredModelList) {
        listaNoticias = new ArrayList<>();
        listaNoticias.addAll(filteredModelList);
        notifyDataSetChanged();
    }
}
