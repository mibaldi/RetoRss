package com.mibaldi.retorss2.Utils;

import com.mibaldi.retorss2.Models.Noticia;

import java.util.Comparator;

/**
 * Created by mikelbalducieldiaz on 15/5/16.
 */
public class CustomComparator implements Comparator<Noticia> {
    @Override
    public int compare(Noticia noticia1, Noticia noticia2) {
        return noticia2.getPubDate().compareTo(noticia1.getPubDate());
    }
}
