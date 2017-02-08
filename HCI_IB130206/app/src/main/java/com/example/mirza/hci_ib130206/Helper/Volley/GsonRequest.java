package com.example.mirza.hci_ib130206.Helper.Volley;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.example.mirza.hci_ib130206.Helper.MyGson;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Developer on 28.09.2016..
 */
public class GsonRequest<T> extends JsonRequest<T> {
    private final Gson gson = MyGson.build();
    private final Class<T> clazz;
    private final Map<String, String> headers;
    private final ThreadLocal<Listener<T>> listener;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param get
     * @param url URL of the request to make
     * @param clazz Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    public GsonRequest(String url, Class<T> clazz, Map<String, String> headers, String json,
                       final Listener<T> listener, Response.ErrorListener errorListener, int get) {
        super(get, url,json,listener, errorListener);
        this.clazz = clazz;
        this.headers = headers;
        this.listener = new ThreadLocal<Listener<T>>() {
            @Override
            protected Listener<T> initialValue() {
                return listener;
            }
        };
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected void deliverResponse(T response) {
        listener.get().onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(
                    gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}