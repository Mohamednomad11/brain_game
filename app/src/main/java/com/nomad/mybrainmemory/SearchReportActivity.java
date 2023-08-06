package com.nomad.mybrainmemory;

import android.os.Bundle;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.widget.EditText;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;
        import java.util.ArrayList;
        import java.util.List;

public class SearchReportActivity extends AppCompatActivity {

    private RecyclerView recyclerViewSearchResults;
    private UserReportAdapter userAdapter;
    private List<String> allUsers; // Replace this with your actual list of user names

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_report);

        // Initialize your list of user names
        allUsers = new ArrayList<>();
        allUsers.add("John");
        allUsers.add("Alice");
        allUsers.add("Bob");
        // Add more user names to the list as needed

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
                List<String> filteredUsers = new ArrayList<>();
                for (String userName : allUsers) {
                    if (userName.toLowerCase().contains(query)) {
                        filteredUsers.add(userName);
                    }
                }
                userAdapter.setUsers(filteredUsers); // Update the adapter with filtered results
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}
