package com.example.mirza.hci_ib130206;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mirza.hci_ib130206.Helper.Connection.ApiRezultat;
import com.example.mirza.hci_ib130206.Helper.MyApp;
import com.example.mirza.hci_ib130206.Helper.MyRunnable;
import com.example.mirza.hci_ib130206.Helper.Sesija;
import com.example.mirza.hci_ib130206.Users.Fragments.RegistracijaFragment;
import com.example.mirza.hci_ib130206.api.KorisnikApi;


public class LoginActivity extends AppCompatActivity {

    protected Button signinButton;
    protected Button registrationButton;
    protected EditText username;
    protected EditText password;
    String poruka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        signinButton = (Button) findViewById(R.id.SigninBtn);
        registrationButton = (Button) findViewById(R.id.RegistrBtn);
        username = (EditText) findViewById(R.id.UsernameInput);
        password = (EditText) findViewById(R.id.PasswordInput);


        signinButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                do_Login_Btn_OnClick();


            }
        });

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_registration_Btn_OnClick();
            }
        });



    }

    private void do_registration_Btn_OnClick() {

   RegistracijaFragment.openFragmentAsDialog(this, new MyRunnable<KorisnikApi.KorisnikVM>() {
         @Override
         public void run(KorisnikApi.KorisnikVM result) {

             username.setText(result.KorisnickoIme);
             password.setText(result.Lozinka);
             do_Login_Btn_OnClick();
         }
     });

    }


    private void do_Login_Btn_OnClick() {

        KorisnikApi.Provjera( username.getText().toString(), password.getText().toString(),LoginActivity.this, new MyRunnable<ApiRezultat<KorisnikApi.KorisnikVM>>() {
            @Override
            public void run(ApiRezultat<KorisnikApi.KorisnikVM> rezultat) {


                if (!rezultat.isError) {
                    rezultat.value.Lozinka=password.getText().toString();
                    Sesija.setLogiraniKorisnik(rezultat.value);
                    Toast.makeText(MyApp.getContext(), "Welcome " + rezultat.value.Ime.toString() + " " + rezultat.value.Prezime.toString(), Toast.LENGTH_LONG).show();

                    final Intent intent = new Intent(MyApp.getContext(), SlidingMenuActivity.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(MyApp.getContext(), "Wrong username or password!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }




    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
