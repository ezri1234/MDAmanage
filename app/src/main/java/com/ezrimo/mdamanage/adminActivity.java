package com.ezrimo.mdamanage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class adminActivity extends AppCompatActivity {
    Button logout;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        uid = getIntent().getStringExtra("uid");
        }

    public void logout(View view) {
        logout=findViewById(R.id.logoutAdmin);
        FirebaseAuth.getInstance().signOut();
        Intent go = new Intent(getApplicationContext(), SignInUpActivity.class);
        go.putExtra("uid", uid);
        startActivity(go);
        finish();
    }

    public void Users(View view) {
        Intent go = new Intent(getApplicationContext(), RecycleViewAdmin.class);
        go.putExtra("uid", uid);
        startActivity(go);
    }

    public void Calendar(View view) {
        Intent go = new Intent(getApplicationContext(), Assign.class);//for now assing but it will go to calendar
        go.putExtra("uid", uid);
        startActivity(go);
    }
}
