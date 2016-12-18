package com.example.etienne.scoreboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Classe pour gérer le menu principal.
 *
 * @author Nicolas Bisson.
 */
public class MenuPrincipal extends Activity {

    /**
     * Crée les éléments de la vue du menu principal.
     *
     * @param savedInstanceState : L'état de l'application enregistré.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);
        Button quitter = (Button) findViewById(R.id.button3);
        Button inscription = (Button) findViewById(R.id.button2);
        Button scoreboard = (Button) findViewById(R.id.button);

        quitter.setOnClickListener(new OnClickListener() {
            /**
             * Permet de quitter l'application.
             *
             * @param v : La vue dans laquelle on clique.
             */
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        inscription.setOnClickListener(new OnClickListener() {
            /**
             * Permet d'aller au menu d'inscription.
             *
             * @param v : La vue dans laquelle on clique.
             */
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuPrincipal.this, InscriptionActivity.class));
            }
        });

        scoreboard.setOnClickListener(new OnClickListener() {
            /**
             * Permet d'aller au menu du scoreboard.
             *
             * @param v : La vue dans laquelle on clique.
             */
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuPrincipal.this, MainActivity.class));
            }
        });
    }
}
