package com.example.mirza.hci_ib130206.Users.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mirza.hci_ib130206.Helper.Connection.ApiRezultat;
import com.example.mirza.hci_ib130206.Helper.MyApp;
import com.example.mirza.hci_ib130206.Helper.MyRunnable;
import com.example.mirza.hci_ib130206.Helper.Sesija;
import com.example.mirza.hci_ib130206.R;
import com.example.mirza.hci_ib130206.api.KorisnikApi;

/**
 * Created by Developer on 17.10.2016..
 */
public class UserProfileFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.user_profile, container, false);

        Button btnChangeInfo = (Button) view.findViewById(R.id.btnChangeInfo);

        btnChangeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_btn_changeInfo_Click(view);
            }
        });



        Button btnSaveInfo = (Button) view.findViewById(R.id.btnSaveInfo);
        btnSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_btnSaveChanges_Click(view);
            }
        });
        Button btnCancel = (Button) view.findViewById(R.id.btnCancelSave);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_btnCancel_Click(view);
            }
        });

        do_setInfo(view);


        return view;
    }

    private void do_btnCancel_Click(View view) {
        EditText firstName = (EditText) view.findViewById(R.id.txtFirstName);
        EditText lastName = (EditText) view.findViewById(R.id.txtLastName);
        EditText username = (EditText) view.findViewById(R.id.txtUsername);
        EditText email = (EditText) view.findViewById(R.id.txtEmail);
        EditText password = (EditText) view.findViewById(R.id.txtPassword);


        email.setEnabled(false);
        firstName.setEnabled(false);
        lastName.setEnabled(false);
        username.setEnabled(false);
        password.setEnabled(false);


        final LinearLayout layoutButtons = (LinearLayout) view.findViewById(R.id.layoutButtons);
        layoutButtons.setVisibility(View.INVISIBLE);
        final LinearLayout layoutButtonChangeInfo = (LinearLayout) view.findViewById(R.id.layoutButtonChange);
        layoutButtonChangeInfo.setVisibility(View.VISIBLE);
    }

    private void do_btnSaveChanges_Click(final View view) {


        final EditText firstName = (EditText) view.findViewById(R.id.txtFirstName);
        final EditText lastName = (EditText) view.findViewById(R.id.txtLastName);
        final EditText username = (EditText) view.findViewById(R.id.txtUsername);
        final EditText email = (EditText) view.findViewById(R.id.txtEmail);
        final EditText password = (EditText) view.findViewById(R.id.txtPassword);

        final KorisnikApi.KorisnikVM korisnik=new KorisnikApi.KorisnikVM();
        korisnik.KorisnikID=Sesija.getLogiraniKorisnik().KorisnikID;
        korisnik.Ime=firstName.getText().toString();
        korisnik.Prezime=lastName.getText().toString();
        korisnik.KorisnickoIme=username.getText().toString();
        korisnik.Email=email.getText().toString();
        korisnik.Lozinka=password.getText().toString();



        KorisnikApi.UpdateInfo(korisnik,getActivity(), new MyRunnable<ApiRezultat<KorisnikApi.KorisnikVM>>() {
            @Override
            public void run(ApiRezultat<KorisnikApi.KorisnikVM> rezultat) {
                if (!rezultat.isError) {


                 do_btnCancel_Click(view);
                    Sesija.setLogiraniKorisnik(korisnik);
                    Toast.makeText(MyApp.getContext(), "Info update successfull! ", Toast.LENGTH_LONG).show();



                }
                else{

                    Toast.makeText(MyApp.getContext(),"Server Error Connection: "+rezultat.porukaError,Toast.LENGTH_LONG).show();

                }


            }
        });


    }

    private void do_btn_changeInfo_Click(View view) {

        EditText firstName = (EditText) view.findViewById(R.id.txtFirstName);
        EditText lastName = (EditText) view.findViewById(R.id.txtLastName);
        EditText username = (EditText) view.findViewById(R.id.txtUsername);
        EditText password = (EditText) view.findViewById(R.id.txtPassword);
        EditText email = (EditText) view.findViewById(R.id.txtEmail);

        email.setEnabled(true);
        firstName.setEnabled(true);
        lastName.setEnabled(true);
        username.setEnabled(true);
        password.setEnabled(true);


        final LinearLayout layoutButtons = (LinearLayout) view.findViewById(R.id.layoutButtons);
        layoutButtons.setVisibility(View.VISIBLE);
        final LinearLayout layoutButtonChangeInfo = (LinearLayout) view.findViewById(R.id.layoutButtonChange);
        layoutButtonChangeInfo.setVisibility(View.INVISIBLE);

    }

    private void do_setInfo(View view) {

        EditText firstName = (EditText) view.findViewById(R.id.txtFirstName);
        EditText lastName = (EditText) view.findViewById(R.id.txtLastName);
        EditText username = (EditText) view.findViewById(R.id.txtUsername);
        EditText password = (EditText) view.findViewById(R.id.txtPassword);
        EditText email = (EditText) view.findViewById(R.id.txtEmail);

        firstName.setText(Sesija.getLogiraniKorisnik().Ime);
        lastName.setText(Sesija.getLogiraniKorisnik().Prezime);
        username.setText(Sesija.getLogiraniKorisnik().KorisnickoIme);
        password.setText(Sesija.getLogiraniKorisnik().Lozinka);
        email.setText(Sesija.getLogiraniKorisnik().Email);


    }
}
