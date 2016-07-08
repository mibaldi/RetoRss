package com.mibaldi.retorss2.Models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import com.mibaldi.retorss2.DB.NoticiasSQLiteHelper;
import com.mibaldi.retorss2.Utils.DateFormatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mikelbalducieldiaz on 15/5/16.
 */
public class Noticia implements Parcelable {
    private String title;
    private String description = "Sin descripcion";
    private Date pubDate;
    private String url;
    private String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeLong(this.pubDate != null ? this.pubDate.getTime() : -1);
        dest.writeString(this.url);
        dest.writeString(this.image);
    }

    public Noticia() {
    }

    protected Noticia(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        long tmpPubDate = in.readLong();
        this.pubDate = tmpPubDate == -1 ? null : new Date(tmpPubDate);
        this.url = in.readString();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<Noticia> CREATOR = new Parcelable.Creator<Noticia>() {
        @Override
        public Noticia createFromParcel(Parcel source) {
            return new Noticia(source);
        }

        @Override
        public Noticia[] newArray(int size) {
            return new Noticia[size];
        }
    };
    public static void insertNoticia(SQLiteDatabase db, Noticia news) {

        ContentValues nuevaNoticia = new ContentValues();
        nuevaNoticia.put(NoticiasSQLiteHelper.KEY_TITLE, news.getTitle());
        nuevaNoticia.put(NoticiasSQLiteHelper.KEY_DESCRIPTION, news.getDescription());
        nuevaNoticia.put(NoticiasSQLiteHelper.KEY_DATE, DateFormatter.convertDateToString(news.getPubDate()));
        nuevaNoticia.put(NoticiasSQLiteHelper.KEY_PHOTO, news.getImage());
        nuevaNoticia.put(NoticiasSQLiteHelper.KEY_URL,news.getUrl());
        db.insert(NoticiasSQLiteHelper.DATABASE_TABLE, null, nuevaNoticia);
    }
    public static boolean CheckExist(SQLiteDatabase db,String url) {
        url = DatabaseUtils.sqlEscapeString(url);

        String Query = "Select * from " + NoticiasSQLiteHelper.DATABASE_TABLE + " where " + NoticiasSQLiteHelper.KEY_URL + " = "+url;
        Cursor cursor = db.rawQuery(Query,null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    public static List<Noticia> verMisNoticias(SQLiteDatabase db) {
        List<Noticia> noticias = new ArrayList<>();
        String[] campos = new String[]{NoticiasSQLiteHelper.KEY_TITLE, NoticiasSQLiteHelper.KEY_DESCRIPTION,NoticiasSQLiteHelper.KEY_DATE, NoticiasSQLiteHelper.KEY_PHOTO,NoticiasSQLiteHelper.KEY_URL};
        Cursor c = db.query(NoticiasSQLiteHelper.DATABASE_TABLE, campos, null, null, null, null,
                null);
        if (c.moveToFirst()) {
            // Recorremos el cursor hasta que no haya más registros
            do {
                String title = c.getString(0);
                String description = c.getString(1);
                String pubDate = c.getString(2);
                String photo = c.getString(3);
                String url = c.getString(4);
                Noticia noticia = new Noticia();
                noticia.setTitle(title);
                noticia.setDescription(description);
                noticia.setPubDate(DateFormatter.convertStringToDate(pubDate));
                noticia.setImage(photo);
                noticia.setUrl(url);
                noticias.add(noticia);

            } while (c.moveToNext());
        }
        c.close();
        return noticias;
    }
}
