package com.ezrimo.mdamanage;

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

public class RecycleViewAdmin extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<User> userArrayList;
    RecycleAdapter myAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_admin);

        progressDialog = new ProgressDialog(this);
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

        db.collection("Users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                        //e.getMessage();
                        //Toast.makeText(getApplicationContext(), "error"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                        if (e != null){
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                            Toast.makeText(getApplicationContext(), "error"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()){
                            //dc.getDocument().toObject(User.class);
                            Toast.makeText(getApplicationContext(), "in the loop "+dc.getDocument().getString("isAdmin"), Toast.LENGTH_SHORT).show();
                            if (dc.getType() == DocumentChange.Type.ADDED){
                              //  userArrayList.add(dc.getDocument().toObject(User.class));
                            }


                            myAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }


                        }
                    }



                });


    }
}