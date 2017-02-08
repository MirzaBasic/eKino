package com.example.mirza.hci_ib130206.api;

import android.app.Activity;

import com.example.mirza.hci_ib130206.Helper.Connection.ApiRezultat;
import com.example.mirza.hci_ib130206.Helper.Connection.Config;
import com.example.mirza.hci_ib130206.Helper.Connection.HttpManager;
import com.example.mirza.hci_ib130206.Helper.MyRunnable;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Developer on 21.10.2016..
 */
public class NovostiApi {


    public static class Novosti{
        public int NovostID ;
        public String Slika ;
        public String Naslov ;
        public String Sadrzaj ;
        public boolean Aktivno ;
        public String DatumKreiranja ;


    }

    public static void GetAllNovosti(Activity activity,MyRunnable<ApiRezultat<List<Novosti>>> onSuccess){

        String url= Config.urlApi+"Novosti";

        Type listType=new TypeToken<ArrayList<Novosti>>(){}.getType();
        HttpManager.getList(url, activity,listType, onSuccess);

    }

}
