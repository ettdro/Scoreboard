package com.example.etienne.scoreboard;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Classe pour gérer le menu d'inscription.
 *
 * @author Nicolas Bisson.
 */

public class InscriptionActivity extends Activity {
    /**
     * Crée les éléments de la vue du menu d'inscription.
     *
     * @param savedInstanceState : L'état de l'application enregistré.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription);
        Button boutonRetour = (Button) findViewById(R.id.boutonRetour);

        boutonRetour.setOnClickListener(new View.OnClickListener() {
            /**
             * Permet de revenir au menu principal.
             *
             * @param v : La vue dans laquelle on clique.
             */
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final ArrayList<String> numero = new ArrayList<String>() ;
        for(int i = 1; i < 100; i++) {
            String numeroStr = Integer.toString(i);
            numero.add(numeroStr);
        }

        // Spinner des numeros et positions

        final ArrayList<String> position = new ArrayList<String>() ;
        position.add("AG");
        position.add("C");
        position.add("AD");
        position.add("G");

        Spinner numeroLoc = (Spinner)findViewById(R.id.NumeroJoueurLoc);
        Spinner numeroVis = (Spinner)findViewById(R.id.NumeroJoueurVis);
        Spinner positionLoc = (Spinner)findViewById(R.id.PositionJoueurLoc);
        Spinner positionVis = (Spinner)findViewById(R.id.PositionJoueurVis);

        ArrayAdapter<String> adapternumeroLoc = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, numero);
        adapternumeroLoc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numeroLoc.setAdapter(adapternumeroLoc);

        ArrayAdapter<String> adapternumeroVis = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, numero);
        adapternumeroVis.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numeroVis.setAdapter(adapternumeroVis);

        ArrayAdapter<String> adapterpositionLoc = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, position);
        adapterpositionLoc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        positionLoc.setAdapter(adapterpositionLoc);

        ArrayAdapter<String> adapterpositionVis = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, position);
        adapterpositionVis.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        positionVis.setAdapter(adapterpositionVis);









    }
}
