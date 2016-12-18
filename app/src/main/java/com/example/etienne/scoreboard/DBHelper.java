package com.example.etienne.scoreboard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Classe pour créer la base de données et son contenu.
 * Permet de gérer la base de données.
 *
 * @author Étienne Drolet et Nicolas Bisson.
 */
public class DBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;

    // Nom de la base de données.
    private static final String DATABASE_NAME = "scoreboard.db";

    /**
     * Crée la base de données dans le contexte.
     *
     * @param context : Le contexte où créer la base de données.
     */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Crée les tables et leurs colonnes.
     *
     * @param db : La base de données dans laquelle créer les tables.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Permet de créer les tables dans la base de données.
        String CREATE_TABLE_JOUEUR = "CREATE TABLE " + Joueur.TABLE  + "("
                + Joueur.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Joueur.KEY_nom + " TEXT, "
                + Joueur.KEY_position + " TEXT, "
                + Joueur.KEY_numero + " INTEGER, "
                + Joueur.KEY_equipe + " INTEGER );";

        String CREATE_TABLE_OFFICIEL = "CREATE TABLE " + Officiel.TABLE + "("
                + Officiel.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Officiel.KEY_type + " TEXT,"
                + Officiel.KEY_nomComplet + " TEXT );";

        db.execSQL(CREATE_TABLE_JOUEUR);
        //db.execSQL(CREATE_TABLE_OFFICIEL);

    }

    /**
     * Mets à jour la base de données.
     *
     * @param db         : La base de données à mettre à jour.
     * @param oldVersion : Ancienne version de la base de données.
     * @param newVersion : Nouvelle version de la base de données.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Détruit les tables et leurs données si elles existaient.
        db.execSQL("DROP TABLE IF EXISTS " + Joueur.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Officiel.TABLE);

        // Recrée les tables.
        onCreate(db);

    }

    /**
     * Détruit les tables et leur contenu de la base de données.
     */
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Joueur.TABLE, null, null);
        //db.delete(Officiel.TABLE, null, null);
        db.close();
    }

    /**
     * Insert un joueur dans la base de données.
     *
     * @param joueur : Le joueur à insérer dans la base de données.
     */
    public void insertJoueur(Joueur joueur) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues values;
        try {
            values = new ContentValues();
            values.put("nom", joueur.nom);
            values.put("position", joueur.position);
            values.put("numero", joueur.numero);
            values.put("equipe", joueur.equipe);
            db.insert(Joueur.TABLE, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {e.printStackTrace();}
        finally {
            db.endTransaction();
            db.close();
        }
    }

    /**
     * Insert un officiel dans la base de données.
     *
     * @param officiel : L'officiel à insérer dans la base de données.
     */
    public void insertOfficiel(Officiel officiel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues values;
        try {
            values = new ContentValues();
            values.put("type", officiel.type);
            values.put("nomComplet", officiel.nomComplet);
            db.insert(Officiel.TABLE, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    /**
     * Va chercher tous les joueurs visiteurs.
     *
     * @return la liste de tous les joueurs visiteurs.
     */
    public ArrayList<String> getJoueursVisiteurs() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM " + Joueur.TABLE + " WHERE equipe = " + 1;
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

    /**
     * Va chercher tous les joueurs locaux.
     *
     * @return la liste de tous les joueurs locaux.
     */
    public ArrayList<String> getJoueursLocal() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM " + Joueur.TABLE + " WHERE equipe = " + 0;
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

    /**
     * Va chercher tous les officiels de la partie.
     *
     * @return la liste de tous les officiels de la partie.
     */
    public ArrayList<String> getAllOfficiels() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM " + Officiel.TABLE;
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
