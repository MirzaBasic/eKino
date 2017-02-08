package com.example.mirza.hci_ib130206.Movies.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mirza.hci_ib130206.Helper.Connection.ApiRezultat;
import com.example.mirza.hci_ib130206.Helper.MyRunnable;
import com.example.mirza.hci_ib130206.Helper.Sesija;
import com.example.mirza.hci_ib130206.R;
import com.example.mirza.hci_ib130206.api.RezervacijeApi;

import java.util.List;

/**
 * Created by Developer on 12.10.2016..
 */
public class ReservationFragment extends DialogFragment {


    int SeatID=0;
    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       final View view=inflater.inflate(R.layout.stavke_rezervacije, container, false);
        getDialog().setTitle("Seat reservation");

        doSetSlobodnaSjedista(view);

        RezervacijeApi.GetZauzetaSjedistaByProjekcijaID(this.getActivity(), Sesija.getOdabranaProjekcija().ProjekcijaID, new MyRunnable<ApiRezultat<List<RezervacijeApi.SjedistaVM>>>() {
            @Override
            public void run(ApiRezultat<List<RezervacijeApi.SjedistaVM>> result) {
               do_SetZauzetaSjedista(result.value, view);
            }
        });


        final Button btnConfirmReservation = (Button) view.findViewById(R.id.btnConfirmReservation);

        btnConfirmReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                do_btnConfirmReservationClick(view);


            }
        });


        return view;


    }

    private void do_btnConfirmReservationClick(final View view) {

        if(SeatID!=0){

            RezervacijeApi.SetRezervaciju(this.getActivity(),SeatID, new MyRunnable<ApiRezultat<RezervacijeApi.Rezervacije>>() {
                @Override
                public void run(ApiRezultat<RezervacijeApi.Rezervacije> rezervacijeApiRezultat) {
                    do_AfterReservation(view);
                }
            });


        }

    }

    private void do_AfterReservation(View view) {


        int resID = view.getResources().getIdentifier("btnSeat"+SeatID, "id",getActivity().getPackageName());
        Button btn = (Button) view.findViewById(resID);
        btn.setTextColor(Color.RED);
        btn.setClickable(false);
        SeatID=0;




    }

    private void doSetSlobodnaSjedista(final View view) {

       Button btn;
        for (int i=1; i <=30;i++) {
             int resID = view.getResources().getIdentifier("btnSeat"+i, "id",getActivity().getPackageName());
           btn = (Button) view.findViewById(resID);
            final int id = i;
            final Button finalBtn = btn;
            btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

      if(SeatID!=0){

         int buttonID = view.getResources().getIdentifier("btnSeat"+SeatID, "id",getActivity().getPackageName());
         Button btnOld = (Button) view.findViewById(buttonID);
          btnOld.setTextColor(getResources().getColor(R.color.green));
          btnOld.setClickable(true);

      }
       SeatID=id;
        finalBtn.setTextColor(Color.BLUE);

    }
});

        }


    }



    private void do_SetZauzetaSjedista(List<RezervacijeApi.SjedistaVM> takenSeats, View view) {
        Button btn;
        for (int i=0; i <takenSeats.size();i++) {
            int resID = view.getResources().getIdentifier("btnSeat"+takenSeats.get(i).SjedisteID, "id",getActivity().getPackageName());
            btn = (Button) view.findViewById(resID);
            btn.setTextColor(Color.RED);
            btn.setClickable(false);

        }


    }
}
