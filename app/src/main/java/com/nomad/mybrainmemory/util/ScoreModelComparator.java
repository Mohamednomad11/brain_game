package com.nomad.mybrainmemory.util;

import com.nomad.mybrainmemory.model.ScoreModel;

import java.util.Comparator;

public class ScoreModelComparator implements Comparator<ScoreModel> {
    @Override
    public int compare(ScoreModel model1, ScoreModel model2) {
        // Compare the Timestamp values of model1 and model2
        return model1.getTimeStamp().compareTo(model2.getTimeStamp());
    }
}