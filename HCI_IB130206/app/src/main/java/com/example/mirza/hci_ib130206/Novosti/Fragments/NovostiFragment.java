package com.example.mirza.hci_ib130206.Novosti.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mirza.hci_ib130206.Helper.Connection.ApiRezultat;
import com.example.mirza.hci_ib130206.Helper.MyApp;
import com.example.mirza.hci_ib130206.Helper.MyRunnable;
import com.example.mirza.hci_ib130206.R;
import com.example.mirza.hci_ib130206.api.NovostiApi;

import java.util.List;

/**
 * Created by Developer on 21.10.2016..
 */
public class NovostiFragment extends Fragment {
    protected View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.stavke_list_novosti,container,false);


        NovostiApi.GetAllNovosti(getActivity(), new MyRunnable<ApiRezultat<List<NovostiApi.Novosti>>>() {
            @Override
            public void run(ApiRezultat<List<NovostiApi.Novosti>> rezultat) {
                if(rezultat.value!=null)
                do_SetNovosti(rezultat.value);

                else{

                    Toast.makeText(MyApp.getContext(),"Result: "+rezultat.porukaError,Toast.LENGTH_LONG).show();
                }
            }
        });



        return view;
    }

    private void do_SetNovosti(final List<NovostiApi.Novosti> novosti) {
        ListView listNovosti= (ListView) view.findViewById(R.id.listNovosti);




        listNovosti.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return novosti.size();
            }

            @Override
            public Object getItem(int position) {
                return novosti.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View view, ViewGroup parent) {

                NovostiApi.Novosti novost=novosti.get(position);
                if(view==null){
                   LayoutInflater inflater=(LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view=inflater.inflate(R.layout.stavke_novosti,parent,false);

                }

                TextView txtTitle= (TextView) view.findViewById(R.id.tvTitle);
                TextView txtContent= (TextView) view.findViewById(R.id.tvContent);
                ImageView imageNews = (ImageView) view.findViewById(R.id.imageNews);


                txtTitle.setText(novost.Naslov);

                txtContent.setText(novost.Sadrzaj);

                byte[] decodedString= Base64.decode(novost.Slika,Base64.DEFAULT);
                Bitmap decodedByte= BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);

                imageNews.setImageBitmap(decodedByte);



                return view;
            }
        });





    }
}
