package com.ezrimo.mdamanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class SignInUpActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    Button loginButton, signupButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_up);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("login"));
        tabLayout.addTab(tabLayout.newTab().setText("Sign UP"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        LogInAdapter adapter = new LogInAdapter(getSupportFragmentManager(), this, tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        signupButton = findViewById(R.id.signupB);
        loginButton = findViewById(R.id.loginB);
    }

    /**
     * setting the menu
     * @param menu the menu
     * @return if there is a menu returns true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    /**
     * going to th wanted activity
     * @param item the item clicked on
     * @return default
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if(itemId == R.id.metronome){
            Intent go = new Intent(getApplicationContext(), Metronome.class);
            startActivity(go);
            finish();
        }
        return true;
    }
}