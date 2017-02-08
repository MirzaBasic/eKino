package com.example.mirza.hci_ib130206.Helper.Connection;

import android.app.Activity;
import android.app.ProgressDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mirza.hci_ib130206.Helper.MyRunnable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by Developer on 10.09.2016..
 */
public class HttpManager {

    public static  <T> void get(String url, final Activity activity, final Class<T> outputType, final MyRunnable<ApiRezultat<T>> onSuccess) {
        final ProgressDialog dialogLoading = new ProgressDialog(activity);


        dialogLoading.setTitle("Please wait!");
        dialogLoading.setMessage("Loading");
        dialogLoading.show();



        RequestQueue queue = Volley.newRequestQueue(activity);


        final ApiRezultat<T> rezultat = new ApiRezultat<T>();
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new GsonBuilder().create();
                        T x = gson.fromJson(response.toString(),outputType);
                        rezultat.value=x;
                        rezultat.isError=false;
                        dialogLoading.dismiss();
                        onSuccess.run(rezultat);

                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        dialogLoading.dismiss();
                        rezultat.isError = true;
                        rezultat.porukaError = error.getMessage();
                        onSuccess.run(rezultat);

                    }

                }

        );
        queue.add(jsonObjectRequest);


    }



    public static  <T> void getList(String url, final Activity activity, final Type listType, final MyRunnable<ApiRezultat<List<T>>> onSuccess) {
        final ProgressDialog dialogLoading = new ProgressDialog(activity);

        dialogLoading.setTitle("Please wait!");
        dialogLoading.setMessage("Loading");
        dialogLoading.show();



        RequestQueue queue = Volley.newRequestQueue(activity);


        final ApiRezultat<List<T>> rezultat = new ApiRezultat<List<T>>();

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson = new GsonBuilder().create();

                        List<T>  x = gson.fromJson(response.toString(),listType);
                        rezultat.value=x;
                        rezultat.isError=false;
                        dialogLoading.dismiss();
                       onSuccess.run(rezultat);
                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        dialogLoading.dismiss();
                        rezultat.isError = true;
                        rezultat.porukaError = error.getMessage();
                        onSuccess.run(rezultat);

                    }

                }

        );
        queue.add(jsonArrayRequest);


    }


    public static  <T> void getList1(String url, final Activity activity, final Type listType, final MyRunnable<ApiRezultat<List<T>>> onSuccess) {

        RequestQueue queue = Volley.newRequestQueue(activity);


        final ApiRezultat<List<T>> rezultat = new ApiRezultat<List<T>>();

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson = new GsonBuilder().create();

                        List<T>  x = gson.fromJson(response.toString(),listType);
                        rezultat.value=x;
                        rezultat.isError=false;

                        onSuccess.run(rezultat);
                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        rezultat.isError = true;
                        rezultat.porukaError = error.getMessage();
                        onSuccess.run(rezultat);

                    }

                }

        );
        queue.add(jsonArrayRequest);


    }








    public static  <T> void post(String url,final Map<String,String> params, final Activity activity, final MyRunnable<ApiRezultat<T>> onSuccess) {

        final ProgressDialog dialogLoading = new ProgressDialog(activity);
        dialogLoading.setTitle("Please wait!");
        dialogLoading.setMessage("Loading");
        dialogLoading.show();


        final RequestQueue queue = Volley.newRequestQueue(activity);


        final ApiRezultat<T> rezultat = new ApiRezultat<T>();

   JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.POST, url, new JSONObject (params), new Response.Listener<JSONObject>() {
       @Override
       public void onResponse(JSONObject response) {
           dialogLoading.dismiss();
           rezultat.isError=false;
           onSuccess.run(rezultat);
       }
   }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

          dialogLoading.dismiss();

                rezultat.isError=true;
                 rezultat.porukaError=error.getMessage();
                onSuccess.run(rezultat);

        }});


       queue.add(jsonObjectRequest);

    }



    public static  <T> void delete(String url, final Activity activity, final MyRunnable<ApiRezultat<T>> onSuccess) {

        final ProgressDialog dialogLoading = new ProgressDialog(activity);
        dialogLoading.setTitle("Please wait!");
        dialogLoading.setMessage("Loading");
        dialogLoading.show();


        final RequestQueue queue = Volley.newRequestQueue(activity);


        final ApiRezultat<T> rezultat = new ApiRezultat<T>();



        final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.DELETE,url,

                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                rezultat.isError=false;
                dialogLoading.dismiss();
                onSuccess.run(rezultat);
            }
        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        dialogLoading.dismiss();
                        rezultat.isError = true;
                        rezultat.porukaError = error.getMessage();
                        onSuccess.run(rezultat);

                    }

                });








queue.add(jsonObjectRequest);

    }


    public static  <T> void put(String url,final Map<String,String> params, final Activity activity, final MyRunnable<ApiRezultat<T>> onSuccess) {

        final ProgressDialog dialogLoading = new ProgressDialog(activity);
        dialogLoading.setTitle("Please wait!");
        dialogLoading.setMessage("Loading");
        dialogLoading.show();


        final RequestQueue queue = Volley.newRequestQueue(activity);


        final ApiRezultat<T> rezultat = new ApiRezultat<T>();

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url,
               new JSONObject(params),new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                rezultat.isError=false;
                dialogLoading.dismiss();
                onSuccess.run(rezultat);
            }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        rezultat.isError=true;
                        rezultat.porukaError=error.getMessage();
                        dialogLoading.dismiss();
                        onSuccess.run(rezultat);
                    }
                });






                queue.add(jsonObjectRequest);

    }

}


