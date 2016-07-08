package com.mibaldi.retorss2.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    // Sentencia SQL para crear la tabla de Noticias
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
    public static void close(SQLiteDatabase db){
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminamos la versión anterior de la tabla para simplificar este
        // ejemplo.
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        // Creamos la nueva versión de la tabla
        db.execSQL(sqlCreate);
    }

}
