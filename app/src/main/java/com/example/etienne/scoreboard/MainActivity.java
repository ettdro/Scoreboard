package com.example.etienne.scoreboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Joueur> listeJoueursVisiteur;
    private List<Joueur> listeJoueursLocal;

    private String nomV = "Alex Galchenyuk";
    private String numeroV = "27";
    private String positionV = "C";
    private int equipeV = 0;

    private String nomL = "Max Pacioretty";
    private String numeroL = "67";
    private String positionL = "AG";
    private int equipeL = 1;

    //private Spinner spinnerPenaltyV;
    //private Spinner spinnerPenaltyL;

    private int tirsVisiteurs = 0;
    private int tirsLocaux = 0;


    private TextView scoreVLabel;
    private TextView scoreLLabel;

    private int scoreL = 0;
    private int scoreV = 0;

    private TextView tirsLabelV;
    private TextView tirsLabelL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        DBHelper dbHelper = new DBHelper(this);

        dbHelper.insertJoueur(nomV);
        dbHelper.insertJoueur(nomL);

        ArrayList<String> listeJoueurs = dbHelper.getAllJoueurs();
        Spinner spinnerPenaltyV = (Spinner)findViewById(R.id.spinnerPenaltyV);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.main_layout,R.id.txtView, listeJoueurs);
        spinnerPenaltyV.setAdapter(adapter);

        //JoueurCRUD db = new JoueurCRUD(this);


        //Joueur joueurV = new Joueur();
        /*
        Joueur joueurL = new Joueur();
        this.listeJoueursVisiteur = new ArrayList<Joueur>();
        this.listeJoueursLocal = new ArrayList<Joueur>();

        Popup popup = new Popup();
        this.scoreV = popup.getScoreV();
        */


        /*
        joueurV.nom = this.nomV;
        joueurV.numero = this.numeroV;
        joueurV.position = this.positionV;
        joueurV.equipe = this.equipeV;

        db.insert(joueurV);
        */



        /*
        joueurL.nom = this.nomL;
        joueurL.numero = this.numeroL;
        joueurL.position = this.positionL;
        joueurL.equipe = this.equipeL;
        */


        /*
        db.deleteAll();
        for (int i = 0; i < 23; i++) {
            db.insert(joueurV);
            this.listeJoueursVisiteur.add(joueurV);
        }

        for (int i = 0; i < 23; i++) {
            db.insert(joueurL);
            this.listeJoueursLocal.add(joueurL);
        }
        */

        // Score visiteurs
        scoreVLabel = (TextView) findViewById(R.id.scoreVisiteur);
        final Button scoreVUpButton = (Button) findViewById(R.id.buttonScoreUpV);
        scoreVUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                scoreV++;
                scoreVLabel.setText(String.valueOf(scoreV));
                //startActivity(new Intent(MainActivity.this, Popup.class));
            }
        });

        final Button scoreVDownButton = (Button) findViewById(R.id.buttonScoreDownV);
        scoreVDownButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (scoreV > 0) {
                    scoreV--;
                    scoreVLabel.setText(String.valueOf(scoreV));
                } else {
                    scoreVLabel.setText(String.valueOf(0));
                }
                //startActivity(new Intent(MainActivity.this, Popup.class));
            }
        });

        // Score locaux
        scoreLLabel = (TextView) findViewById(R.id.scoreLocal);
        final Button scoreLUpButton = (Button) findViewById(R.id.buttonScoreUpLocal);
        scoreLUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                scoreL++;
                scoreLLabel.setText(String.valueOf(scoreL));
                //startActivity(new Intent(MainActivity.this, Popup.class));
            }
        });

        final Button scoreLDownButton = (Button) findViewById(R.id.buttonScoreDownLocal);
        scoreLDownButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (scoreL > 0) {
                    scoreL--;
                    scoreLLabel.setText(String.valueOf(scoreL));
                } else {
                    scoreLLabel.setText(String.valueOf(0));
                }
                //startActivity(new Intent(MainActivity.this, Popup.class));
            }
        });

        // Tirs visiteurs
        tirsLabelV = (TextView) findViewById(R.id.tirsVisiteur);
        final Button tirsVUpButton = (Button) findViewById(R.id.buttonTirsUpV);
        tirsVUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tirsVisiteurs++;
                tirsLabelV.setText(String.valueOf(tirsVisiteurs));
            }
        });

        final Button tirsVDownButton = (Button) findViewById(R.id.buttonTirsDownV);
        tirsVDownButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tirsVisiteurs > 0) {
                    tirsVisiteurs--;
                    tirsLabelV.setText(String.valueOf(tirsVisiteurs));
                } else {
                    tirsLabelV.setText(String.valueOf(0));
                }
            }
        });

        // Tirs locaux
        tirsLabelL = (TextView) findViewById(R.id.tirsLocal);
        final Button tirsLUpButton = (Button) findViewById(R.id.buttonTirsUpL);
        tirsLUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tirsLocaux++;
                tirsLabelL.setText(String.valueOf(tirsLocaux));
            }
        });

        final Button tirsLDownButton = (Button) findViewById(R.id.buttonTirsDownL);
        tirsLDownButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tirsLocaux > 0) {
                    tirsLocaux--;
                    tirsLabelL.setText(String.valueOf(tirsLocaux));
                } else {
                    tirsLabelL.setText(String.valueOf(0));
                }
            }
        });
    }

    /**
     * Function to load the spinner data from SQLite database
     */
    private void loadSpinnerData() {
        JoueurCRUD db = new JoueurCRUD(getApplicationContext());

        List<String> joueursV = db.getJoueurListe(0);
        List<String> joueursL = db.getJoueurListe(1);

        ArrayAdapter<String> dataAdapterV = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, joueursV);

        ArrayAdapter<String> dataAdapterL = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, joueursL);

        dataAdapterV.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterL.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //this.spinnerPenaltyV.setAdapter(dataAdapterV);
        //this.spinnerPenaltyL.setAdapter(dataAdapterL);
    }
}
