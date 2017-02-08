package com.example.mirza.hci_ib130206.api;

import android.app.Activity;

import com.example.mirza.hci_ib130206.Helper.Connection.ApiRezultat;
import com.example.mirza.hci_ib130206.Helper.Connection.Config;
import com.example.mirza.hci_ib130206.Helper.Connection.HttpManager;
import com.example.mirza.hci_ib130206.Helper.MyRunnable;
import com.example.mirza.hci_ib130206.Helper.Sesija;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Developer on 10.10.2016..
 */
public class RezervacijeApi {

    public  class Rezervacije
    {
        public int RezervacijaID;
        public int KorisnikID;
        public int ProjekcijaID;
        public int SjedisteID;
        public String  DatumRezervacije;
        public boolean  Otkazano;


    }

        public class RezervacijeVM {


        public int RezervacijaID;
        public int ProjekcijaID;
        public String Slika;
        public double Cijena;
        public int KorisnikID;
        public String DatumRezervacije;
        public String Red;
        public int BrojSjedista;
        public String NazivFilma;
        public String TipProjekcije;
        public String Dan;
    }


    public class SjedistaVM{

        public int SjedisteID;
        public int BrojSjedista;
        public String Red;

    }


   public static void GetReservationsByUserID(Activity activity, int id, MyRunnable<ApiRezultat<List<RezervacijeVM>>> onSuccess){

       String url= Config.urlApi+"Rezervacije/Korisnik/"+id;
       Type listType = new TypeToken<ArrayList<RezervacijeVM>>() {}.getType();
       HttpManager.getList(url,activity,listType,onSuccess);

   }


    public static void GetZauzetaSjedistaByProjekcijaID(Activity activity, int id, MyRunnable<ApiRezultat<List<SjedistaVM>>> onSuccess){

        String url= Config.urlApi+"Sjedista/Zauzeta/"+id;
        Type listType = new TypeToken<ArrayList<SjedistaVM>>() {}.getType();
        HttpManager.getList(url,activity,listType,onSuccess);

    }



    public static void SetRezervaciju(Activity activity, int SjedisteID, MyRunnable<ApiRezultat<Rezervacije>> onSuccess){

        String url= Config.urlApi+"Rezervacije";
        Map<String,String> parameter=new HashMap<String,String>();


        parameter.put("KorisnikID", String.valueOf(Sesija.getLogiraniKorisnik().KorisnikID));
        parameter.put("ProjekcijaID", String.valueOf(Sesija.getOdabranaProjekcija().ProjekcijaID));
        parameter.put("SjedisteID", String.valueOf(SjedisteID));
        HttpManager.post(url,parameter,activity,onSuccess);

    }


    public static void Delete(int id, Activity activity, final MyRunnable<ApiRezultat<Rezervacije>> onSuccess) {

        String url = Config.urlApi + "DeleteRezervacije/"+id;

        HttpManager.delete(url,activity,onSuccess);


    }

}



