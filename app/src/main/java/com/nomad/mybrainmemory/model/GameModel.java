package com.nomad.mybrainmemory.model;

import java.util.HashMap;

public class GameModel {
    private int score;

    private int timeSpent;

    private HashMap<Integer,Boolean> levelStatus = new HashMap<>();

    public GameModel() {
        score = 0;
    }

    public GameModel(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score += score;
    }

    public int getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(int timeSpent) {
        this.timeSpent += timeSpent;
    }

    public void updateLevelStatus(int levelNo, boolean status) {
       this.levelStatus.put(levelNo,status);
    }

    public HashMap<Integer,Boolean> getLevelStatus(){
        return this.levelStatus;
    }
}
