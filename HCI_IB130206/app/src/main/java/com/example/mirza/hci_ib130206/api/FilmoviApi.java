package com.example.mirza.hci_ib130206.api;

import android.app.Activity;

import com.example.mirza.hci_ib130206.Helper.Connection.ApiRezultat;
import com.example.mirza.hci_ib130206.Helper.Connection.Config;
import com.example.mirza.hci_ib130206.Helper.Connection.HttpManager;
import com.example.mirza.hci_ib130206.Helper.MyRunnable;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Developer on 29.09.2016..
 */
public class FilmoviApi {
    public static class FilmoviVM implements Serializable{

        public int FilmID;
        public String Naziv;
        public String Glumci;
        public String Opis ;
        public boolean Akrivan;
        public double IMDBOcjena;
        public double Ocjena;
        public String Slika;
        public List<KomentariVM> Komentari;
        public List<OcjeneVM> Ocjene;
        public List<Zanrovi> Zanrovi;
        public List<DaniVM> Dani;
    }
    public static class Filmovi implements Serializable{

        public int FilmID;
        public String Naziv;
        public String Glumci;
        public String Opis ;
        public boolean Akrivan;
        public double IMDBOcjena;
        public double Ocjena;
        public String Slika;

    }
    public static class OcjeneVM implements Serializable {

        public int Ocjena;
        public String DatumKreiranja;
        public String KorisnickoIme;
    }

    public static class DaniVM implements Serializable{

        public int DanID;
        public String Naziv;
        public List<ProjekcijeVM> Projekcije;
    }


    public static class ProjekcijeVM implements Serializable
    {
        public int ProjekcijaID;
        public int FilmID ;
        public double Cijena;
        public String Vrijeme;
        public String NazivFilma;
        public String TipProjekcije;
        public String Dan;
    }


    /**
     * Created by Developer on 03.10.2016..
     */
    public static class Zanrovi implements  Serializable{
        public int ZanrID;
        public String Naziv;
    }
    /**
     * Created by Developer on 03.10.2016..
     */
    public static class KomentariVM   implements Serializable{
        public int KomentarID;
        public String Komentar;
        public String KorisnickoIme;
        public String DatumKreiranja;

    }
public static class Komentari implements Serializable {
    public int KomentarID;
    public String Komentar;
    public int KorisnikID;
    public int FilmID;
    public int KomentarKomentaraID;
    public String DatumKreiranja;
}


    public static void getMovies(Activity activity,String Naziv, MyRunnable<ApiRezultat<List<FilmoviVM>>> onSuccess){

        String url= Config.urlApi+"Filmovi/Naziv/"+Naziv;
        Type listType = new TypeToken<ArrayList<FilmoviVM>>() {}.getType();
        HttpManager.getList1(url,activity,listType, onSuccess);


    }
    public static void getTopMovies(Activity activity, MyRunnable<ApiRezultat<List<FilmoviVM>>> onSuccess){

        String url= Config.urlApi+"Filmovi/Top10";
        Type listType = new TypeToken<ArrayList<FilmoviVM>>() {}.getType();
        HttpManager.getList1(url,activity,listType, onSuccess);


    }
    public static void getMovieDetails(Activity activity, int id, MyRunnable<ApiRezultat<FilmoviVM>> onSuccess){

        String url= Config.urlApi+"Filmovi/"+id;
        HttpManager.get(url,activity,FilmoviVM.class, onSuccess);


    }

    public static void postComment(FilmoviApi.Komentari komentar, Activity activity, final MyRunnable<ApiRezultat<Komentari>> onSuccess) {

        String url = Config.urlApi + "Komentari";

        Map<String,String> parameters= new HashMap<String ,String>();
        parameters.put("Komentar",komentar.Komentar);
        parameters.put("KorisnikID",String.valueOf(komentar.KorisnikID));
        parameters.put("FilmID",String.valueOf(komentar.FilmID));
        parameters.put("KomentarKomentaraID",String.valueOf(komentar.KomentarKomentaraID));



        HttpManager.post(url,parameters,activity,onSuccess);


    }





}
