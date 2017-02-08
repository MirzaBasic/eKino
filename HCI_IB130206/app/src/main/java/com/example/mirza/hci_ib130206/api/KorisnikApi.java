package com.example.mirza.hci_ib130206.api;

import android.app.Activity;

import com.example.mirza.hci_ib130206.Helper.Connection.ApiRezultat;
import com.example.mirza.hci_ib130206.Helper.Connection.Config;
import com.example.mirza.hci_ib130206.Helper.Connection.HttpManager;
import com.example.mirza.hci_ib130206.Helper.MyRunnable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Developer on 08.09.2016..
 */
public class KorisnikApi {

    public static class KorisnikVM{


        public int KorisnikID;
        public String KorisnickoIme;
        public String Ime;
        public String Prezime;
        public String Lozinka;
        public String Email;
        public String Spol;
    }


    public static class Korisnik implements Serializable{


        public int KorisnikID;
        public String KorisnickoIme;
        public String Ime;
        public String Prezime;
        public String Lozinka;
        public String Email;
        public String Spol;
    }


    public static void Provjera( String   username, String password, Activity activity, final MyRunnable<ApiRezultat<KorisnikVM>> onSuccess) {

        String url = Config.urlApi + "Autentifikacija/Provjera/" + username + "/" + password;
        HttpManager.get(url, activity, KorisnikVM.class, onSuccess);


    }


    public static void Registrate( KorisnikVM korisnik, Activity activity, final MyRunnable<ApiRezultat<KorisnikVM>> onSuccess) {

        String url = Config.urlApi + "Korisnici";

        Map<String,String> parameters= new HashMap<String ,String>();
        parameters.put("KorisnickoIme",korisnik.KorisnickoIme);
        parameters.put("Ime",korisnik.Ime);
        parameters.put("Prezime",korisnik.Prezime);
        parameters.put("Lozinka",korisnik.Lozinka);
        parameters.put("Email",korisnik.Email);
        parameters.put("Spol","");


        HttpManager.post(url,parameters,activity,onSuccess);


    }
    public static void UpdateInfo(KorisnikVM korisnik, Activity activity, final MyRunnable<ApiRezultat<KorisnikVM>> onSuccess) {

        String url = Config.urlApi + "UpdateKorisnici";

        Map<String,String> parameters= new HashMap<String ,String>();
        parameters.put("id",String.valueOf(korisnik.KorisnikID));
        parameters.put("KorisnikID",String.valueOf(korisnik.KorisnikID));
        parameters.put("KorisnickoIme",korisnik.KorisnickoIme);
        parameters.put("Ime",korisnik.Ime);
        parameters.put("Prezime",korisnik.Prezime);
        parameters.put("Lozinka",korisnik.Lozinka);
        parameters.put("Email",korisnik.Email);
        parameters.put("Spol","/");


        HttpManager.put(url,parameters,activity,onSuccess);


    }








}




