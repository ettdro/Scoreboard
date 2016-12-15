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
import android.widget.AdapterView;
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
    private long timeP1;
    private long timeP2;
    private long timeP3;
    private long timeP4;
    private CountDownTimer timer;
    private CountDownTimer timerP1;
    private long milliLeft;
    private long milliLeftP1;
    private long milliLeftP2;
    private long milliLeftP3;
    private long milliLeftP4;
    private long min;
    private long sec;
    TextView timerText;
    private boolean timerHasStarted;
    private boolean penaliteHasStarted;

    TextView penalite1;
    TextView penalite2;
    TextView penalite3;
    TextView penalite4;

    TextView timerPenalite1;
    TextView timerPenalite2;
    TextView timerPenalite3;
    TextView timerPenalite4;

    Button setTimerPenalite1;
    Button setTimerPenalite2;
    Button setTimerPenalite3;
    Button setTimerPenalite4;
    Button buttonSetTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        final Seeder seeder = new Seeder();
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.deleteAll();
        seeder.seed(dbHelper);

        this.timerText = (TextView) findViewById(R.id.timerText);
        this.periodeText = (TextView) findViewById(R.id.periodeText);

        this.penalite1 = (TextView) findViewById(R.id.penalite1);
        this.penalite2 = (TextView) findViewById(R.id.penalite2);
        this.penalite3 = (TextView) findViewById(R.id.penalite3);
        this.penalite4 = (TextView) findViewById(R.id.penalite4);

        this.timerPenalite1 = (TextView) findViewById(R.id.timerPenalite1);
        this.timerPenalite2 = (TextView) findViewById(R.id.timerPenalite2);
        this.timerPenalite3 = (TextView) findViewById(R.id.timerPenalite3);
        this.timerPenalite4 = (TextView) findViewById(R.id.timerPenalite4);

        this.buttonSetTimer = (Button) findViewById(R.id.buttonSetTimer);

        this.setTimerPenalite1 = (Button) findViewById(R.id.setTimerPenalite1);
        this.setTimerPenalite2 = (Button) findViewById(R.id.setTimerPenalite2);
        this.setTimerPenalite3 = (Button) findViewById(R.id.setTimerPenalite3);
        this.setTimerPenalite4 = (Button) findViewById(R.id.setTimerPenalite4);



        final Button buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isPaused) {
                    isPaused = false;
                    timerResume();
                    timer.start();
                    
                    timerStartP1(timeP1 * 60000);
                    timerP1.start();
                } else {
                    if (timerHasStarted) {
                    } else {
                        if (time == 0) {
                        } else {
                            timerHasStarted = true;
                            timerStart(time * 60000);
                            timer.start();

                            penaliteHasStarted = true;
                            timerStartP1(timeP1 * 60000);
                            timerP1.start();
                        }
                    }
                }
                buttonSetTimer.setEnabled(false);
                setTimerPenalite1.setEnabled(false);
            }
        });

        final Button buttonPause = (Button) findViewById(R.id.buttonPause);
        buttonPause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (timerHasStarted) {
                    buttonSetTimer.setEnabled(true);
                    setTimerPenalite1.setEnabled(true);
                    timerPause();
                }
            }
        });

        final Button buttonReset = (Button) findViewById(R.id.buttonReset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (timerHasStarted) {
                    timerHasStarted = false;
                    penaliteHasStarted = false;
                    isPaused = false;
                    time = 0;
                    timeP1 = 0;
                    timer.cancel();
                    timerP1.cancel();

                    timerText.setText(String.valueOf("00:00"));
                    timerPenalite1.setText(String.valueOf("00:00"));
                }
                setTimerPenalite1.setEnabled(true);
            }
        });

        this.buttonSetTimer.setOnClickListener(new View.OnClickListener() {
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


        this.setTimerPenalite1.setOnClickListener(new View.OnClickListener() {
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
                            timeP1 = Long.valueOf(input.getText().toString());
                            if (timeP1 < 10) {
                                timerPenalite1.setText("0" + String.valueOf(timeP1) + ":00");
                            } else {
                                timerPenalite1.setText(String.valueOf(timeP1 + ":00"));
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

        this.setTimerPenalite2.setOnClickListener(new View.OnClickListener() {
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
                            timerPenalite2.setText("0" + String.valueOf(Long.valueOf(input.getText().toString())) + ":00");
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

        this.setTimerPenalite3.setOnClickListener(new View.OnClickListener() {
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
                            timerPenalite3.setText("0" + String.valueOf(Long.valueOf(input.getText().toString())) + ":00");
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

        this.setTimerPenalite4.setOnClickListener(new View.OnClickListener() {
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
                            timerPenalite4.setText("0" + String.valueOf(Long.valueOf(input.getText().toString())) + ":00");
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
        Spinner spinnerPenaltyV1 = (Spinner)findViewById(R.id.spinnerPenaltyV1);
        Spinner spinnerPenaltyV2 = (Spinner)findViewById(R.id.spinnerPenaltyV2);
        Spinner spinnerPenaltyL1 = (Spinner)findViewById(R.id.spinnerPenaltyL1);
        Spinner spinnerPenaltyL2 = (Spinner)findViewById(R.id.spinnerPenaltyL2);

        ArrayAdapter<String> adapterV1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listeJoueursVString);
        adapterV1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPenaltyV1.setAdapter(adapterV1);

        ArrayAdapter<String> adapterV2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listeJoueursVString);
        adapterV2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPenaltyV2.setAdapter(adapterV2);

        ArrayAdapter<String> adapterL1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listeJoueursLString);
        adapterL1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPenaltyL1.setAdapter(adapterL1);

        ArrayAdapter<String> adapterL = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listeJoueursLString);
        adapterL.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPenaltyL2.setAdapter(adapterL);

        spinnerPenaltyV1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                penalite1.setText(String.valueOf(seeder.listeJoueursVisiteur.get(i).numero));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerPenaltyV2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                penalite2.setText(String.valueOf(seeder.listeJoueursVisiteur.get(i).numero));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerPenaltyL1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                penalite3.setText(String.valueOf(seeder.listeJoueursLocal.get(i).numero));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerPenaltyL2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                penalite4.setText(String.valueOf(seeder.listeJoueursLocal.get(i).numero));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        JoueurCRUD db = new JoueurCRUD(this);
//        Joueur joueurV = new Joueur();
//        Joueur joueurL = new Joueur();
//
//        this.listeJoueursVisiteur = new ArrayList<Joueur>();
//        this.listeJoueursLocal = new ArrayList<Joueur>();
//
//        joueurV.nom = this.nomV;
//        joueurV.numero = this.numeroV;
//        joueurV.position = this.positionV;
//        joueurV.equipe = this.equipeV;
//
//        db.insert(joueurV);
//
//        joueurL.nom = this.nomL;
//        joueurL.numero = this.numeroL;
//        joueurL.position = this.positionL;
//        joueurL.equipe = this.equipeL;
//
//        db.deleteAll();
//        for (int i = 0; i < 23; i++) {
//            db.insert(joueurV);
//            this.listeJoueursVisiteur.add(joueurV);
//        }
//
//        for (int i = 0; i < 23; i++) {
//            db.insert(joueurL);
//            this.listeJoueursLocal.add(joueurL);
//        }

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
                penaliteHasStarted = false;
                timer.cancel();
                timeP1 = milliLeftP1 / 60000;
                timerP1.cancel();
                periode++;
                periodeText.setText(String.valueOf(periode));
            }
        };
    }

    public void timerStartP1(long timeLengthMilli) {
        timerP1 = new CountDownTimer(timeLengthMilli ,1000) {
            @Override
            public void onTick(long milliTillFinish) {
                milliLeftP1 = milliTillFinish;
                min = (milliTillFinish / (1000 * 60));
                sec = ((milliTillFinish / 1000) - min * 60);
                if (min < 10) {
                    timerPenalite1.setText("0" + Long.toString(min) + ":" + "0" + Long.toString(sec));
                } else {
                    timerPenalite1.setText(Long.toString(min) + ":" + "0" + Long.toString(sec));
                }
                if (sec < 10) {
                    timerPenalite1.setText(Long.toString(min) + ":" + "0" + Long.toString(sec));
                } else {
                    timerPenalite1.setText(Long.toString(min) + ":" + Long.toString(sec));
                }
            }

            @Override
            public void onFinish() {
                if (timeP1 < 10) {
                    timerPenalite1.setText("00:00");
                } else {
                    timerPenalite1.setText("00:00");
                }
                timerHasStarted = false;
                timerPenalite1.setText("00:00");
                penalite1.setText(String.valueOf("00"));
                timeP1 = 0;
            }
        };
    }

    public void timerPause() {
        isPaused = true;
        if (timerHasStarted) {
            timer.cancel();
        }
        if (penaliteHasStarted) {
            timerP1.cancel();
        }
    }

    private void timerResume() {
        timer = null;
        timerP1 = null;
        timerStart(milliLeft);
        timerStartP1(milliLeftP1);
    }
}
