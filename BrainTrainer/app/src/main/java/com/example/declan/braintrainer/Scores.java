package com.example.declan.braintrainer;

import java.util.Date;

public class Scores {
    private int score;
    private String captured;

    public Scores(int score, String captured) {
        this.score = score;
        this.captured = captured;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCaptured() {
        return captured;
    }

    public void setCaptured(String date) {
        this.captured = captured;
    }
}
