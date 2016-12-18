package com.example.etienne.scoreboard;

/**
 * Classe pour les modèles des joueurs.
 *
 * @author Étienne Drolet
 */
public class Joueur {
    // Le nom de la table.
    public static final String TABLE = "Joueur";

    // Le nom des champs de la table.
    public static final String KEY_ID = "Id";
    public static final String KEY_nom = "Nom";
    public static final String KEY_position = "Position";
    public static final String KEY_numero = "Numero";
    public static final String KEY_equipe = "Equipe";

    // Variables pour garder les données.
    public int joueur_ID;
    public String nom;
    public String position;
    public String numero;
    public int equipe;

    /**
     * Constructeur d'un joueur.
     */
    public void Joueur(){

    }
}
