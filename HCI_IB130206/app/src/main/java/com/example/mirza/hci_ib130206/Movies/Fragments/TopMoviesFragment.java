package com.example.mirza.hci_ib130206.Movies.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mirza.hci_ib130206.Helper.Connection.ApiRezultat;
import com.example.mirza.hci_ib130206.Helper.MyApp;
import com.example.mirza.hci_ib130206.Helper.MyRunnable;
import com.example.mirza.hci_ib130206.Helper.Sesija;
import com.example.mirza.hci_ib130206.Movies.Activitys.MovieDetailsActivity;
import com.example.mirza.hci_ib130206.R;
import com.example.mirza.hci_ib130206.api.FilmoviApi;
import com.example.mirza.hci_ib130206.api.FilmoviApi.FilmoviVM;

import java.util.List;


public class TopMoviesFragment extends Fragment {
    final TopMoviesFragment context = TopMoviesFragment.this;
    ListView moviesList ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_movies, container, false);
        LinearLayout layoutSearch = (LinearLayout) view.findViewById(R.id.layoutSearchMovie);
        layoutSearch.setVisibility(View.GONE);


        moviesList= (ListView) view.findViewById(R.id.listViewMovies);
        FilmoviApi.getTopMovies(this.getActivity(), new MyRunnable<ApiRezultat<List<FilmoviVM>>>() {
            @Override
            public void run(ApiRezultat<List<FilmoviVM>> rezultat) {
                if (rezultat.value != null) {

                    do_GetMovies(rezultat.value);


                } else {
                    Toast.makeText(MyApp.getContext(), "Server Error Connection: " + rezultat.porukaError, Toast.LENGTH_LONG).show();
                }
            }

        });
        return view;
    }




    public void do_GetMovies(final List<FilmoviVM> filmovi) {



moviesList.setAdapter(new BaseAdapter() {
    @Override
    public int getCount() {
        return filmovi.size();
    }

    @Override
    public Object getItem(int position) {
        return filmovi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

       final FilmoviVM film = filmovi.get(position);


        if(view==null){
            final LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.stavke_filmovi,parent,false);
        }
            final TextView naziv = (TextView) view.findViewById(R.id.tvNaziv);
        final TextView imdbOcjena = (TextView) view.findViewById(R.id.tvImdbOcjena);
        final TextView ocjena = (TextView) view.findViewById(R.id.tvOcjena);
        final TextView stars = (TextView) view.findViewById(R.id.tvStars);
        ImageView imageFIlm = (ImageView) view.findViewById(R.id.imageFilm);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);


        naziv.setText("Movie: "+film.Naziv);
        imdbOcjena.setText("IMDb rate: "+film.IMDBOcjena);
        ocjena.setText("User rate: "+ film.Ocjena);
        stars.setText("Stars: "+ film.Glumci);


        ratingBar.setRating((float) film.IMDBOcjena);

        byte[] decodedString = Base64.decode(film.Slika, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        imageFIlm.setImageBitmap(decodedByte);
        return view;
    }
});
      moviesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              FilmoviVM odabraniFilm=filmovi.get(position);
              Sesija.setOdabraniFilm(odabraniFilm);


              final Intent intent=  new Intent(getActivity(),MovieDetailsActivity.class);
              // ili moze i prebaciti na activity
              //intent.putExtra("odabrani film",odabraniFilm);
              startActivity(intent);
          }
      });
    }



}
