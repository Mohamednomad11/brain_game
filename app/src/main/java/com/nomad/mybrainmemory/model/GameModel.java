package com.nomad.mybrainmemory.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.HashMap;

public class GameModel implements Parcelable {
    private int score;

    private int timeSpent;

    private HashMap<Integer,Integer> levelStatus = new HashMap<>();

    public GameModel() {
        score = 0;
    }

    public GameModel(int score) {
        this.score = score;
    }

    protected GameModel(Parcel in) {
        score = in.readInt();
        timeSpent = in.readInt();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            Integer key = in.readInt();
            Integer value = in.readInt();
            levelStatus.put(key, value);
        }

    }

    public static final Creator<GameModel> CREATOR = new Creator<GameModel>() {
        @Override
        public GameModel createFromParcel(Parcel in) {
            return new GameModel(in);
        }

        @Override
        public GameModel[] newArray(int size) {
            return new GameModel[size];
        }
    };

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

    public void updateLevelStatus(int levelNo, Integer status) {
       this.levelStatus.put(levelNo,status);
    }

    public HashMap<Integer,Integer> getLevelStatus(){
        return this.levelStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(score);
        dest.writeInt(timeSpent);
        // if this part is uncommented I get a NullPointerException
        dest.writeInt(levelStatus.size());
        for (HashMap.Entry<Integer, Integer> entry : levelStatus.entrySet()) {
            dest.writeInt(entry.getKey());
            dest.writeInt(entry.getValue());
        }
    }
}
