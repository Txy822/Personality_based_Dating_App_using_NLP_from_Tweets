package com.txy822.android_personality_based_dating_app.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeViewAdapter extends FragmentStatePagerAdapter {
List<Fragment> fragmentList=new ArrayList<>();
List<String>titleList=new ArrayList<>();


    public HomeViewAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title){
        fragmentList.add(fragment);
        titleList.add(title);
    }
    /**
     * Return the Fragment associated with a specified position.
     * @param position
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position){
//        return titleList.get(position);
        return null;
    }
}
