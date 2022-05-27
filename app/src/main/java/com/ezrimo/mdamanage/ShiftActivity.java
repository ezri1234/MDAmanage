package com.ezrimo.mdamanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.Date;

/**
 * The type Shift activity.
 */
public class ShiftActivity extends AppCompatActivity {
    protected static TextView [] places;
    protected static FirebaseFirestore fStore;
    int numOfU;

    /**
     * in this activity the user can see who's in this dates shift.
     * and if admin he can assign any user he wants, as long there are less than 3 assigned
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift);

        places = new TextView[3];


        places[0] = findViewById(R.id.shift1);
        places[1] = findViewById(R.id.shift2);
        places[2] = findViewById(R.id.shift3);
        long num = getIntent().getLongExtra("date", 62419651056000l);
        Date myDate = new Date(num);

        Log.d("TAG", myDate.toString());
        String [] sArr = new String[3];
        fStore = FirebaseFirestore.getInstance();
        //getCurrentUid(myDate);

        fStore.collection("Event")
                .whereEqualTo("date", myDate)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            /**
             * updating who is on today's shift
             * @param task the Query that has all the Events at this (myDate) Date
             */
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.d("TAG", "in firebase");
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        numOfU++;
                    }

                    if(numOfU>3)
                        return;
                    int i = 0;
                    for (QueryDocumentSnapshot document : task.getResult()){
                        updateNames(document.getString("uid"), places);
                        sArr[i] = document.getString("uid");
                        Log.d("TAG", sArr[i]);
                        i++;
                    }
                    for (int k = 0; k<sArr.length;k++){}
                        //updateNames(sArr[i], places);
                }else{
                    Toast.makeText(getApplicationContext(), "error "+task.getException().toString() , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * returns the first empty TextView
     *
     * @param arr the TV used
     * @return the integer if the first empty tv in the array
     */
    public int smallestIsEmpty(TextView[]arr){

        for (int i = 0; i<arr.length;i++){
            if (arr[i].getText().toString().isEmpty())
                return i;
        }
        return -1;
    }

    /**
     * updates the names
     *
     * @param uid the wanted user uid
     * @param arr the TextView array
     */
    public void updateNames(String uid, TextView[]arr){

        fStore.collection("User")
                .document(uid)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                    if(!places[smallestIsEmpty(arr)].getText().toString().equals(-1)) {
                        places[smallestIsEmpty(arr)].setText(documentSnapshot.getString("fullName"));
                    }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * if Admin it lets him assign any user to this shift
     * it is the same for the 3 shifts, just different TextViews
     *
     * @param view the view
     */
    public void shift1(View view) {

        TextView tv = (TextView)view;
        SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);
        boolean isAdmin = sp.getBoolean("isAdmin", false);
        if(isAdmin&&tv.getText().toString().isEmpty()){
            assignShift();
        }
    }

    /**
     * if Admin it lets him assign any user to this shift
     * it is the same for the 3 shifts, just different TextViews
     *
     * @param view what button was clicked
     */
    public void shift2(View view) {
        TextView tv = (TextView)view;
        SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);
        boolean isAdmin = sp.getBoolean("isAdmin", false);
        if(isAdmin&&tv.getText().toString().isEmpty()){
            assignShift();
        }
    }

    /**
     *if Admin it lets him assign any user to this shift
     *it is the same for the 3 shifts, just different TextViews
     * @param view what button was clicked
     */
    public void shift3(View view) {
        TextView tv = (TextView)view;
        SharedPreferences sp = getSharedPreferences("User", MODE_PRIVATE);
        boolean isAdmin = sp.getBoolean("isAdmin", false);
        if(isAdmin&&tv.getText().toString().isEmpty()){
            assignShift();
        }
    }

    /**
     * goes to assign activity and sends the wanted data
     */
    public void assignShift(){
        long dateInMilli = getIntent().getLongExtra("date", 62419651056000l);

        SharedPreferences sr = getApplicationContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        boolean isAdmin = sr.getBoolean("isAdmin", false);
        String thisUid = sr.getString("uid", "");

        Intent go = new Intent(ShiftActivity.this, Assign.class);
        go.putExtra("date", dateInMilli);
        go.putExtra("uid", thisUid);
        startActivity(go);
        finish();
    }
}