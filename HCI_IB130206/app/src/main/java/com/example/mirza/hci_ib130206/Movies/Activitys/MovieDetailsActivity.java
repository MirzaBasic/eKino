package com.example.mirza.hci_ib130206.Movies.Activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mirza.hci_ib130206.Helper.Connection.ApiRezultat;
import com.example.mirza.hci_ib130206.Helper.MyRunnable;
import com.example.mirza.hci_ib130206.Helper.Sesija;
import com.example.mirza.hci_ib130206.Mod.NonScrollListView;
import com.example.mirza.hci_ib130206.R;
import com.example.mirza.hci_ib130206.api.FilmoviApi;
import com.example.mirza.hci_ib130206.api.FilmoviApi.FilmoviVM;

import java.util.List;



public class MovieDetailsActivity extends AppCompatActivity {

protected int filmID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //preuzimanje iz prethodnog activitija
        //FilmoviVM odabraniFilm = (FilmoviVM) getIntent().getSerializableExtra("odabraniFilm")


        do_GetMovieDetails();



    }

    private void do_GetMovieDetails() {

        filmID=Sesija.getOdabraniFilm().FilmID;
        if(filmID!=0)
            FilmoviApi.getMovieDetails(MovieDetailsActivity.this, filmID, new MyRunnable<ApiRezultat<FilmoviVM>>() {
                @Override
                public void run(ApiRezultat<FilmoviVM> rezultat) {
                    Sesija.setOdabraniFilm(rezultat.value);
                    do_setDetails(rezultat.value);
                }
            });

        Button btnProjekcije = (Button) findViewById(R.id.btnProjections);
        btnProjekcije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_btnProjekcijeClick();
            }
        });



    }


    private void do_btnProjekcijeClick() {

        Intent intent = new Intent(this,MovieProjectionsActivity.class);
        startActivity(intent);
    }


    private void do_setDetails(FilmoviVM film) {

        TextView naziv = (TextView) findViewById(R.id.tvNazivDetalji);
        TextView imdbOcjena = (TextView) findViewById(R.id.tvIdbmOcjenaDetalji);
        TextView ocjena = (TextView) findViewById(R.id.tvOcjenaDetalji);
        TextView opis = (TextView) findViewById(R.id.tvKratakSadrzajDetalji);
        final TextView zanr = (TextView)findViewById(R.id.tvZanrDetalji);
        ImageView imageFIlm = (ImageView) findViewById(R.id.imageFilmDetalji);

        String zanrovi="";
        if(film.Zanrovi!=null)
        for (long i=0;i<film.Zanrovi.size();i++){
            if(i==0)
                zanrovi=film.Zanrovi.get((int)i).Naziv;
            else
            zanrovi=zanrovi+", "+film.Zanrovi.get((int)i).Naziv;
        }
        zanr.setText(zanrovi);

        naziv.setText(film.Naziv);
        imdbOcjena.setText("IMDb rate: "+String.valueOf(film.IMDBOcjena)+"/10");
        ocjena.setText("User rate: "+String.valueOf( film.Ocjena));
        opis.setText("Description: "+String.valueOf( film.Opis));

        byte[] decodedString = Base64.decode(film.Slika, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


        imageFIlm.setImageBitmap(decodedByte);
        imageFIlm.setMaxHeight(decodedByte.getHeight());






        do_setKomentari(film.Komentari);
    }

    private void do_setKomentari(final List<FilmoviApi.KomentariVM> komentari) {


    komentari.add(new FilmoviApi.KomentariVM());

        final NonScrollListView listKomentari= (NonScrollListView) findViewById(R.id.listKomentariFilma);
        listKomentari.setFocusable(false);


       listKomentari.setAdapter(new BaseAdapter() {
           @Override
           public int getCount() {
               return komentari.size();
           }

           @Override
           public Object getItem(int position) {
               return komentari.get(position);
           }

           @Override
           public long getItemId(int position) {
               return 0;
           }

           @Override
           public View getView(int position, View view, ViewGroup parent) {
               final FilmoviApi.KomentariVM komentar=komentari.get(position);


               if((position+1)==komentari.size()){

                   LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                   view=inflater.inflate(R.layout.add_komentar,parent,false);


                   ImageButton btnAddComment= (ImageButton) view.findViewById(R.id.btnAddComment);
                   final EditText txtComent = (EditText) view.findViewById(R.id.txtComment);

                   btnAddComment.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {

                           do_btnAddCommentClick(txtComent.getText().toString());
                       }
                   });




               }
               else {

                   LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                   view=inflater.inflate(R.layout.komentari_filmova,parent,false);

                   final TextView komentarTxt = (TextView) view.findViewById(R.id.tvKomentar);
                   final TextView username = (TextView) view.findViewById(R.id.tvUsernameKomentara);
                   final TextView datum = (TextView) view.findViewById(R.id.tvDatumKreiranjaKomentara);
                   komentarTxt.setText('"' + komentar.Komentar + '"');
                   username.setText(" " + komentar.KorisnickoIme);
                   datum.setText("on " + komentar.DatumKreiranja);
               }


               return view;
           }
       });



    }

    private void do_btnAddCommentClick(String comment) {

        FilmoviApi.Komentari komentar=new FilmoviApi.Komentari();
        komentar.Komentar=comment;
        komentar.KorisnikID=Sesija.getLogiraniKorisnik().KorisnikID;
        komentar.FilmID=Sesija.getOdabraniFilm().FilmID;





        FilmoviApi.postComment(komentar, MovieDetailsActivity.this, new MyRunnable<ApiRezultat<FilmoviApi.Komentari>>() {
            @Override
            public void run(ApiRezultat<FilmoviApi.Komentari> komentariApiRezultat) {

                do_GetMovieDetails();


            }
        });



    }


}





