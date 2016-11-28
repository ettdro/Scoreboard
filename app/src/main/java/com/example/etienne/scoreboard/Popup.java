package com.example.etienne.scoreboard;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Étienne on 2016-11-01.
 */
public class Popup extends Activity {

    private Spinner spinnerGoal;
    private Spinner spinnerAssist1;
    private Spinner spinnerAssist2;
    int scoreV = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popwindow);

        JoueurCRUD db = new JoueurCRUD(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        Button confirmer = (Button) findViewById(R.id.buttonConfirmerPopup);
        confirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add the players to DB.
                scoreV++;
                finish();
            }
        });

        Button annuler = (Button) findViewById(R.id.buttonAnnulerPopup);
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.spinnerGoal = (Spinner) findViewById(R.id.spinnerGoal);
        this.spinnerAssist1 = (Spinner) findViewById(R.id.spinnerPasseur1Popup);
        this.spinnerAssist2 = (Spinner) findViewById(R.id.spinnerPasseur2Popup);

        loadSpinnerData();
    }

    /**
     * Function to load the spinner data from SQLite database
     * */
    private void loadSpinnerData() {
        JoueurCRUD db = new JoueurCRUD(getApplicationContext());

        List<String> joueurs = db.getJoueurListe(0);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, joueurs);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.spinnerGoal.setAdapter(dataAdapter);
        this.spinnerAssist1.setAdapter(dataAdapter);
        this.spinnerAssist2.setAdapter(dataAdapter);
    }

    public int getScoreV() {
        return this.scoreV;
    }
}
