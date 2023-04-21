package com.example.sqlitedemo.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.sqlitedemo.fragment.FragmentHistory;
import com.example.sqlitedemo.fragment.FragmentHome;
import com.example.sqlitedemo.fragment.FragmentSearch;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private int numPage;
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        numPage = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return new FragmentHome();
            case 1:return new FragmentHistory();
            case 2:return new FragmentSearch();
        }
        return new FragmentHome();
    }

    @Override
    public int getCount() {
        return numPage;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Hom nay";
            case 1: return "Lich su";
            case 2: return "Tim kiem";
        }
        return "Hom nay";
    }
}
