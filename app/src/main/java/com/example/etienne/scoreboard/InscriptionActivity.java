package com.example.etienne.scoreboard;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    }
}
