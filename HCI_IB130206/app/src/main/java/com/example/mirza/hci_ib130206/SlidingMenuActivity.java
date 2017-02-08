package com.example.mirza.hci_ib130206;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mirza.hci_ib130206.Helper.Sesija;
import com.example.mirza.hci_ib130206.Movies.Fragments.MoviesFragment;
import com.example.mirza.hci_ib130206.Movies.Fragments.TopMoviesFragment;
import com.example.mirza.hci_ib130206.Novosti.Fragments.NovostiFragment;
import com.example.mirza.hci_ib130206.Users.Fragments.UserProfileFragment;
import com.example.mirza.hci_ib130206.Users.Fragments.UserReservationsFragment;

public class SlidingMenuActivity extends AppCompatActivity
{
    private DrawerLayout drawer_Layout;
    private ListView left_drawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Fragment f=null;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_menu);



        drawer_Layout = (DrawerLayout) findViewById(R.id.drawerLayout);




        left_drawer = (ListView) findViewById(R.id.leftDrawer);


        // set up the drawer's list view with items and click listener
        left_drawer.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item,R.id.textView1, getNaslovi()));


        left_drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                               @Override
                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectItem(position);
                    }
                });


                // enable ActionBar app icon to behave as action to toggle nav drawer
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawer_Layout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_open  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {

                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle("Cinema");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

        };
        drawer_Layout.setDrawerListener(mDrawerToggle);

        selectItem(0);
    }

    private void selectItem(int position) {






            if (position == 0){
                f = new NovostiFragment();
        getSupportActionBar().setTitle("News");
        invalidateOptionsMenu();

    }

        if (position == 1){
            f = new MoviesFragment();
            getSupportActionBar().setTitle("Movies");
            invalidateOptionsMenu();

        }
        if (position == 2) {
            f = new TopMoviesFragment();
            getSupportActionBar().setTitle("Top 10 Movies");
            invalidateOptionsMenu();
        }
            if (position == 3){
            f = new UserReservationsFragment();
            getSupportActionBar().setTitle("My reservations");
            invalidateOptionsMenu();

        }
        if (position == 4){
            f = new UserProfileFragment();
            getSupportActionBar().setTitle("Profile");
            invalidateOptionsMenu();

        }

        if (position == 5)
        {
            Sesija.setLogiraniKorisnik(null);
        final Intent intent=  new Intent(this,MainActivity.class);
        startActivity(intent);

        }






        if (f != null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, f);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            // update selected item and title, then close the drawer
            left_drawer.setItemChecked(position, true);
            setTitle(getNaslovi()[position]);
            drawer_Layout.closeDrawer(left_drawer);
        }
    }


    protected String[] getNaslovi()
    {
        return new String[]{
                "News",
                "Movies",
                "Top 10",
                "Reservations",
                "Profile",
                "Logout"

        };
    };



    public void onBackPressed() {
        moveTaskToBack(true);
    }




}
