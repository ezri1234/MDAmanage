package com.ezrimo.mdamanage;

import static java.lang.Long.parseLong;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class RecycleViewAdmin extends AppCompatActivity {

    /*RecyclerView recyclerView;
    ArrayList<User> userArrayList;
    RecycleAdapter myAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_admin);

        /*progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("getting data...");
        progressDialog.show();

        recyclerView = findViewById(R.id.recycleViewAdmin);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<User>();
        myAdapter = new RecycleAdapter(RecycleViewAdmin.this, userArrayList);
        recyclerView.setAdapter(myAdapter);

        AddUserListener();
    }

    private void AddUserListener() {

        db.collection("Users").orderBy("isAdmin", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                        if (e != null){
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                            Toast.makeText(getApplicationContext(), "error"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
                                Log.d("TAG" ,dc.getDocument().getLong("isAdmin").toString());
                                HashMap<String, Object> usersMap = (HashMap<String, Object>) dc.getDocument().getData().get("Users");
                                userArrayList.add( new User( usersMap.get("fullName").toString(), usersMap.get("userEmail").toString(), parseLong(usersMap.get("isAdmin").toString())));
                            }


                            myAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }

                        }
                    }

                });


    }*/
    }
}