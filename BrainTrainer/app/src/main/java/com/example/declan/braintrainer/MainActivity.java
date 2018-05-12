package com.example.declan.braintrainer;

import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    //init widgets
    Button startButton, button0, button1, button2, button3, playAgainButton, scoresButton, backButton, playButton, easyButton, mediumButton, hardButton;
    TextView sumTextView, resultTextView, pointsTextView, timerTextView, totalScoreTextView, averageScoreTextView;
    ConstraintLayout mainConstraintLayout, scoresConstraintLayout;
    ImageView brainImg;

    //init application variables
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfCorrectAnswer;
    int score = 0;
    int numberOfQuestions = 0;
    private SqlLiteHelper db;
    //difficulty - 0 is easy, 1 is medium, 2 is hard
    private int difficulty = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get widgets
        startButton = (Button) findViewById(R.id.startButton);
        playButton = (Button) findViewById(R.id.playButton);
        scoresButton = (Button) findViewById(R.id.scoresButton);
        easyButton = (Button) findViewById(R.id.easyButton);
        mediumButton = (Button) findViewById(R.id.mediumButton);
        hardButton = (Button) findViewById(R.id.hardButton);
        backButton = (Button) findViewById(R.id.backButton);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        sumTextView = (TextView) findViewById(R.id.sumTextView);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        pointsTextView = (TextView) findViewById(R.id.pointsTextView);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        totalScoreTextView = (TextView) findViewById(R.id.totalScoreTextView);
        averageScoreTextView = (TextView) findViewById(R.id.averageScoreTextView);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        mainConstraintLayout = (ConstraintLayout) findViewById(R.id.mainConstraintLayout);
        scoresConstraintLayout = (ConstraintLayout) findViewById(R.id.scoresConstraintLayout);
        brainImg = (ImageView) findViewById(R.id.brainImg);

        //init db connection
        db = new SqlLiteHelper(this);
    }

    //starts the game
    public void enter(View view) {
        //make start button invisible
        startButton.setVisibility(View.INVISIBLE);
        mainConstraintLayout.setVisibility(View.VISIBLE);
        brainImg.setVisibility(View.INVISIBLE);
        scoresButton.setVisibility(View.VISIBLE);

    }

    public void start(View view) {
        playButton.setVisibility(View.INVISIBLE);
        scoresButton.setVisibility(View.INVISIBLE);
        easyButton.setVisibility(View.INVISIBLE);
        mediumButton.setVisibility(View.INVISIBLE);
        hardButton.setVisibility(View.INVISIBLE);
        playAgain(findViewById(R.id.playAgainButton));
    }

    //generates the next question in the game
    public void generateQuestion() {
        Random rand = new Random();

        if(difficulty == 0) {
            int a = rand.nextInt(21);
            int b = rand.nextInt(21);

            sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));

            //generate location of correction answer, i.e. what button it will be on
            locationOfCorrectAnswer = rand.nextInt(4);

            //nuke answers list so new answers appear
            answers.clear();

            int incorrectAnswer;

            //generate the incorrect answers
            for(int i = 0; i <= 4; i++) {
                if(i == locationOfCorrectAnswer) {
                    answers.add(a + b);
                } else {
                    incorrectAnswer = rand.nextInt(41);

                    while(incorrectAnswer == a + b) {
                        incorrectAnswer = rand.nextInt(41);
                    }
                    answers.add(incorrectAnswer);
                }
            }
        } else if(difficulty == 1) {
            int low = 30;
            int high = 70;
            int result1 = rand.nextInt(high - low) + low;
            int result2 = rand.nextInt(high - low) + low;

            sumTextView.setText(Integer.toString(result1) + " + " + Integer.toString(result2));

            //generate location of correction answer, i.e. what button it will be on
            locationOfCorrectAnswer = rand.nextInt(4);

            //nuke answers list so new answers appear
            answers.clear();

            int incorrectAnswer;

            //generate the incorrect answers
            for(int i = 0; i <= 4; i++) {
                if(i == locationOfCorrectAnswer) {
                    answers.add(result1 + result2);
                } else {
                    incorrectAnswer = rand.nextInt(71);

                    while(incorrectAnswer == result1 + result2) {
                        incorrectAnswer = rand.nextInt(71);
                    }
                    answers.add(incorrectAnswer);
                }
            }
        } else if(difficulty == 2) {
            int low = 100;
            int high = 250;
            int result1 = rand.nextInt(high - low) + low;
            int result2 = rand.nextInt(high - low) + low;

            sumTextView.setText(Integer.toString(result1) + " + " + Integer.toString(result2));

            //generate location of correction answer, i.e. what button it will be on
            locationOfCorrectAnswer = rand.nextInt(4);

            //nuke answers list so new answers appear
            answers.clear();

            int incorrectAnswer;

            //generate the incorrect answers
            for(int i = 0; i <= 4; i++) {
                if(i == locationOfCorrectAnswer) {
                    answers.add(result1 + result2);
                } else {
                    incorrectAnswer = rand.nextInt(450);

                    while(incorrectAnswer == result1 + result2) {
                        incorrectAnswer = rand.nextInt(450);
                    }
                    answers.add(incorrectAnswer);
                }
            }
        }

        //Set answers to buttons
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    public void chooseAnswer(View view) {
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
            score++;
            resultTextView.setText("Correct!");
        } else {
            resultTextView.setText("Wrong!");
        }

        numberOfQuestions++;
        pointsTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
        generateQuestion();
    }

    public void chooseDifficulty(View view) {
        if(view.getTag().equals("easy")) {
            difficulty = 0;
            Toast.makeText(this, "Difficulty changed to easy", Toast.LENGTH_LONG).show();
        } else if(view.getTag().equals("medium")) {
            difficulty = 1;
            Toast.makeText(this, "Difficulty changed to medium", Toast.LENGTH_LONG).show();
        } else if(view.getTag().equals("hard")) {
            difficulty = 2;
            Toast.makeText(this, "Difficulty changed to hard", Toast.LENGTH_LONG).show();
        }
    }

    public void playAgain(View view) {
        score = 0;
        numberOfQuestions = 0;
        timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);
        button0.setEnabled(true);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);

        generateQuestion();

        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                timerTextView.setText("0s");
                resultTextView.setText("Your score: " + Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
                scoresButton.setVisibility(View.VISIBLE);
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                easyButton.setVisibility(View.VISIBLE);
                mediumButton.setVisibility(View.VISIBLE);
                hardButton.setVisibility(View.VISIBLE);


                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date date = new Date();
                String capturedDate = dateFormat.format(date).toString();

                //check if questions have been answered, stops stupid amounts of zeros entering db
                if(numberOfQuestions == 0) {

                } else {
                    db.insertScore(new Scores(score, capturedDate));
                    db.getScores();
                }
                playButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    public void showScores(View view) {
        mainConstraintLayout.setVisibility(View.INVISIBLE);
        scoresConstraintLayout.setVisibility(View.VISIBLE);
        scoresButton.setVisibility(View.INVISIBLE);

        if(db.getScoreCount() == 0) {
            averageScoreTextView.setText("No scores");
            totalScoreTextView.setText("No Scores");
        } else {
            averageScoreTextView.setText(Integer.toString(db.getAverageScore()));
            totalScoreTextView.setText(Integer.toString(db.getTotalScore()));
        }


    }

    public void backToGame(View view) {
        scoresConstraintLayout.setVisibility(View.INVISIBLE);
        mainConstraintLayout.setVisibility(View.VISIBLE);
        playButton.setVisibility(View.VISIBLE);
        scoresButton.setVisibility(View.VISIBLE);
    }
}
