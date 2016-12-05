package com.example.etienne.scoreboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Debug;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

    TextView periodeText;
    int periode = 1;

    // TIMER
    private boolean isPaused;
    private long time;
    CountDownTimer timer;
    private long milliLeft;
    private long min;
    private long sec;
    TextView timerText;
    private boolean timerHasStarted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        DBHelper dbHelper = new DBHelper(this);

        dbHelper.insertJoueur(nomV);
        dbHelper.insertJoueur(nomL);

        //TextView txtView = (TextView) findViewById(R.id.txtView);
        this.timerText = (TextView) findViewById(R.id.timerText);
        this.periodeText = (TextView) findViewById(R.id.periodeText);

        final Button buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isPaused) {
                    isPaused = false;
                    timerResume();
                    timer.start();
                } else {
                    if (timerHasStarted) {
                    } else {
                        if (time == 0) {
                        } else {
                            timerHasStarted = true;
                            timerStart(time * 60000);
                            timer.start();
                        }
                    }
                }
            }
        });

        final Button buttonPause = (Button) findViewById(R.id.buttonPause);
        buttonPause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                timerPause();
            }
        });

        final Button buttonReset = (Button) findViewById(R.id.buttonReset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (timerHasStarted) {
                    timerHasStarted = false;
                    isPaused = false;
                    time = 0;
                    timer.cancel();
                    timerText.setText(String.valueOf("00:00"));
                }
            }
        });

        final Button buttonSetTimer = (Button) findViewById(R.id.buttonSetTimer);
        buttonSetTimer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                InputFilter[] FilterArray = new InputFilter[1];
                FilterArray[0] = new InputFilter.LengthFilter(2);
                alert.setTitle("Entrez le temps");
                final EditText input = new EditText(MainActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                input.setFilters(FilterArray);
                alert.setView(input);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (!(input.getText().toString().isEmpty())) {
                            time = Long.valueOf(input.getText().toString());
                            if (time < 10) {
                                timerText.setText("0" + String.valueOf(time) + ":00");
                            } else {
                                timerText.setText(String.valueOf(time + ":00"));
                            }
                        }
                    }
                });
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Put actions for CANCEL button here, or leave in blank
                    }
                });
                alert.show();
            }
        });

        final ArrayList<String> listeJoueursVString = dbHelper.getJoueursVisiteurs();
        final ArrayList<String> listeJoueursLString = dbHelper.getJoueursLocal();
        Spinner spinnerPenaltyV = (Spinner)findViewById(R.id.spinnerPenaltyV);
        Spinner spinnerPenaltyL = (Spinner)findViewById(R.id.spinnerPenaltyL);

        ArrayAdapter<String> adapterV = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listeJoueursVString);
        adapterV.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPenaltyV.setAdapter(adapterV);

        ArrayAdapter<String> adapterL = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listeJoueursLString);
        adapterL.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPenaltyL.setAdapter(adapterL);

        JoueurCRUD db = new JoueurCRUD(this);


        Joueur joueurV = new Joueur();

        Joueur joueurL = new Joueur();
        this.listeJoueursVisiteur = new ArrayList<Joueur>();
        this.listeJoueursLocal = new ArrayList<Joueur>();

        joueurV.nom = this.nomV;
        joueurV.numero = this.numeroV;
        joueurV.position = this.positionV;
        joueurV.equipe = this.equipeV;

        db.insert(joueurV);

        joueurL.nom = this.nomL;
        joueurL.numero = this.numeroL;
        joueurL.position = this.positionL;
        joueurL.equipe = this.equipeL;

        db.deleteAll();
        for (int i = 0; i < 23; i++) {
            db.insert(joueurV);
            this.listeJoueursVisiteur.add(joueurV);
        }

        for (int i = 0; i < 23; i++) {
            db.insert(joueurL);
            this.listeJoueursLocal.add(joueurL);
        }

        // Score visiteurs
        scoreVLabel = (TextView) findViewById(R.id.scoreVisiteur);
        final Button scoreVUpButton = (Button) findViewById(R.id.buttonScoreUpV);
        scoreVUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                InputFilter[] FilterArray = new InputFilter[1];
                FilterArray[0] = new InputFilter.LengthFilter(2);
                alert.setTitle("BUT!");
                final Spinner spinnerBut = new Spinner(MainActivity.this);
                ArrayAdapter<String> adapterV = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, listeJoueursVString);
                adapterV.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerBut.setAdapter(adapterV);
                alert.setView(spinnerBut);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                        InputFilter[] FilterArray = new InputFilter[1];
                        FilterArray[0] = new InputFilter.LengthFilter(2);
                        alert.setTitle("PASSE 1");
                        final Spinner spinnerPasse1 = new Spinner(MainActivity.this);
                        ArrayAdapter<String> adapterV = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, listeJoueursVString);
                        adapterV.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerPasse1.setAdapter(adapterV);
                        alert.setView(spinnerPasse1);
                        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                                InputFilter[] FilterArray = new InputFilter[1];
                                FilterArray[0] = new InputFilter.LengthFilter(2);
                                alert.setTitle("PASSE 2");
                                final Spinner spinnerPasse2 = new Spinner(MainActivity.this);
                                ArrayAdapter<String> adapterV = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, listeJoueursVString);
                                adapterV.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerPasse2.setAdapter(adapterV);
                                alert.setView(spinnerPasse2);
                                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        scoreV++;
                                        scoreVLabel.setText(String.valueOf(scoreV));
                                    }
                                });
                                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                    }
                                });
                                alert.show();
                            }
                        });
                        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        });
                        alert.show();
                    }
                });
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
                alert.show();
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

    public void timerStart(long timeLengthMilli) {
        timer = new CountDownTimer(timeLengthMilli, 1000) {

            @Override
            public void onTick(long milliTillFinish) {
                milliLeft = milliTillFinish;
                min = (milliTillFinish / (1000 * 60));
                sec = ((milliTillFinish / 1000) - min * 60);
                if (min < 10) {
                    timerText.setText("0" + Long.toString(min) + ":" + "0" + Long.toString(sec));
                } else {
                    timerText.setText(Long.toString(min) + ":" + "0" + Long.toString(sec));
                }
                if (sec < 10) {
                    timerText.setText(Long.toString(min) + ":" + "0" + Long.toString(sec));
                } else {
                    timerText.setText(Long.toString(min) + ":" + Long.toString(sec));
                }
            }

            @Override
            public void onFinish() {
                if (time < 10) {
                    timerText.setText("0" + String.valueOf(time) + ":00");
                } else {
                    timerText.setText(String.valueOf(time + ":00"));
                }
                timerHasStarted = false;
                periode++;
                periodeText.setText(String.valueOf(periode));
            }
        };

    }

    public void timerPause() {
        isPaused = true;
        timer.cancel();
    }

    private void timerResume() {
        timer = null;
        timerStart(milliLeft);
    }
}
