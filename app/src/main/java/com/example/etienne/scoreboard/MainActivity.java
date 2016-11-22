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

    private int scoreV = 0;
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

    private Spinner spinnerPenaltyV;
    private Spinner spinnerPenaltyL;

    private int tirsV = 0;
    static TextView scoreVLabel;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        JoueurCRUD db = new JoueurCRUD(this);
        Joueur joueurV = new Joueur();
        Joueur joueurL = new Joueur();
        this.listeJoueursVisiteur = new ArrayList<Joueur>();
        this.listeJoueursLocal = new ArrayList<Joueur>();

        Popup popup = new Popup();
        this.scoreV = popup.getScoreV();

        scoreVLabel = (TextView) findViewById(R.id.scoreVisiteur);

        joueurV.nom = this.nomV;
        joueurV.numero = this.numeroV;
        joueurV.position = this.positionV;
        joueurV.equipe = this.equipeV;

        joueurL.nom = this.nomL;
        joueurL.numero = this.numeroL;
        joueurL.position = this.positionL;
        joueurL.equipe = this.equipeL;


        final TextView tirsVLabel = (TextView) findViewById(R.id.tirsVisiteur);



        db.deleteAll();
        for (int i = 0; i < 23; i++) {
            db.insert(joueurV);
            this.listeJoueursVisiteur.add(joueurV);
        }

        for (int i = 0; i < 23; i++) {
            db.insert(joueurL);
            this.listeJoueursLocal.add(joueurL);
        }

        setContentView(R.layout.layout);

        final Button scoreVUpButton = (Button) findViewById(R.id.buttonScoreUpV);
        scoreVUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Popup.class));
            }
        });

        final Button tirsVUpButton = (Button) findViewById(R.id.buttonTirsUpV);
        tirsVUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tirsV++;

                tirsVLabel.setText(String.valueOf(tirsV));
            }
        });

        this.spinnerPenaltyV = (Spinner) findViewById(R.id.spinnerPenaltyV);
        this.spinnerPenaltyL = (Spinner) findViewById(R.id.spinnerPenaltyL);

        loadSpinnerData();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * Function to load the spinner data from SQLite database
     */
    private void loadSpinnerData() {
        JoueurCRUD db = new JoueurCRUD(getApplicationContext());

        List<String> joueursV = db.getJoueurListe(0);
        List<String> joueursL = db.getJoueurListe(1);

        ArrayAdapter<String> dataAdapterV = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, joueursV);

        ArrayAdapter<String> dataAdapterL = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, joueursL);

        dataAdapterV.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterL.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.spinnerPenaltyV.setAdapter(dataAdapterV);
        this.spinnerPenaltyL.setAdapter(dataAdapterL);
    }

    public static void scoreUPVisitor(int score) {
        scoreVLabel.setText(String.valueOf(score));
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
