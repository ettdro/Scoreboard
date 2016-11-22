package com.example.etienne.scoreboard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
}
