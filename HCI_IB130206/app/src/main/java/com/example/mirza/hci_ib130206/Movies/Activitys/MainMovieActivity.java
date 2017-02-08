package com.example.mirza.hci_ib130206.Movies.Activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.mirza.hci_ib130206.Movies.Fragments.MoviesFragment;
import com.example.mirza.hci_ib130206.Movies.Fragments.TopMoviesFragment;
import com.example.mirza.hci_ib130206.R;

public class MainMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_movie);

        ActionBar actionBar = getSupportActionBar();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerMovies);


        do_setViewPager(viewPager);




    }

    private void do_setViewPager(final ViewPager viewPager) {

        final FragmentManager fm = getSupportFragmentManager();


        viewPager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public Fragment getItem(int position) {

                Fragment fragment = null;
                if (position == 0) {

                    fragment = new MoviesFragment();                }
                if (position == 1) {

                    fragment = new TopMoviesFragment();
                }

                return fragment;
            }
        });


        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.TabListener nesto = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        };

        ActionBar.Tab tab1 = actionBar.newTab().setText("Movies").setTabListener(nesto);
        actionBar.addTab(tab1);

        ActionBar.Tab tab2 = actionBar.newTab().setText("Top 10").setTabListener(nesto);
        actionBar.addTab(tab2);


        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                actionBar.setSelectedNavigationItem(position);
            }
        });


    }
}


