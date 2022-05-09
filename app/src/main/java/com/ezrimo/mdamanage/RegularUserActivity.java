package com.ezrimo.mdamanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegularUserActivity extends AppCompatActivity {
    Button logout;
    Button myInfo, calendar;
    User user=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_user);
        logout = findViewById(R.id.logoutUser);
        myInfo = findViewById(R.id.myInfo);
        calendar = findViewById(R.id.calender);

        myInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = getIntent().getStringExtra("uid");
                FirebaseFirestore fStore = FirebaseFirestore.getInstance();

                fStore.collection("User").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user = new User(documentSnapshot.getString("fullName"), documentSnapshot.getString("email"), 0);
                        Intent go = new Intent(RegularUserActivity.this, UserInfo.class);
                        go.putExtra("user", user);
                        startActivity(go);
                    }
                });

            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(getApplicationContext(), Calendar.class);
                startActivity(go);
            }
        });

    }

    public void logout(View view) {
        logout=findViewById(R.id.logoutUser);
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), SignInUpActivity.class));
        finish();
    }
}
