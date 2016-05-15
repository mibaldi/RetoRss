package com.mibaldi.retorss.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mibaldi.retorss.Models.Noticia;
import com.mibaldi.retorss.Utils.DateFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikelbalducieldiaz on 15/5/16.
 */
public class NoticiasSQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "newsDatabase.db";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_TABLE = "news";

    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_DATE = "pubdate";
    public static final String KEY_PHOTO = "photo";
    public static final String KEY_URL = "url";

    // Sentencia SQL para crear la tabla de Jugadores
    String sqlCreate = "CREATE TABLE " + DATABASE_TABLE + "("
            + "_id " + "INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_TITLE + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_DATE + " TEXT, "
            + KEY_PHOTO + " TEXT, "
            + KEY_URL + " TEXT)";

    public NoticiasSQLiteHelper(Context contexto, String nombre,
                            SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminamos la versión anterior de la tabla para simplificar este
        // ejemplo.
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        // Creamos la nueva versión de la tabla
        db.execSQL(sqlCreate);
    }
    public static void insertNoticia(SQLiteDatabase db,Noticia news) {
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
