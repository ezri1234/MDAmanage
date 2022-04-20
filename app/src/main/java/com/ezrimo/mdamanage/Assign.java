package com.ezrimo.mdamanage;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.StringValue;
import com.google.protobuf.StringValueOrBuilder;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Assign extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView date, name;
    Button btEnter, btAssign;
    Date finalDate = null;
    FirebaseFirestore fStore;
    boolean exist = false;
    boolean chosenUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign);
        fStore = FirebaseFirestore.getInstance();
        name = findViewById(R.id.tvName);
        date = findViewById(R.id.date);
        btEnter = findViewById(R.id.btEnter);
        btAssign = findViewById(R.id.assign);


        SharedPreferences sr = getApplicationContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        boolean isAdmin = sr.getBoolean("isAdmin", false);
        String thisUid = sr.getString("uid", "");
        Event myEvent = new Event(finalDate, thisUid);

        String uid = getIntent().getStringExtra("uid");
        chosenUid = getIntent().getBooleanExtra("chosenUid", false);
        Log.d("TAG", Boolean.toString(chosenUid));
        Log.d("TAG", thisUid);
        Log.d("TAG", uid);

        if(chosenUid)//if we got back from choosing
        {
            myEvent.setUid(uid);
            DocumentReference drUser = fStore.collection("User").document(uid);
            drUser.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()){
                        name.setText(documentSnapshot.getString("fullName"));
                    }else{
                        Toast.makeText(Assign.this, "Document Does not Exist ", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("TAG", "get failed with ", e);
                }
            });//changes the name in the tv to chosen user
        }

        findViewById(R.id.btCalander).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        //Timestamp ts = new Timestamp(myEvent.getDate());
        /*fStore.collection("Event").whereArrayContains("date",ts).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Map o = (Map) document.getData().get("Event");
                                Timestamp ts = (Timestamp) o.get("date");
                                if (ts.toString().equals("2/3/2022")) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());

                                }
                            }
                            Log.d(TAG, "");


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });*///checks for same date events
        btEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (date.getText().equals("")) {
                    Log.d("TAG", "empty");
                    date.setError("no Date selected");
                    return;
                }
                myEvent.setDate(finalDate);
                DocumentReference drUser = fStore.collection("User").document(thisUid);


                DocumentReference drNewEvent = fStore.collection("Event").document();


                drUser.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();

                            if (document.exists()) {
                                Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                                exist = true;
                            } else {
                                Log.d("TAG", "No such document");
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });//checks for the user
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("uid", myEvent.getUid());
                userInfo.put("date", myEvent.getDate());
                drNewEvent.set(userInfo);

            }
        });

        btAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isAdmin) {
                    myEvent.setUid(thisUid);

                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(Assign.this);
                    alert.setTitle("Choose");
                    alert.setMessage("who do you want to assign?");
                    alert.setPositiveButton("Me", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            myEvent.setUid(thisUid);
                            DocumentReference drUser = fStore.collection("User").document(thisUid);
                            drUser.get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            if(documentSnapshot.exists()){
                                                name.setText(documentSnapshot.getString("fullName"));
                                            }else{
                                                Toast.makeText(Assign.this, "Document Does not Exist ", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG", "get failed with ", e);
                                }
                            });
                        }
                    });

                    alert.setNegativeButton("Users", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent go = new Intent(getApplicationContext(), ChooseUser.class);
                            go.putExtra("fromAdmin", true);
                            startActivity(go);
                            finish();
                        }
                    });
                    alert.create().show();


                }
                //Timestamp ts = new Timestamp(myEvent.getDate());
                //Log.d("TAG", ts.toString());
                /*fStore.collection("Event")
                        .whereEqualTo("uid", thisUid)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d("TAG", document.getId() + " => " + document.getData());
                                    }
                                } else {
                                    Log.d("TAG", "Error getting documents: ", task.getException());
                                }
                            }
                        });*/ //ths works, i get the timestamp by uid
            }
        });


    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.DATE),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String chosenDate = " " + (i2) + "/" + (i1) + "/" + (i);
        date.setText(chosenDate);
        finalDate = new Date(i, i1, i2);
        Toast.makeText(this, finalDate.toString(), Toast.LENGTH_SHORT).show();
    }

}