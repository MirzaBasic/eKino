package com.example.mirza.hci_ib130206.Users.Fragments;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mirza.hci_ib130206.Helper.Connection.ApiRezultat;
import com.example.mirza.hci_ib130206.Helper.MyApp;
import com.example.mirza.hci_ib130206.Helper.MyRunnable;
import com.example.mirza.hci_ib130206.Helper.Sesija;
import com.example.mirza.hci_ib130206.R;
import com.example.mirza.hci_ib130206.api.RezervacijeApi;

import java.util.List;

/**
 * Created by Developer on 18.10.2016..
 */
public class UserReservationsFragment extends Fragment {
protected View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.stavke_user_reservations, container, false);




        do_setReservations();







        return view;
    }

    private void do_setReservations() {


        RezervacijeApi.GetReservationsByUserID(getActivity(),Sesija.getLogiraniKorisnik().KorisnikID, new MyRunnable<ApiRezultat<List<RezervacijeApi.RezervacijeVM>>>() {
            @Override
            public void run(ApiRezultat<List<RezervacijeApi.RezervacijeVM>> result) {
                if(result.value!=null)
                do_setListReservations(result.value,view);

                else {
                    Toast.makeText(MyApp.getContext(),"Result: "+result.porukaError,Toast.LENGTH_LONG).show();

                }
            }
        });



    }

    private void do_setListReservations(final List<RezervacijeApi.RezervacijeVM> reservations, View view) {

        final ListView listReservations = (ListView) view.findViewById(R.id.listReservations);


        listReservations.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return reservations.size() ;
            }

            @Override
            public Object getItem(int position) {
                return reservations.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View view, ViewGroup parent) {

                final RezervacijeApi.RezervacijeVM reseration = reservations.get(position);


                if(view==null){
                    final LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view=inflater.inflate(R.layout.stavke_list_rezervacije,parent,false);
                }

                ImageView imageMovie= (ImageView) view.findViewById(R.id.imageMovie);
                TextView movieName = (TextView) view.findViewById(R.id.tvMovieName);
                TextView date = (TextView) view.findViewById(R.id.tvDateReservation);
                TextView typeProjection = (TextView) view.findViewById(R.id.tvProjectionType);
                TextView row = (TextView) view.findViewById(R.id.tvRow);
                TextView seatNumber = (TextView) view.findViewById(R.id.tvSeatNumber);
                TextView day = (TextView) view.findViewById(R.id.tvDay);
                TextView time = (TextView) view.findViewById(R.id.tvStartTime);
                TextView price = (TextView) view.findViewById(R.id.tvPrice);



                movieName.setText(reseration.NazivFilma);
                date.setText(String.valueOf(reseration.DatumRezervacije.toCharArray(),0,11));
                typeProjection.setText("Type :"+reseration.TipProjekcije);
                row.setText("Row: "+reseration.Red);
                seatNumber.setText(String.valueOf("Seat: "+reseration.BrojSjedista));
                day.setText("Day: "+String.valueOf(reseration.Dan.toCharArray(),0,3));

                time.setText("Start :"+"00.00");
                price.setText("Price: "+reseration.Cijena);


                byte[] decodedString = Base64.decode(reseration.Slika, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                imageMovie.setImageBitmap(decodedByte);


                Button btnDelete = (Button) view.findViewById(R.id.btnDeleteReservation);

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        do_DeleteReservation(reseration.RezervacijaID);

                    }
                });


                return view;
            }
        });
    }

    private void do_DeleteReservation(int id) {


        RezervacijeApi.Delete(id,getActivity(), new MyRunnable<ApiRezultat<RezervacijeApi.Rezervacije>>() {
            @Override
            public void run(ApiRezultat<RezervacijeApi.Rezervacije> rezultat) {

                if(!rezultat.isError) {

                    do_setReservations();
                    Toast.makeText(MyApp.getContext(),"Delete successfull!",Toast.LENGTH_LONG).show();

                }
else{

                    Toast.makeText(MyApp.getContext(),"Server Error Connection: "+rezultat.porukaError,Toast.LENGTH_LONG).show();

                }

            }
        });




    }
}
