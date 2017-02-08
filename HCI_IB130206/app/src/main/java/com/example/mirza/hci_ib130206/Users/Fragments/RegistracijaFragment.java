package com.example.mirza.hci_ib130206.Users.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mirza.hci_ib130206.Helper.Connection.ApiRezultat;
import com.example.mirza.hci_ib130206.Helper.MyApp;
import com.example.mirza.hci_ib130206.Helper.MyRunnable;
import com.example.mirza.hci_ib130206.R;
import com.example.mirza.hci_ib130206.api.KorisnikApi;

/**
 * Created by Developer on 13.10.2016..
 */
public class RegistracijaFragment extends DialogFragment {

public RegistracijaFragment(){


}
    public static void openFragmentAsDialog(FragmentActivity activity, MyRunnable<KorisnikApi.KorisnikVM> onSuccess){

        final RegistracijaFragment fragment=new RegistracijaFragment();
        final Bundle args=new Bundle();
        args.putSerializable("MY_RUNNABLE", onSuccess);
        fragment.setArguments(args);
        FragmentManager fm=activity.getSupportFragmentManager();
        fragment.show(fm,"nesto");





    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.stavke_registracije, container, false);

       getDialog().setTitle("Registration");
        Button btnRegistration = (Button) view.findViewById(R.id.btnRegistration);

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_registration(view);
            }
        });


        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_CancelRegistration();
            }
        });

        return view;

    }

    private void do_CancelRegistration() {

        this.dismiss();
    }

    private void do_registration(final View view) {

        EditText txtUsername = (EditText) view.findViewById(R.id.txtUsername);
        EditText txtFirstName = (EditText) view.findViewById(R.id.txtFirstName);
        EditText txtLastName = (EditText) view.findViewById(R.id.txtLastName);
        EditText txtEmail = (EditText) view.findViewById(R.id.txtEmail);
        EditText txtPassword = (EditText) view.findViewById(R.id.txtPassword);
        EditText txtPasswordRepeate = (EditText) view.findViewById(R.id.txtPasswordRepeate);

String pass=txtPassword.getText().toString();
        String passRepeat=txtPasswordRepeate.getText().toString();
        String firstName=txtFirstName.getText().toString();
        String lastName=txtLastName.getText().toString();
        String username=txtUsername.getText().toString();
        String email=txtEmail.getText().toString();

        if(!pass.contentEquals(passRepeat)){

            Toast.makeText(MyApp.getContext(),"Password repeate must be same as password!",Toast.LENGTH_LONG).show();
        }

        else if (firstName.contentEquals("")||lastName.contentEquals("")||email.contentEquals("")||username.contentEquals("")){


            Toast.makeText(MyApp.getContext(),"All fields are reqired!",Toast.LENGTH_LONG).show();
        }


            else {


            final KorisnikApi.KorisnikVM k=new KorisnikApi.KorisnikVM();
            k.KorisnickoIme=txtUsername.getText().toString();
            k.Ime=txtFirstName.getText().toString();
            k.Prezime=txtLastName.getText().toString();
            k.Email=txtEmail.getText().toString();
            k.Lozinka=txtPassword.getText().toString();

            KorisnikApi.Registrate(k, getActivity(), new MyRunnable<ApiRezultat<KorisnikApi.KorisnikVM>>() {
                @Override
                public void run(ApiRezultat<KorisnikApi.KorisnikVM> rezultat) {


                    Toast.makeText(MyApp.getContext(), "Registration successfull!", Toast.LENGTH_LONG).show();
                    final MyRunnable<KorisnikApi.KorisnikVM> onSuccess = (MyRunnable<KorisnikApi.KorisnikVM>) getArguments().getSerializable("MY_RUNNABLE");

                    onSuccess.run(k);

                    dismiss();


                }
            });
        }


    }
}
