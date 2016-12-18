package com.example.etienne.scoreboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe pour peupler la base de données.
 *
 * @author Étienne Drolet et Nicolas Bisson.
 */
public class Seeder {

    List<String> listeJoueurs;
    List<String> listeOfficiels;
    public List<Joueur> listeJoueursVisiteur;
    public List<Joueur> listeJoueursLocal;
    public List<Officiel> listeOfficielsMatch;

    /**
     * Peuple la base de données dans la table des joueurs.
     *
     * 0 = Local
     * 1 = Visiteur
     *
     * @param dbHelper : L'assistant de la base de données.
     */
    public void seed(DBHelper dbHelper){
        listeJoueurs = new ArrayList<String>();
        listeJoueursVisiteur = new ArrayList<Joueur>();
        listeJoueursLocal = new ArrayList<Joueur>();

        // Joueurs locaux.
        listeJoueurs.addAll(Arrays.asList("", "", "00", "0"));
        listeJoueurs.addAll(Arrays.asList("Max Pacioretty", "AG", "67", "0"));
        listeJoueurs.addAll(Arrays.asList("Tomas Plekanec", "C", "14", "0"));
        listeJoueurs.addAll(Arrays.asList("Alexander Radulov", "AD", "47", "0"));
        listeJoueurs.addAll(Arrays.asList("Shea Weber", "D", "6", "0"));
        listeJoueurs.addAll(Arrays.asList("Alexei Emelin", "D", "74", "0"));
        listeJoueurs.addAll(Arrays.asList("Carey Price", "G", "31", "0"));

        // Joueurs visiteurs.
        listeJoueurs.addAll(Arrays.asList("", "", "00", "1"));
        listeJoueurs.addAll(Arrays.asList("Rene Bourque", "AG", "17", "1"));
        listeJoueurs.addAll(Arrays.asList("Nathan MacKinnon", "C", "29", "1"));
        listeJoueurs.addAll(Arrays.asList("Jarome Iginla", "AD", "12", "1"));
        listeJoueurs.addAll(Arrays.asList("Tyson Barrie", "D", "4", "1"));
        listeJoueurs.addAll(Arrays.asList("Nikita Zadorov", "AD", "16", "1"));
        listeJoueurs.addAll(Arrays.asList("Calvin Pickard", "G", "31", "1"));

        // Insère les joueurs et leurs informations tour à tour.
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

    /**
     * Peuple la base de données dans la table des officiels.
     *
     * @param dbHelper : L'assistant de la base de données.
     */
    public void seedArbitre(DBHelper dbHelper) {
        listeOfficiels = new ArrayList<String>();
        listeOfficielsMatch = new ArrayList<Officiel>();

        // Officiels de la partie.
        listeOfficiels.addAll(Arrays.asList("Arbitre", "Jon McIsaac"));
        listeOfficiels.addAll(Arrays.asList("Arbitre", "Wes McCauley"));
        listeOfficiels.addAll(Arrays.asList("Juge de lignes", "Shandor Alphonso"));
        listeOfficiels.addAll(Arrays.asList("Juge de lignes", "Steve Barton"));
        listeOfficiels.addAll(Arrays.asList("Juge de but", "Nicolas Bisson"));
        listeOfficiels.addAll(Arrays.asList("Juge de but", "Étienne Drolet"));
        listeOfficiels.addAll(Arrays.asList("Annonceur", "Michel Lacroix"));
        listeOfficiels.addAll(Arrays.asList("Chronométreur", "Marc Plante"));
        listeOfficiels.addAll(Arrays.asList("Marqueur", "Daniel Guay"));

        // Insère les officiels et leur informations tour à tour.
        for (int i = 0; i < listeOfficiels.size(); i = i + 2) {
            Officiel officiel = new Officiel();
            officiel.type = listeOfficiels.get(i);
            officiel.nomComplet = listeOfficiels.get(i + 1);
            listeOfficielsMatch.add(officiel);
            dbHelper.insertOfficiel(officiel);
        }
    }
}
