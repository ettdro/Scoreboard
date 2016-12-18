package com.example.etienne.scoreboard;

/**
 * Classe pour les modèles des Officiels.
 *
 * @author Nicolas Bisson.
 */
public class Officiel {
    // Le nom de la table.
    public static final String TABLE = "Officiel";

    // Le nom des champs de la table.
    public static final String KEY_ID = "Id";
    public static final String KEY_type = "Type";
    public static final String KEY_nomComplet = "NomComplet";

    // Variables pour garder les données.
    public int officiel_ID;
    public String type;
    public String nomComplet;

    /**
     * Constructeur d'un officiel.
     */
    public void Officiel() {

    }
}
