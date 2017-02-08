package com.example.mirza.hci_ib130206.Helper;

    import com.google.gson.Gson;
    import com.google.gson.GsonBuilder;

/**
 * Created by Developer on 11.09.2016..
 */

    public class MyGson
    {
        public static Gson build()
        {
            GsonBuilder builder = new GsonBuilder();
            return builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        }
    }


