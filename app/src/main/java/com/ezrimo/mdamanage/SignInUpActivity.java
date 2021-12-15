package com.ezrimo.mdamanage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class SignInUpActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

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
    }
}