package com.example.ta.ponpes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ta.ponpes.Config.Config;
import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailProfilActivity extends AppCompatActivity {

    //String profil, fasilitas,nm_ponpes,id_ponpes,gambar;

    LatLng PETA;
    Double lt, lg;
    String id_ponpes,nm_ponpes,alamat,profil,kegiatan,latitude,longitude,syarat,gambar,jml_pengajar,jml_santri,
            fasilitas,sekitar,program,jenis,ijin;
    @BindView(R.id.fasilitas)
    TextView t_fasilitas;
    @BindView(R.id.btnback)
    LinearLayout btnback;
    @BindView(R.id.profil) TextView t_profil;
    @BindView(R.id.img)
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);


        Bundle b = getIntent().getExtras();
        id_ponpes = b.getString("id_ponpes");
        nm_ponpes = b.getString("nm_ponpes");
        fasilitas = b.getString("fasilitas");
        final Spanned fasilitas3 = Html.fromHtml(fasilitas);
        profil = b.getString("profil");
        final Spanned profil3 = Html.fromHtml(profil);
        gambar = b.getString("gambar");


        id_ponpes = b.getString("id_ponpes");
        nm_ponpes = b.getString("nm_ponpes");
        alamat = b.getString("alamat");
        // profil = b.getString("profil");
        // final Spanned profil3 = Html.fromHtml(profil);

        kegiatan = b.getString("kegiatan");      // used by WebView
        final Spanned kegiatan3 = Html.fromHtml(kegiatan); // used by TextView
        syarat = b.getString("syarat");
        final Spanned syarat3 = Html.fromHtml(syarat);
        jml_pengajar = b.getString("jml_pengajar");
        jml_santri = b.getString("jml_santri");
        //fasilitas = b.getString("fasilitas");
        // final Spanned fasilitas3 = Html.fromHtml(fasilitas);

        jenis = b.getString("jenis");
        program = b.getString("program");
        latitude = b.getString("lt");
        longitude = b.getString("lg");


        gambar = b.getString("gambar");

        t_profil.setText(profil3);
        t_fasilitas.setText(fasilitas3);
        Glide.with(this).load(Config.URL_IMG+gambar)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(img);
        getSupportActionBar().setTitle(nm_ponpes);

       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
//            public void onClick(View view) {
//                Bundle bundle = new Bundle();
//                bundle.putString("id_ponpes", id_ponpes);
//                bundle.putString("nm_ponpes", nm_ponpes);
//                bundle.putString("gambar", gambar);
//
//                bundle.putString("alamat", alamat);
//                bundle.putString("jml_pengajar", jml_pengajar);
//                bundle.putString("jml_santri", jml_santri);
//
//                bundle.putString("lt", latitude);
//                bundle.putString("lg", longitude);
//                bundle.putString("kegiatan", kegiatan);
//
//                bundle.putString("jenis", jenis);
//                bundle.putString("ijin", ijin);
//                bundle.putString("program", program);
//
//                bundle.putString("fasilitas", fasilitas);
//                bundle.putString("profil", profil);
//                Intent intent = new Intent(DetailProfilActivity.this, DetailPonPesActivity.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
            public void onClick(View view) {
                if (view == btnback) {
                    // int i = getAdapterPosition();
                    Context context = view.getContext();
                    Intent intent = new Intent(context, DetailPonPesActivity.class);
                    intent.putExtra("id_ponpes", id_ponpes);
                    intent.putExtra("nm_ponpes", nm_ponpes);
                    intent.putExtra("alamat", alamat);
                    intent.putExtra("profil", profil);
                    intent.putExtra("kegiatan", kegiatan);
                    intent.putExtra("lt", latitude);
                    intent.putExtra("lg", longitude);
                    intent.putExtra("syarat", syarat);
                    intent.putExtra("gambar", gambar);
                    intent.putExtra("jml_pengajar", jml_pengajar);
                    intent.putExtra("jml_santri", jml_santri);
                    intent.putExtra("fasilitas", fasilitas);
                    //intent.putExtra("sekitar", sekitar);

                    intent.putExtra("jenis", jenis);
                    intent.putExtra("program", program);
                    intent.putExtra("ijin", ijin);

                    context.startActivity(intent);


                }
            }
        });

    }

}
