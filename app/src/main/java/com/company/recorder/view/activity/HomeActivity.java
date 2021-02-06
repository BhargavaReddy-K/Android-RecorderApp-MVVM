package com.company.recorder.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.company.recorder.R;
import com.company.recorder.model.adapter.ViewPagerAdapter;
import com.company.recorder.model.interfaces.IMessages;
import com.company.recorder.model.utils.Messages;


public class HomeActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager tabsViewPager;
    private long backPressed;
    private static final int TIME_INTERVAL = 2000;
    private IMessages messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        try {
            //views
            tabLayout = findViewById(R.id.tabLayout);
            tabsViewPager = findViewById(R.id.view_pagerTabs);

            messages = new Messages(this);

            manageResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onBackPressed() {
        if (backPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            finishAffinity();
            return;
        } else {
            messages.toast(getString(R.string.exit_info));
        }
        backPressed = System.currentTimeMillis();
    }

    private void manageResponse() {
        try {
            //add tabs
            tabLayout.removeAllTabs();
            tabLayout.addTab(tabLayout.newTab().setText("Record"));
            tabLayout.addTab(tabLayout.newTab().setText("Music"));
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
            tabsViewPager.setAdapter(viewPagerAdapter);
            tabsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    tabsViewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }

            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
