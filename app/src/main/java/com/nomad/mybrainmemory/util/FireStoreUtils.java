package com.nomad.mybrainmemory.util;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.nomad.mybrainmemory.model.ScoreModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireStoreUtils {

    public static void saveScores(List<ScoreModel> scoreList){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference scoreCollectionRef = db.collection("scores");

// Assuming you have a list of ScoreModel objects named "scoreList"
       ;
        for (ScoreModel scoreModel : scoreList) {
            Map<String, Object> scoreMap = new HashMap<>();
            scoreMap.put("score", scoreModel.getScore());
            scoreMap.put("name", scoreModel.getName());
            scoreMap.put("time", scoreModel.getTime());
            scoreMap.put("game", scoreModel.getGame());

            scoreCollectionRef.add(scoreMap)
                    .addOnSuccessListener(documentReference -> {
                        // Document was successfully added to Firestore
                        // You can add any success logic here if needed
                    })
                    .addOnFailureListener(e -> {
                        // Handle any error that occurred while saving to Firestore
                        e.printStackTrace();
                    });
        }

    }

    public static List<ScoreModel> retriveScores(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference scoreCollectionRef = db.collection("scores");
        List<ScoreModel> scoreList = new ArrayList<>();
        scoreCollectionRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        int id = Integer.parseInt( document.getId());

                        int score = document.getLong("score").intValue();
                        String name = document.getString("name");
                        String time = document.getString("time");
                        String game = document.getString("game");

                        ScoreModel scoreModel = new ScoreModel(id, score, name, time, game);
                        scoreList.add(scoreModel);
                    }

                    // Use the scoreList to populate the search result list or perform any other operation
                })
                .addOnFailureListener(e -> {
                    // Handle any error that occurred while retrieving data from Firestore
                    e.printStackTrace();
                });

        return scoreList;

    }
}
