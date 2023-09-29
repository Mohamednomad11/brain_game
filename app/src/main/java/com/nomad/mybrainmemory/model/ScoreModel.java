package com.nomad.mybrainmemory.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;

import java.util.Date;

public class ScoreModel implements Parcelable {

    private int id;
    private int score;
    private String name;

    private String time;
    //6783949786
    private String game;

    private Timestamp timeStamp;

    private String uid;

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

    public ScoreModel(int id, int score, String name, String time, String game, Timestamp timeStamp) {
        this.id = id;
        this.score = score;
        this.name = name;
        this.time = time;
        this.game = game;
        this.timeStamp = timeStamp;
    }

    public ScoreModel(int id, int score, String name, String time, String game, Timestamp timeStamp,String uid) {
        this.id = id;
        this.score = score;
        this.name = name;
        this.time = time;
        this.game = game;
        this.timeStamp = timeStamp;
        this.uid = uid;
    }

    public ScoreModel(int score, String name, String time, String game, Timestamp timeStamp,String uid) {
        this.score = score;
        this.name = name;
        this.time = time;
        this.game = game;
        this.timeStamp = timeStamp;
        this.uid = uid;
    }

    protected ScoreModel(Parcel in) {
        id = in.readInt();
        score = in.readInt();
        name = in.readString();
        time = in.readString();
        game = in.readString();
        timeStamp = in.readParcelable(Timestamp.class.getClassLoader());
        uid = in.readString();
    }

    public static final Creator<ScoreModel> CREATOR = new Creator<ScoreModel>() {
        @Override
        public ScoreModel createFromParcel(Parcel in) {
            return new ScoreModel(in);
        }

        @Override
        public ScoreModel[] newArray(int size) {
            return new ScoreModel[size];
        }
    };

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

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "ScoreModel{" +
                "id=" + id +
                ", score=" + score +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", game='" + game + '\'' +
                ", timeStamp " + timeStamp.toDate().toString() + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(score);
        dest.writeString(name);
        dest.writeString(time);
        dest.writeString(game);
        dest.writeParcelable(timeStamp, flags);
        dest.writeString(uid);
    }
}
