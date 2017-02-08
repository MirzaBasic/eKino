package com.example.mirza.hci_ib130206.Helper;

import android.content.SharedPreferences;

import com.example.mirza.hci_ib130206.api.FilmoviApi;
import com.example.mirza.hci_ib130206.api.KorisnikApi;

public class Sesija {


    private static final String PREFS_NAME = "SharedPreferencesFile";

    public static KorisnikApi.KorisnikVM getLogiraniKorisnik(){

        SharedPreferences setings=MyApp.getContext().getSharedPreferences(PREFS_NAME,0);

        String str=setings.getString("logiraniKorisnikJson","");

        if(str.length()==0)
            return null;
       KorisnikApi.KorisnikVM logiranikorisnik= MyGson.build().fromJson(str, KorisnikApi.KorisnikVM.class);
        return logiranikorisnik;




    }

    public static void setLogiraniKorisnik( KorisnikApi.KorisnikVM logiraniKorisnik){

        String str =MyGson.build().toJson(logiraniKorisnik);
        SharedPreferences setings=MyApp.getContext().getSharedPreferences(PREFS_NAME,0);
        SharedPreferences.Editor editor=setings.edit();
        editor.putString("logiraniKorisnikJson",str);
        editor.commit();





    }


    public static void setOdabraniFilm(FilmoviApi.FilmoviVM odabraniFilm) {

        String str =MyGson.build().toJson(odabraniFilm);
        SharedPreferences setings=MyApp.getContext().getSharedPreferences(PREFS_NAME,0);
        SharedPreferences.Editor editor=setings.edit();
        editor.putString("odabraniFilmJson",str);
        editor.commit();


    }


    public static FilmoviApi.FilmoviVM getOdabraniFilm() {
        SharedPreferences setings=MyApp.getContext().getSharedPreferences(PREFS_NAME,0);

        String str=setings.getString("odabraniFilmJson","");

        if(str.length()==0)
            return null;
        FilmoviApi.FilmoviVM odabraniFilm= MyGson.build().fromJson(str,FilmoviApi.FilmoviVM.class);
        return odabraniFilm;



    }

    public static FilmoviApi.ProjekcijeVM getOdabranaProjekcija() {
        SharedPreferences setings=MyApp.getContext().getSharedPreferences(PREFS_NAME,0);

        String str=setings.getString("odabranaProjekcijaJson","");

        if(str.length()==0)
            return null;
        FilmoviApi.ProjekcijeVM odabraniFilm= MyGson.build().fromJson(str,FilmoviApi.ProjekcijeVM.class);
        return odabraniFilm;



    }

    public static void setOdabranaProjekcija(FilmoviApi.ProjekcijeVM odabranaProjekcija) {

        String str =MyGson.build().toJson(odabranaProjekcija);
        SharedPreferences setings=MyApp.getContext().getSharedPreferences(PREFS_NAME,0);
        SharedPreferences.Editor editor=setings.edit();
        editor.putString("odabranaProjekcijaJson",str);
        editor.commit();


    }


}
