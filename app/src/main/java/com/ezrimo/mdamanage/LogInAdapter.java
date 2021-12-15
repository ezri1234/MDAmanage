package com.ezrimo.mdamanage;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class LogInAdapter extends FragmentPagerAdapter {
    private Context context;
    int totalTabs;

    public LogInAdapter (FragmentManager fm, Context context, int totalTabs){
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }


    public Fragment getItem(int position){
        switch (position){
            case 0:
                LoginTabFragment loginTabFragment = new LoginTabFragment();
                return loginTabFragment;
            case 1:
                SignUpTabFragment signUpTabFragment = new SignUpTabFragment();
                return signUpTabFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
