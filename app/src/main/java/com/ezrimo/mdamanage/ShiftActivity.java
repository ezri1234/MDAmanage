package com.ezrimo.mdamanage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class ShiftActivity extends AppCompatActivity {
    TextView place1, place2, place3;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift);

        place1 = findViewById(R.id.shift1);
        place2 = findViewById(R.id.shift2);
        place3 = findViewById(R.id.shift3);
        long num = getIntent().getLongExtra("date", 62419651056000l);
        Date myDate = new Date(num);

        Log.d("TAG", myDate.toString());


    }

    public void shift1(View view) {
    }

    public void shift2(View view) {
    }

    public void shift3(View view) {
    }
}