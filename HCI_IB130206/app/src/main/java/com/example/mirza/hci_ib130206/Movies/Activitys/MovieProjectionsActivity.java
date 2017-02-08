package com.example.mirza.hci_ib130206.Movies.Activitys;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mirza.hci_ib130206.Helper.Sesija;
import com.example.mirza.hci_ib130206.Movies.Fragments.ReservationFragment;
import com.example.mirza.hci_ib130206.R;
import com.example.mirza.hci_ib130206.api.FilmoviApi;

import java.util.List;

public class MovieProjectionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_projections);

       List<FilmoviApi.DaniVM> days =Sesija.getOdabraniFilm().Dani;

        do_SetProjectionList(days);



    }

    private void do_SetProjectionList(final List<FilmoviApi.DaniVM> days) {


        final ExpandableListView listDays = (ExpandableListView) findViewById(R.id.listProjections);

        listDays.setAdapter(new BaseExpandableListAdapter() {
            @Override
            public int getGroupCount() {
                return days.size();
            }

            @Override
            public int getChildrenCount(int groupPosition) {
                return days.get(groupPosition).Projekcije.size();
            }

            @Override
            public Object getGroup(int groupPosition) {
                return days.get(groupPosition);
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                return days.get(groupPosition).Projekcije.get(childPosition);
            }

            @Override
            public long getGroupId(int groupPosition) {
                return 0;
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {

                final FilmoviApi.DaniVM day = days.get(groupPosition);
                final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view=inflater.inflate(R.layout.stavke_dani,parent,false);
               final  TextView txtDayName = (TextView) view.findViewById(R.id.tvDaysName);

                ImageView active = (ImageView) view.findViewById(R.id.imageActiv);
                if(day.Projekcije.size()!=0){

                    active.setBackgroundColor(Color.parseColor("#008000"));
                }

                txtDayName.setText(day.Naziv);



                return view;
            }

            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
                final FilmoviApi.ProjekcijeVM projection = days.get(groupPosition).Projekcije.get(childPosition);
                final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view=inflater.inflate(R.layout.stavke_projekcije,parent,false);
                TextView txtStartTime = (TextView) view.findViewById(R.id.tvStartTime);
                TextView txtProjectionType = (TextView)view.findViewById(R.id.tvProjectionType  );
                TextView txtPrice = (TextView) view.findViewById(R.id.tvPrice);

                Button btnReservate = (Button) view.findViewById(R.id.btnReservate);

                txtStartTime.setText("Start time: "+projection.Vrijeme);
                txtProjectionType.setText("Type: "+projection.TipProjekcije);
                txtPrice.setText("Price: "+projection.Cijena);

                btnReservate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        do_BtnReservateClick(projection);
                    }
                });


                return view;


            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return true;
            }
        });



    }

    private void do_BtnReservateClick(FilmoviApi.ProjekcijeVM projection) {

        Sesija.setOdabranaProjekcija(projection);

        DialogFragment dialogFragment=new ReservationFragment();
            FragmentManager fm = getSupportFragmentManager();
            dialogFragment.show(fm, "neki_tag");


    }

}
