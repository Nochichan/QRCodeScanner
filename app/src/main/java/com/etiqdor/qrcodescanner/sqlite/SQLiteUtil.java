package com.etiqdor.qrcodescanner.sqlite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import java.util.ArrayList;

public class SQLiteUtil {

    /**
     * Méthode qui permet d'obtenir le nombre d'enregistrement dans la table
     * @param helper Le helper permettant d'obtenir la base de donné
     * @return Le nombre d'enregistrement
     */
    public static int getLineCount(SQLiteHelper helper){
        SQLiteDatabase db = helper.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(true, "qr_code", new String[]{"id_qr_code", "latitude", "longitude", "altitude"}, null, null, null, null, "id_qr_code", null);
        //db.close();
        return cursor.getCount();
    }

    /**
     * Permet d'ajouter une ligne dans la base de données
     * Les arguments doivent être séparé par des virgules
     * @param helper Le helper qui permet d'ouvrir la base de donnée
     * @param arg1 Le premier argument de la requête (latitude)
     * @param arg2 Le deuxième argument de la requête (longitude)
     * @param arg3 Le troisième argument de la requête (altitude)
     * @param arg4 Le quatrième argument de la requête (url)
     */
    public static void insertInto(SQLiteHelper helper, String arg1, String arg2, String arg3, String arg4){
        int lineIndex = SQLiteUtil.getLineCount(helper)+1;
        String cmd = "INSERT INTO qr_code VALUES("+lineIndex+",\""+arg1+"\",\""+arg2+"\",\""+arg3+"\",\""+arg4+"\")";
        SQLiteDatabase db = helper.getReadableDatabase();
        db.execSQL(cmd);
        //db.close();
    }

    /**
     * Permet de récupérer sous forme de liste la totalité de la base de donné
     * @param helper Le helper permettant d'obtenir la base de donné
     * @return La liste de chaîne de caractère concatenné pour chaque enregistrement
     */
    public static ArrayList<String> getLocation(SQLiteHelper helper){
        SQLiteDatabase db = helper.getReadableDatabase();
        ArrayList<String> locations = new ArrayList<>();
        Cursor cursor = db.query(true, "qr_code", new String[]{"id_qr_code", "latitude", "longitude", "altitude", "url"}, null, null, null, null, "id_qr_code", null);
        String args;
        while (cursor.moveToNext()){
            args = "Latitude : " + cursor.getString(1);
            args += ", Longitude : " + cursor.getString(2);
            args += ", Altitude : " + cursor.getString(3);
            args += ", Url : " + cursor.getString(4);
            locations.add(args);
        }
        cursor.close();
        db.close();
        return locations;
    }

    /**
     * Permet de réinitaliser la base de donnée à son état d'origine
     * @param helper Le helper permettant d'obtenir la base de donné
     * @param context Le contexte de la fenêtre pour afficher le message Toast
     */
    public static void resetDatabase(SQLiteHelper helper, Context context){
        resetDatabase(helper);
        Toast.makeText(context, "Base de donné reinitialisée avec succès",Toast.LENGTH_SHORT).show();
    }

    /**
     * Permet de réinitaliser la base de donnée à son état d'origine
     * @param helper Le helper permettant d'obtenir la base de donné
     */
    public static void resetDatabase(SQLiteHelper helper){
        SQLiteDatabase db = helper.getReadableDatabase();
        db.execSQL("DROP TABLE qr_code;");
        String cmd = "CREATE TABLE qr_code(id_qr_code INTEGER PRIMARY KEY, latitude TEXT, longitude TEXT, altitude TEXT, url TEXT);";
        db.execSQL(cmd);
    }
}
