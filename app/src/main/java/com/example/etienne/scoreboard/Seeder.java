package com.example.etienne.scoreboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Étienne on 2016-12-14.
 */

public class Seeder {

    /**
     * 1 = Local
     * 2 = Visiteur
     */

    List<String> listeJoueurs;
    List<String> listeOfficiels;
    public List<Joueur> listeJoueursVisiteur;
    public List<Joueur> listeJoueursLocal;
    public List<Officiel> listeOfficielsMatch;

    public void seed(DBHelper dbHelper){
        listeJoueurs = new ArrayList<String>();
        listeJoueursVisiteur = new ArrayList<Joueur>();
        listeJoueursLocal = new ArrayList<Joueur>();

        listeJoueurs.addAll(Arrays.asList("", "", "00", "1"));
        listeJoueurs.addAll(Arrays.asList("Max Pacioretty", "AG", "67", "1"));
        listeJoueurs.addAll(Arrays.asList("Tomas Plekanec", "C", "14", "1"));
        listeJoueurs.addAll(Arrays.asList("Alexander Radulov", "AD", "47", "1"));
        listeJoueurs.addAll(Arrays.asList("Shea Weber", "D", "6", "1"));
        listeJoueurs.addAll(Arrays.asList("Alexei Emelin", "D", "74", "1"));
        listeJoueurs.addAll(Arrays.asList("Carey Price", "G", "31", "1"));

        listeJoueurs.addAll(Arrays.asList("", "", "00", "0"));
        listeJoueurs.addAll(Arrays.asList("Rene Bourque", "AG", "17", "0"));
        listeJoueurs.addAll(Arrays.asList("Nathan MacKinnon", "C", "29", "0"));
        listeJoueurs.addAll(Arrays.asList("Jarome Iginla", "AD", "12", "0"));
        listeJoueurs.addAll(Arrays.asList("Tyson Barrie", "D", "4", "0"));
        listeJoueurs.addAll(Arrays.asList("Nikita Zadorov", "AD", "16", "0"));
        listeJoueurs.addAll(Arrays.asList("Calvin Pickard", "G", "31", "0"));

        for (int i = 0; i < listeJoueurs.size(); i = i + 4) {
            if (i >= 28) {
                Joueur joueurL = new Joueur();
                joueurL.nom = listeJoueurs.get(i);
                joueurL.position = listeJoueurs.get(i + 1);
                joueurL.numero = listeJoueurs.get(i + 2);
                joueurL.equipe = Integer.parseInt(listeJoueurs.get(i + 3));
                listeJoueursLocal.add(joueurL);
                dbHelper.insertJoueur(joueurL);
            } else {
                Joueur joueurV = new Joueur();
                joueurV.nom = listeJoueurs.get(i);
                joueurV.position = listeJoueurs.get(i + 1);
                joueurV.numero = listeJoueurs.get(i + 2);
                joueurV.equipe = Integer.parseInt(listeJoueurs.get(i + 3));
                listeJoueursVisiteur.add(joueurV);
                dbHelper.insertJoueur(joueurV);
            }
        }
    }

    public void seedArbitre(DBHelper dbHelper) {
        listeOfficiels = new ArrayList<String>();
        listeOfficielsMatch = new ArrayList<Officiel>();

        listeOfficiels.addAll(Arrays.asList("Arbitre", "Jon McIsaac"));
        listeOfficiels.addAll(Arrays.asList("Arbitre", "Wes McCauley"));
        listeOfficiels.addAll(Arrays.asList("Juge de lignes", "Shandor Alphonso"));
        listeOfficiels.addAll(Arrays.asList("Juge de lignes", "Steve Barton"));
        listeOfficiels.addAll(Arrays.asList("Juge de but", "Nicolas Bisson"));
        listeOfficiels.addAll(Arrays.asList("Juge de but", "Étienne Drolet"));
        listeOfficiels.addAll(Arrays.asList("Annonceur", "Michel Lacroix"));
        listeOfficiels.addAll(Arrays.asList("Chronométreur", "Marc Plante"));
        listeOfficiels.addAll(Arrays.asList("Marqueur", "Daniel Guay"));

        for (int i = 0; i < listeOfficiels.size(); i = i + 2) {
            Officiel officiel = new Officiel();
            officiel.type = listeOfficiels.get(i);
            officiel.nomComplet = listeOfficiels.get(i + 1);
            listeOfficielsMatch.add(officiel);
            dbHelper.insertOfficiel(officiel);
        }
    }
}
