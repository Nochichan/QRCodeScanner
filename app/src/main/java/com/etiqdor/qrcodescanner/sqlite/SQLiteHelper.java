package com.etiqdor.qrcodescanner.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    static String DB_NAME = "QRCode_Scanner_ETIQDOR.db";
    static int DB_VERSION = 1;

    /**
     * Constructeur de SQLiteHelper
     * @param context Le context de l'application
     */
    public SQLiteHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Méthode qui s'execute une seul fois à la création de la base de données
     * @param db La base de données
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateDatatableQuiz = "CREATE TABLE qr_code(id_qr_code INTEGER PRIMARY KEY, latitude TEXT, longitude TEXT, altitude TEXT, url TEXT);";
        db.execSQL(sqlCreateDatatableQuiz);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
