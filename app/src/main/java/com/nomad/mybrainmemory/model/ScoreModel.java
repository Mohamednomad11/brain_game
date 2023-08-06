package com.nomad.mybrainmemory.model;

public class ScoreModel {

    private int id;
    private int score;
    private String name;

    private String time;

    private String game;

    public ScoreModel(int score, String name,String time, String game) {
        this.score = score;
        this.name = name;
        this.time = time;
        this.game = game;
    }

    public ScoreModel(int id, int score, String name, String time, String game) {
        this.id = id;
        this.score = score;
        this.name = name;
        this.time = time;
        this.game = game;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return "ScoreModel{" +
                "id=" + id +
                ", score=" + score +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", game='" + game + '\'' +
                '}';
    }
}
