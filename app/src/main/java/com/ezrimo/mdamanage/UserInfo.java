package com.ezrimo.mdamanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.DateSorter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.Timestamp;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


public class UserInfo extends AppCompatActivity {

    TextView fullName, fullNameP, email, emailP, numberOfShifts, lastShift, nextShift;
    String uid;
    Button delete;
    Date [] dArray;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        getSupportActionBar().hide();
        fullName = findViewById(R.id.tv_name);
        fullNameP = findViewById(R.id.profile_name);
        email = findViewById(R.id.tv_email);
        emailP = findViewById(R.id.profile_Email);
        numberOfShifts = findViewById(R.id.tv_num);
        lastShift = findViewById(R.id.date_profile);
        nextShift = findViewById(R.id.next_date_profile);


        uid = getIntent().getStringExtra("uid");
        User user = getIntent().getParcelableExtra("user");
        Toast.makeText(getApplicationContext(), user.getFullName(), Toast.LENGTH_SHORT).show();

        fullNameP.setText(user.getFullName());
        fullName.setText(user.getFullName());
        email.setText(user.getEmail());
        emailP.setText(user.getEmail());

        FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        fStore.collection("Event")
                .whereEqualTo("uid", uid)
                //.orderBy("date")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //Log.d("TAG", "random");
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){//getting how much date are there
                                //Log.d("TAG", document.getData().toString());
                                count++;
                            }

                            dArray = new Date[count];
                            int i = 0;
                            for(QueryDocumentSnapshot document1 : task.getResult()){
                                dArray[i] = new Date(document1.getTimestamp("date").toDate().getTime());
                                Log.d("TAG", dArray[i].toString());
                                i++;
                            }
                            Log.d("TAG", Integer.toString(count));
                            numberOfShifts.setText(Integer.toString(count));
                            long intTime[] = new long[count];
                            for(int k = 0; k<intTime.length; k++){
                                intTime[k] = dArray[k].getTime();
                                Log.d("TAG", Long.toString(intTime[k]));
                            }
                            Log.d("TAG", "random");

                            for(int j = 0; j<intTime.length; j++){
                                Log.d("TAG", Long.toString(intTime[j]));
                            }
                            lastShift.setText(LastShift(intTime));
                            nextShift.setText(nextShift(intTime));
                        }else {
                            Log.d("TAG", task.getException().toString());
                        }
                    }
                });


    }
public static String LastShift(long[] arr){
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date lastShift;
        Arrays.sort(arr);
            long date = today.getTime();

            //Log.d("TAG", Long.toString(date));
            //Log.d("TAG", Long.toString(arr[0]));
            if(arr.length==0||date<=arr[0]-59958159400001l)
                return "No Shifts Before Today";
            for(int i = 0; i< arr.length; i++){
                if(!(date>arr[i]-(59958159400001l)&&date>arr[i+1]-(59958159400001l))){
                    lastShift = new Date(arr[i]-59958159400001l);
                    return formatter.format(lastShift);
                }
            }
        return "not valid";
}

    public static String nextShift(long[] arr){
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date lastShift;
        Arrays.sort(arr);
        long date = today.getTime();

        //Log.d("TAG", Long.toString(date));
        //Log.d("TAG", Long.toString(arr[0]));
        if(arr.length==0||date>=arr[arr.length-1]-59958159400001l)
            return "No Upcoming Shifts";
        for(int i = 1; i< arr.length; i++){
            if(!(date<arr[i-1]-(59958159400001l)&&date<arr[i]-(59958159400001l))){
                lastShift = new Date(arr[i]-59958159400001l);
                return formatter.format(lastShift);
            }
        }
        return "not valid";
    }

}


