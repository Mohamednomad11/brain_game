package com.nomad.mybrainmemory.util;

import android.util.Log;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.nomad.mybrainmemory.model.ScoreModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.firebase.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.google.firebase.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
            scoreMap.put("timestamp", scoreModel.getTimeStamp());

            scoreCollectionRef.add(scoreMap)
                    .addOnSuccessListener(documentReference -> {
                        // Document was successfully added to Firestore
                        // You can add any success logic here if needed
                        String docId = documentReference.getId();
                    })
                    .addOnFailureListener(e -> {
                        // Handle any error that occurred while saving to Firestore
                        e.printStackTrace();
                    });
        }

    }

    public static void saveScore(ScoreModel scoreModel){


//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference scoreCollectionRef = db.collection("scores");

        Map<String, Object> scoreMap = new HashMap<>();
//        scoreMap.put("uid", scoreModel.getUid());
//        StaticConstants.CURRENT_USER_FID
        scoreMap.put("uid",  StaticConstants.CURRENT_USER_FID);
        scoreMap.put("score", scoreModel.getScore());
        scoreMap.put("name", scoreModel.getName());
        scoreMap.put("time", scoreModel.getTime());
        scoreMap.put("game", scoreModel.getGame());
        scoreMap.put("timestamp", scoreModel.getTimeStamp());
        scoreMap.put("accuracy", scoreModel.getAccuracy());
        scoreMap.put("avgReactionTime",scoreModel.getAvgReactionTime());
        scoreMap.put("avgSucReactionTime",scoreModel.getAvgSucReactionTime());

        scoreCollectionRef.add(scoreMap)
                .addOnSuccessListener(documentReference -> {
                    // Document was successfully added to Firestore
                    // You can add any success logic here if needed
                    String docId = documentReference.getId();
                    Log.e("FireStore","Score saved with " + docId);
                })
                .addOnFailureListener(e -> {
                    // Handle any error that occurred while saving to Firestore
                    Log.e("FireStore","Score not saved with " );
                    e.printStackTrace();
                });

    }

    public static List<ScoreModel> retriveScores(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference scoreCollectionRef = db.collection("scores");
        List<ScoreModel> scoreList = new ArrayList<>();
        scoreCollectionRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
//                        int id = Integer.parseInt( document.getId());
                        String uid = document.getString("uid");
                        int score = document.getLong("score").intValue();
                        String name = document.getString("name");
                        String time = document.getString("time");
                        String game = document.getString("game");
                        Timestamp timestamp = document.getTimestamp("timestamp");
//                        if (timestamp != null) {
//                            // Handle the timestamp (e.g., convert it to a Date object)
//                            Date date = timestamp.toDate();
//                        }

                        ScoreModel scoreModel = new ScoreModel(score, name, time, game,timestamp,uid);
                        Log.e("FirebseUtils",scoreModel.toString());
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


    public static String timestampToString(Timestamp timestamp) {
        Date date = timestamp.toDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(date);
    }

    public static Timestamp stringToTimestamp(String dateString){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Timestamp timestamp = null;
        //throws ParseException
        try {
            Date date = sdf.parse(dateString);
            timestamp = new Timestamp(date);
        }catch (Exception e){

            Log.e("Fire store Utils",e.getMessage());

        }

        return timestamp;
    }
}
