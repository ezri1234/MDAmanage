package com.ezrimo.mdamanage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SuccessfullSU extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successfull_su);
    }


    public void goToLI(View view) {
        Intent go = new Intent(SuccessfullSU.this, SignInUpActivity.class);
        startActivity(go);
    }
}