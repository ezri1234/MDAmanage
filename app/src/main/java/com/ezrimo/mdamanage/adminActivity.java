package com.ezrimo.mdamanage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class adminActivity extends AppCompatActivity {
    Button logout;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("DE", "inAdmin");
        setContentView(R.layout.activity_admin);
        uid = getIntent().getStringExtra("uid");
        }

    /**
     * log outs the user and goes back to SignInUpActivity
     * @param view the logout Button
     */
    public void logout(View view) {
        logout=findViewById(R.id.logoutAdmin);
        FirebaseAuth.getInstance().signOut();
        Intent go = new Intent(getApplicationContext(), SignInUpActivity.class);
        go.putExtra("uid", uid);
        startActivity(go);
        finish();
    }

    /**
     * goes to all users RecyclerView
     * @param view the Users Button
     */
    public void Users(View view) {
        Intent go = new Intent(getApplicationContext(), ChooseUser.class);
        go.putExtra("uid", uid);
        startActivity(go);
    }

    /**
     * goes to the calendar activity where he can assign a user to a shift
     * @param view the calendar Button
     */
    public void Calendar(View view) {
        Intent go = new Intent(getApplicationContext(), Calendar.class);
        go.putExtra("uid", uid);
        startActivity(go);
    }
}
