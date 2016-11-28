package com.example.etienne.scoreboard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Étienne on 2016-11-03.
 */

public class DBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "scoreboard.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_JOUEUR = "CREATE TABLE " + Joueur.TABLE  + "("
                + Joueur.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Joueur.KEY_nom + " TEXT, "
                + Joueur.KEY_position + " TEXT, "
                + Joueur.KEY_numero + " INTEGER, "
                + Joueur.KEY_equipe + " INTEGER );";

        db.execSQL(CREATE_TABLE_JOUEUR);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Joueur.TABLE);

        // Create tables again
        onCreate(db);

    }

    public void insertJoueur(String nom) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues values;
        try {
            values = new ContentValues();
            values.put("nom", nom);
            db.insert(Joueur.TABLE, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {e.printStackTrace();}
        finally {
            db.endTransaction();
            db.close();
        }

    }

    public ArrayList<String> getAllJoueurs() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM " + Joueur.TABLE;
            Cursor cursor = db.rawQuery(selectQuery,null);
            if(cursor.getCount()>0) {
                while(cursor.moveToNext()) {
                    String nom = cursor.getString(cursor.getColumnIndex("nom"));
                    list.add(nom);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }

        return list;
    }
}
