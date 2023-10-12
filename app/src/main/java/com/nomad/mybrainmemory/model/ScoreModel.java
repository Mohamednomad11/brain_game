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

    private float accuracy;

    private long avgReactionTime;

    private long avgSucReactionTime;

    public ScoreModel(int score, String name,String time, String game) {
        this.score = score;
        this.name = name;
        this.time = time;
        this.game = game;
    }

    public ScoreModel(int score,String time, float accuracy, long avgReactionTime, long avgSucReactionTime) {
        this.score = score;
        this.time = time;
        this.accuracy = accuracy;
        this.avgReactionTime = avgReactionTime;
        this.avgSucReactionTime = avgSucReactionTime;
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

    public ScoreModel(int id, int score, String name, String time, String game, Timestamp timeStamp,String uid, float accuracy, long avgReactionTime, long avgSucReactionTime) {
        this.id = id;
        this.score = score;
        this.name = name;
        this.time = time;
        this.game = game;
        this.timeStamp = timeStamp;
        this.uid = uid;
        this.accuracy = accuracy;
        this.avgReactionTime = avgReactionTime;
        this.avgSucReactionTime = avgSucReactionTime;
    }

    public ScoreModel(int score, String name, String time, String game, Timestamp timeStamp,String uid) {
        this.score = score;
        this.name = name;
        this.time = time;
        this.game = game;
        this.timeStamp = timeStamp;
        this.uid = uid;
    }

    public ScoreModel(int score, String name, String time, String game, Timestamp timeStamp,String uid, float accuracy, long avgReactionTime, long avgSucReactionTime) {
        this.id = id;
        this.score = score;
        this.name = name;
        this.time = time;
        this.game = game;
        this.timeStamp = timeStamp;
        this.uid = uid;
        this.accuracy = accuracy;
        this.avgReactionTime = avgReactionTime;
        this.avgSucReactionTime = avgSucReactionTime;
    }

    protected ScoreModel(Parcel in) {
        id = in.readInt();
        score = in.readInt();
        name = in.readString();
        time = in.readString();
        game = in.readString();
        timeStamp = in.readParcelable(Timestamp.class.getClassLoader());
        uid = in.readString();
        accuracy = in.readFloat();
        avgReactionTime = in.readLong();
        avgSucReactionTime = in.readLong();
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

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public long getAvgReactionTime() {
        return avgReactionTime;
    }

    public void setAvgReactionTime(long avgReactionTime) {
        this.avgReactionTime = avgReactionTime;
    }

    public long getAvgSucReactionTime() {
        return avgSucReactionTime;
    }

    public void setAvgSucReactionTime(long avgSucReactionTime) {
        this.avgSucReactionTime = avgSucReactionTime;
    }

    @Override
    public String toString() {
        return "ScoreModel{" +
                "id=" + id +
                ", score=" + score +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", game='" + game + '\'' +
                ", timeStamp=" + timeStamp +
                ", uid='" + uid + '\'' +
                ", accuracy=" + accuracy +
                ", avgReactionTime=" + avgReactionTime +
                ", avgSucReactionTime=" + avgSucReactionTime +
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
        dest.writeFloat(accuracy);
        dest.writeLong(avgReactionTime);
        dest.writeLong(avgSucReactionTime);
    }
}
