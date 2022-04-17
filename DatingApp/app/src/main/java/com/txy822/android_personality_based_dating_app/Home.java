package com.txy822.android_personality_based_dating_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.txy822.android_personality_based_dating_app.adapter.HomeViewAdapter;
import com.txy822.android_personality_based_dating_app.fragment.FinderFragment;
import com.txy822.android_personality_based_dating_app.fragment.SettingFragment;
import com.txy822.android_personality_based_dating_app.fragment.PersonalityFragment;
import com.txy822.android_personality_based_dating_app.fragment.ViewProfileFragment;

/**
 * Home class adds all fragments and selected by tap layout
 * using view pager and adapter
 */
public class Home extends AppCompatActivity {
    private ViewPager homeView;
    private TabLayout homeTab;
    private TabLayout homeTab2;
    private HomeViewAdapter adapter;

    /**
     * Creates home activity
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //assign home view pager adapter  and layout tap
        homeView=findViewById(R.id.home_view_page);
        homeTab=findViewById(R.id.tab);
        adapter=new HomeViewAdapter(getSupportFragmentManager());

        //adds fragments on the adapter
        adapter.addFragment(new FinderFragment(),"Finder");
        adapter.addFragment(new ViewProfileFragment(),"viewProfile");
        adapter.addFragment(new PersonalityFragment(),"Personality");
//      adapter.addFragment(new ChatFragment(),"chat");
        adapter.addFragment(new SettingFragment(),"Setting");
//      adapter.addFragment(new UpdateProfileFragment(),"updateProfile");

        homeView.setAdapter(adapter);
        homeTab.setupWithViewPager(homeView);
//      access fragments for each tap layout
        homeTab.getTabAt(0).setIcon(R.drawable.discover);
        homeTab.getTabAt(1).setIcon(R.drawable.pfile2);
        homeTab.getTabAt(2).setIcon(R.drawable.icon_personality);
//      homeTab.getTabAt(3).setIcon(R.drawable.chat);
        homeTab.getTabAt(3).setIcon(R.drawable.setting);
//      default home tap  as finder
        homeTab.selectTab(homeTab.getTabAt(0));

        homeTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}