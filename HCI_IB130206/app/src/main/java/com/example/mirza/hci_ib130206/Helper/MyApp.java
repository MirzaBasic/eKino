package com.example.mirza.hci_ib130206.Helper;



        import android.app.Application;
        import android.content.Context;

/**
 * Created by Developer on 11.09.2016..
 */

public class MyApp extends Application
{

    public static Context getContext()
    {
        return context;
    }

    private static Context context;

    @Override
    public void onCreate()
    {
        super.onCreate();

        context = getApplicationContext();
        //custom code
    }

}
