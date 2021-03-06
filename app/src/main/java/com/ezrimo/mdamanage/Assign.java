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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Assign extends AppCompatActivity  {
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
        //initialization of my variables
        fStore = FirebaseFirestore.getInstance();
        name = findViewById(R.id.tvName);
        date = findViewById(R.id.date);
        btEnter = findViewById(R.id.btEnter);
        btAssign = findViewById(R.id.assign);

        //reformatting the date
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        SharedPreferences sr = getApplicationContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        boolean isAdmin = sr.getBoolean("isAdmin", false);
        String thisUid = sr.getString("uid", "");
        Event myEvent = new Event(finalDate, thisUid);

        String uid = getIntent().getStringExtra("uid");//my uid
        long dateNum = ((getIntent().getLongExtra("date", 62419651056000L)) - 59958159400001L) + 86400000L;

        //if we want to assign other user uid
        chosenUid = getIntent().getBooleanExtra("chosenUid", false);

        Date thisDate = new Date(dateNum);
        date.setText(formatter.format(thisDate));
        finalDate = new Date(getIntent().getLongExtra("date", 62419651056000L));
        Log.d("TAG", Boolean.toString(chosenUid));
        Log.d("TAG", thisUid);
        Log.d("TAG", uid);

        //if we got back from choosing
        if(chosenUid)
        {
            myEvent.setUid(uid);
            DocumentReference drUser = fStore.collection("User").document(uid);
            /*
            * changes the name in the TV to chosen user
            */
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
            });
        }

        /**
         * gets the chosen date and user and updates it as a new event in FireStore
         */
        btEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if the date is empty
                if (date.getText().equals("")) {
                    Log.d("TAG", "empty");
                    date.setError("no Date selected");
                    return;
                }
                //updating date
                myEvent.setDate(finalDate);

                //checks if the user exists
                DocumentReference drUser = fStore.collection("User").document(uid);
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
                });//checks if user exists

                DocumentReference drNewEvent = fStore.collection("Event").document();
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("uid", myEvent.getUid());//setting uid field
                userInfo.put("date", myEvent.getDate());//setting date field

                //setting the event
                drNewEvent.set(userInfo);
                Intent go = new Intent(Assign.this, ShiftActivity.class);
                startActivity(go);
                finish();

            }
        });

        /**
         * who does the user wanna Assign- himself or another user?
         */
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
                                        /**
                                         * setting this user tp assigning
                                         * @param documentSnapshot this user Document from FireStore
                                         */
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            if(documentSnapshot.exists()){
                                                name.setText(documentSnapshot.getString("fullName"));
                                            }else{
                                                Toast.makeText(Assign.this, "Document Does not Exist ", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                //letting the user theres an error
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG", "get failed with ", e);
                                }
                            });
                        }
                    });
                    /**
                     * going to choose user with added data
                     */
                    alert.setNegativeButton("Users", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent go = new Intent(getApplicationContext(), ChooseUser.class);
                            go.putExtra("fromAdmin", true);
                            long num = ((getIntent().getLongExtra("date", 62419651056000L)));
                            go.putExtra("date", num);
                            Toast.makeText(getApplicationContext(), "the date" + new Date(dateNum).toString(), Toast.LENGTH_SHORT).show();
                            startActivity(go);
                            finish();
                        }
                    });
                    alert.create().show();

                }

            }
        });//who do you want to assign

    }

}