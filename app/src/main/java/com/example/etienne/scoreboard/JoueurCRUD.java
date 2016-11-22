package com.example.etienne.scoreboard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Étienne on 2016-11-03.
 */

public class JoueurCRUD {
    private DBHelper dbHelper;

    public JoueurCRUD(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(Joueur joueur) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Joueur.KEY_nom, joueur.nom);
        values.put(Joueur.KEY_position,joueur.position);
        values.put(Joueur.KEY_numero, joueur.numero);
        values.put(Joueur.KEY_equipe, joueur.equipe);

        // Inserting Row
        long joueur_Id = db.insert(Joueur.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) joueur_Id;
    }

    public void deleteAll() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Joueur.TABLE, null, null);
        db.close(); // Closing database connection
    }

    public void delete(int joueur_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Joueur.TABLE, Joueur.KEY_ID + "= ?", new String[] { String.valueOf(joueur_Id) });
        db.close(); // Closing database connection
    }

    public void update(Joueur joueur) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Joueur.KEY_nom, joueur.nom);
        values.put(Joueur.KEY_position,joueur.position);
        values.put(Joueur.KEY_numero, joueur.numero);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Joueur.TABLE, values, Joueur.KEY_ID + "= ?", new String[] { String.valueOf(joueur.joueur_ID) });
        db.close(); // Closing database connection
    }

    public ArrayList<String> getJoueurListe(int equipe) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT  " +
                    Joueur.KEY_nom + "," +
                    Joueur.KEY_position + "," +
                    Joueur.KEY_numero + "," +
                    Joueur.KEY_equipe +
                    " FROM " + Joueur.TABLE +
                    " WHERE " + Joueur.KEY_equipe + " = " + equipe + ";";

        //Joueur joueur = new Joueur();
        ArrayList<String> joueurListe = new ArrayList<String>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                joueurListe.add(cursor.getString(cursor.getColumnIndex(Joueur.KEY_nom)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return joueurListe;

    }

    public Joueur getJoueurParId(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Joueur.KEY_ID + "," +
                Joueur.KEY_nom + "," +
                Joueur.KEY_position + "," +
                Joueur.KEY_numero +
                " FROM " + Joueur.TABLE
                + " WHERE " +
                Joueur.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Joueur joueur = new Joueur();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                joueur.joueur_ID = cursor.getInt(cursor.getColumnIndex(Joueur.KEY_ID));
                joueur.nom = cursor.getString(cursor.getColumnIndex(Joueur.KEY_nom));
                joueur.position  = cursor.getString(cursor.getColumnIndex(Joueur.KEY_position));
                joueur.numero = cursor.getString(cursor.getColumnIndex(Joueur.KEY_numero));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return joueur;
    }
}
