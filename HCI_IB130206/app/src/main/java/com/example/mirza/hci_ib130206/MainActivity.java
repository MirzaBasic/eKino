package com.example.mirza.hci_ib130206;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.mirza.hci_ib130206.Helper.Sesija;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Sesija.getLogiraniKorisnik()==null){
        final Intent intent=  new Intent(this,LoginActivity.class);

        startActivity(intent);
            }

        else
        {

            final Intent intent=  new Intent(this,SlidingMenuActivity.class);
            startActivity(intent);


        }


    }


    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
