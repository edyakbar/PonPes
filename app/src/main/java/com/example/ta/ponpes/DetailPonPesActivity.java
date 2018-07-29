package com.example.ta.ponpes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ta.ponpes.Config.Config;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailPonPesActivity extends AppCompatActivity implements OnMapReadyCallback {

    LatLng PETA;
    Double lt, lg;
    String id_ponpes,nm_ponpes,alamat,profil,kegiatan,latitude,longitude,syarat,gambar,jml_pengajar,jml_santri,
    fasilitas,sekitar,program,jenis,ijin;
    private TextView id_ponpes2,nm_ponpes2,alamat2,profil2,kegiatan2,latitude2,longitude2,syarat2,gambar2,jml_pengajar2,jml_santri2,
            fasilitas2,sekitar2,idjenis2,idprog2,ijin2;

    @BindView(R.id.nama) TextView t_nama;
    @BindView(R.id.alamat) TextView t_alamat;
    @BindView(R.id.jml_pengajar) TextView t_jml_pengajar;
    @BindView(R.id.jml_santri) TextView t_jml_santri;
   // @BindView(R.id.sekitar) TextView t_sekitar;

    @BindView(R.id.idjenis) TextView t_idjenis;
    @BindView(R.id.idprog) TextView t_idprog;
    @BindView(R.id.syarat) TextView t_syarat;
    @BindView(R.id.dtl_kegiatan)
    LinearLayout t_detail_kegiatan;
    @BindView(R.id.dtl_profil)
    LinearLayout t_detailprofil;
    @BindView(R.id.img)
    ImageView img;
    private Context context;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pon_pes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        ButterKnife.bind(this);

        Bundle b = getIntent().getExtras();
        id_ponpes = b.getString("id_ponpes");
        nm_ponpes = b.getString("nm_ponpes");
        alamat = b.getString("alamat");
        profil = b.getString("profil");
        final Spanned profil3 = Html.fromHtml(profil);
        latitude = b.getString("lt");
        longitude = b.getString("lg");

        kegiatan = b.getString("kegiatan");      // used by WebView
       final Spanned kegiatan3 = Html.fromHtml(kegiatan); // used by TextView
        syarat = b.getString("syarat");
        final Spanned syarat3 = Html.fromHtml(syarat);
        jml_pengajar = b.getString("jml_pengajar");
        jml_santri = b.getString("jml_santri");
        fasilitas = b.getString("fasilitas");
        final Spanned fasilitas3 = Html.fromHtml(fasilitas);

        jenis = b.getString("jenis");
        program = b.getString("program");
        gambar = b.getString("gambar");


        lt = Double.valueOf(b.getString("lt"));
        lg = Double.valueOf(b.getString("lg"));
        PETA = new LatLng(lt, lg);


        t_nama.setText(nm_ponpes);
        t_alamat.setText(alamat);

        t_syarat.setText(syarat3);
        t_jml_pengajar.setText(jml_pengajar);
        t_jml_santri.setText(jml_santri);

        t_idjenis.setText(jenis);
        t_idprog.setText(program);
        Glide.with(this).load(Config.URL_IMG+gambar)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(img);
        getSupportActionBar().setTitle(nm_ponpes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        t_detailprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dialog = new AlertDialog.Builder(DetailPonPesActivity.this);
//                inflater = getLayoutInflater();
//                dialogView = inflater.inflate(R.layout.lihatprofil, null);
//                dialog.setView(dialogView);
//                dialog.setCancelable(true);
//                dialog.setIcon(R.mipmap.ic_launcher);
//                dialog.setTitle("Profil Ponpes");
//
//
//                profil2 = (TextView) dialogView.findViewById(R.id.lihatprofil);
//                profil2.setText(profil3);
//
//
//
//
//                dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//
//                dialog.show();
//                int i = getAdapterPosition();
//                Context context = v.getContext();
                Bundle bundle = new Bundle();
                bundle.putString("id_ponpes", id_ponpes);
                bundle.putString("nm_ponpes", nm_ponpes);
                bundle.putString("gambar", gambar);

                bundle.putString("alamat", alamat);
                bundle.putString("jml_pengajar", jml_pengajar);
                bundle.putString("jml_santri", jml_santri);

                bundle.putString("lt", latitude);
                bundle.putString("lg", longitude);
                bundle.putString("kegiatan", kegiatan);
                bundle.putString("syarat", syarat);

                bundle.putString("jenis", jenis);
                bundle.putString("ijin", ijin);
                bundle.putString("program", program);

                bundle.putString("fasilitas", fasilitas);
                bundle.putString("profil", profil);
                Intent intent = new Intent(DetailPonPesActivity.this, DetailProfilActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

//                intent.putExtra("id_ponpes",id_ponpes);
//                intent.putExtra("nm_ponpes",nm_ponpes);
//                intent.putExtra("fasilitas",fasilitas);
//                intent.putExtra("profil",profil);
//                intent.putExtra("gambar",gambar);



            }
        });
//        t_detailprofil.setOnClickListener(new View.OnClickListener() {
//                                              @Override
//                                              public void onClick(View view) {
//                                                  if (view == t_detailprofil) {
//                                                      // int i = getAdapterPosition();
//                                                      Context context = view.getContext();
//                                                      Intent intent = new Intent(context, DetailPonPesActivity.class);
//                                                      intent.putExtra("id_ponpes", id_ponpes);
//                                                      intent.putExtra("nm_ponpes", nm_ponpes);
//                                                      intent.putExtra("alamat", alamat);
//                                                      intent.putExtra("profil", profil);
//                                                      intent.putExtra("kegiatan", kegiatan);
//                                                     // intent.putExtra("lt", lt);
//                                                      //intent.putExtra("lg", lg);
//                                                      intent.putExtra("syarat", syarat);
//                                                      intent.putExtra("gambar", gambar);
//                                                      intent.putExtra("jml_pengajar", jml_pengajar);
//                                                      intent.putExtra("jml_santri", jml_santri);
//                                                      intent.putExtra("fasilitas", fasilitas);
//                                                      //intent.putExtra("sekitar", sekitar);
//
//                                                      intent.putExtra("jenis", jenis);
//                                                      intent.putExtra("program", program);
//                                                      intent.putExtra("ijin", ijin);
//
//                                                      context.startActivity(intent);
//
//
//                                                  }
//                                            }
//                                          });


        t_detail_kegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                dialog = new AlertDialog.Builder(DetailPonPesActivity.this);
//                inflater = getLayoutInflater();
//                dialogView = inflater.inflate(R.layout.lihatkegiatan, null);
//                dialog.setView(dialogView);
//                dialog.setCancelable(true);
//                dialog.setIcon(R.mipmap.ic_launcher);
//                dialog.setTitle("Kegiatan Ponpes");
//
//
//
//                kegiatan2 = (TextView) dialogView.findViewById(R.id.lihatkegiatan);
//
//                kegiatan2.setText(kegiatan3);
//
////                WebView webView = (WebView) dialogView.findViewById(R.id.webView);
////                webView.loadDataWithBaseURL(null, kegiatan, "text/html", "utf-8", null);
//
//
//
//
//
//
//                dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//
//                dialog.show();

                Bundle bundle = new Bundle();
                bundle.putString("id_ponpes", id_ponpes);
                bundle.putString("nm_ponpes", nm_ponpes);
                bundle.putString("gambar", gambar);

                bundle.putString("alamat", alamat);
                bundle.putString("jml_pengajar", jml_pengajar);
                bundle.putString("jml_santri", jml_santri);

                bundle.putString("lt", latitude);
                bundle.putString("lg", longitude);
                bundle.putString("kegiatan", kegiatan);
                bundle.putString("syarat", syarat);

                bundle.putString("jenis", jenis);
                bundle.putString("ijin", ijin);
                bundle.putString("program", program);

                bundle.putString("fasilitas", fasilitas);
                bundle.putString("profil", profil);
                Intent intent = new Intent(DetailPonPesActivity.this, DetailKegiatanActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
//                Intent intent = new Intent(DetailPonPesActivity.this, DetailKegiatanActivity.class);
//                intent.putExtras(bundle);
//                startActivity(intent);

            }
        });

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Marker ragunan = googleMap.addMarker(new MarkerOptions().position(PETA).title(nm_ponpes).snippet(alamat));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PETA, 15));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setTiltGesturesEnabled(true);
    }


}
