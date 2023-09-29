package com.nomad.mybrainmemory;

import android.os.Bundle;
        import android.text.Editable;
        import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.nomad.mybrainmemory.model.ScoreModel;
import com.nomad.mybrainmemory.util.StaticConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchReportActivity extends AppCompatActivity {

    private RecyclerView recyclerViewSearchResults;
    private UserReportAdapter userAdapter;
    private List<ScoreModel> allUsers =  new ArrayList<>();; // Replace this with your actual list of user names

    List<ScoreModel> scoreList = new ArrayList<>();

    private void getUserReportFromServer(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference scoreCollectionRef = db.collection("scores");

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
                        scoreList.add(scoreModel);

                    }

                    StaticConstants.scoreList = scoreList;
                    HashMap<String,List<ScoreModel>> userScoreMap = new HashMap<>();



                    // Use the scoreList to populate the search result list or perform any other operation
                    for (ScoreModel scr:scoreList
                    ) {
                        Log.e("SearchReportActivity",scr.getName());
//                        allUsers.add(scr);
                        if(userScoreMap.get(scr.getUid())!=null){
                            List<ScoreModel> currentList =  userScoreMap.get(scr.getUid());
                            currentList.add(scr);
                            userScoreMap.put(scr.getUid(),currentList);
                        }else{
                            List<ScoreModel> currentList = new ArrayList<>();
                            currentList.add(scr);
                            userScoreMap.put(scr.getUid(),currentList);
                        }
                    }

                    StaticConstants.userScoreMap = userScoreMap;

                    for (String uid:
                    userScoreMap.keySet()) {
                        ScoreModel scr = userScoreMap.get(uid).get(0);
                        allUsers.add(scr);


                    }
//                        userAdapter.notifyDataSetChanged();
                    userAdapter.setUserReports(allUsers);
                })
                .addOnFailureListener(e -> {
                    // Handle any error that occurred while retrieving data from Firestore
                    e.printStackTrace();
                });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_report);

        // Initialize your list of user names
//        allUsers = new ArrayList<>();
//        allUsers.add("John");
//        allUsers.add("Alice");
//        allUsers.add("Bob");
        // Add more user names to the list as needed

        getUserReportFromServer();

        recyclerViewSearchResults = findViewById(R.id.recyclerViewSearchResults);
        recyclerViewSearchResults.setLayoutManager(new LinearLayoutManager(this));

        // Create an adapter for the RecyclerView
        userAdapter = new UserReportAdapter(this, allUsers);
        recyclerViewSearchResults.setAdapter(userAdapter);

        EditText editTextSearch = findViewById(R.id.editTextSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Filter the user names based on the search input
                String query = charSequence.toString().toLowerCase();
                List<ScoreModel> filteredUsers = new ArrayList<>();
                for (ScoreModel user : allUsers) {
                    if (user.getName().toLowerCase().contains(query)) {
                        filteredUsers.add(user);
                    }
                }
                userAdapter.setUserReports(filteredUsers); // Update the adapter with filtered results
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}
