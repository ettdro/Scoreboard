package com.example.etienne.scoreboard;

/**
 * Created by Nicolas on 2016-11-22.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuPrincipal extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);
        Button quitter = (Button) findViewById(R.id.button3);
        Button inscription = (Button) findViewById(R.id.button2);
        Button scoreboard = (Button) findViewById(R.id.button);

        quitter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        inscription.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cool méthode qui envoie a la screen pour inscrire les joueurs des équipes.
            }
        });

        scoreboard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuPrincipal.this, MainActivity.class));
            }
        });
    }
}
