package com.ezrimo.mdamanage;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Assign extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    EditText name;
    TextView date;
    Button btEnter, btAssign;
    Date finalDate;
    FirebaseFirestore fStore;
    boolean exist = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign);
        fStore = FirebaseFirestore.getInstance();
        name = findViewById(R.id.etName);
        date = findViewById(R.id.date);
        btEnter = findViewById(R.id.btEnter);
        btAssign = findViewById(R.id.assign);


        SharedPreferences sr = getApplicationContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        boolean isAdmin= sr.getBoolean("isAdmin", false);
        String thisUid = sr.getString("uid", "");
        Event myEvent = new Event(finalDate, thisUid);

        Log.d("TAG", thisUid);

        findViewById(R.id.btCalander).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        btEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(finalDate.equals(null)){
                    date.setError("enter date");
                }
                if(date.equals(null))

                myEvent.setDate(finalDate);
                DocumentReference drUser = fStore.collection("Users").document(thisUid);
                DocumentReference drNewEvent = fStore.collection("Event").document();
                drUser.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                exist = true;
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });//checks for the user
                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("Event", myEvent);
                drNewEvent.set(userInfo);

            }
        });
        btAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isAdmin){
                    myEvent.setUid(thisUid);

                }
            }
        });



    }
    private void showDatePickerDialog(){
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
        String chosenDate = " "+i+"/"+(i1+1)+"/"+(i2-1900);
        date.setText(chosenDate);
        finalDate = new Date(i, i1, i2);
        Toast.makeText(this,  finalDate.toString(), Toast.LENGTH_SHORT).show();
    }

}