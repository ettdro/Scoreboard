package com.example.etienne.scoreboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Étienne on 2016-12-14.
 */

public class SeederJoueurs {

    List<String> listeJoueurs;
    public List<Joueur> listeJoueursVisiteur;
    public List<Joueur> listeJoueursLocal;

    public void seed(DBHelper dbHelper){
        listeJoueurs = new ArrayList<String>();
        listeJoueursVisiteur = new ArrayList<Joueur>();
        listeJoueursLocal = new ArrayList<Joueur>();

        listeJoueurs.addAll(Arrays.asList("Paul Byron", "AG", "41", "1"));
        listeJoueurs.addAll(Arrays.asList("Daniel Carr", "AG", "43", "1"));
        listeJoueurs.addAll(Arrays.asList("Phillip Danault", "C", "24", "1"));
        listeJoueurs.addAll(Arrays.asList("David Desharnais", "C", "51", "1"));
        listeJoueurs.addAll(Arrays.asList("Brian Flynn", "AD", "32", "1"));
        listeJoueurs.addAll(Arrays.asList("Alex Galchenyuk", "C", "27", "1"));
        listeJoueurs.addAll(Arrays.asList("Brendan Gallagher", "AD", "11", "1"));
        listeJoueurs.addAll(Arrays.asList("Arturri Lekhonen", "AG", "62", "1"));
        listeJoueurs.addAll(Arrays.asList("Michael McCarron", "C", "34", "1"));
        listeJoueurs.addAll(Arrays.asList("Torrey Mitchell", "C", "17", "1"));
        listeJoueurs.addAll(Arrays.asList("Max Pacioretty", "AD", "67", "1"));
        listeJoueurs.addAll(Arrays.asList("Tomas Plekanec", "C", "14", "1"));
        listeJoueurs.addAll(Arrays.asList("Alexander Radulov", "AD", "47", "1"));
        listeJoueurs.addAll(Arrays.asList("Andrew Shaw", "AD", "65", "1"));

        for (int i = 0; i < listeJoueurs.size(); i=i+4) {
            Joueur joueurV = new Joueur();
            joueurV.nom = listeJoueurs.get(i);
            joueurV.position = listeJoueurs.get(i+1);
            joueurV.numero = listeJoueurs.get(i+2);
            joueurV.equipe = Integer.parseInt(listeJoueurs.get(i+3));
            listeJoueursVisiteur.add(joueurV);
            listeJoueursLocal.add(joueurV); //Seulement pour que le code fonctionne.
            dbHelper.insertJoueur(joueurV);
        }
    }
}
