package com.ezrimo.mdamanage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class RegularUserActivity extends AppCompatActivity {
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_user);
        logout=findViewById(R.id.logoutUser);
    }

    public void logout(View view) {
        logout=findViewById(R.id.logoutUser);
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), SignInUpActivity.class));
        finish();
    }
}
