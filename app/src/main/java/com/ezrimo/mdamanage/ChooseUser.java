package com.ezrimo.mdamanage;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ChooseUser extends AppCompatActivity {
    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private CollectionReference userRef = fStore.collection("User");

    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user);
        Log.d("TAG", userRef.orderBy("isAdmin", Query.Direction.ASCENDING).toString());
        Query query = userRef.orderBy("isAdmin", Query.Direction.ASCENDING);
        setUpRecView();

    }

    private void setUpRecView() {
        Query query = userRef.orderBy("email", Query.Direction.ASCENDING);
        Log.d("TAG", query.toString());

        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>().
                setQuery(query, User.class)
                .build();
        adapter = new UserAdapter(options);
        Log.d("TAG", "adapter");
        RecyclerView recyclerView = findViewById(R.id.recview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}