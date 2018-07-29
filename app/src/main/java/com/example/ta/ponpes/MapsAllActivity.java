package com.example.ta.ponpes;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ta.ponpes.Config.Config;
import com.example.ta.ponpes.Config.Model.ResultAll;
import com.example.ta.ponpes.Config.Model.UserAPIService;
import com.example.ta.ponpes.Config.Model.Value;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsAllActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private GoogleMap map;
    private ProgressDialog pDialog;
    private Marker myMarker;
    private  Context myContex;
    private List<ResultAll> resultAll = new ArrayList<>();
    private BottomSheetBehavior bottomSheetBehavior;
    @BindView(R.id.nama)TextView t_nama;
    @BindView(R.id.alamat)TextView t_alamat;
    @BindView(R.id.jenis)TextView t_jenis;
    @BindView(R.id.program)TextView t_program;
    @BindView(R.id.gambar)ImageView t_gambar;
    @BindView(R.id.btndetail)Button btndetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_all);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myContex  = this;
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Semua Peta Ponpes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        SupportMapFragment supportMapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
        ActivityCompat.requestPermissions(this, new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION }, 100);
        getData();



// change the state of the bottom sheet




    }

    private void getData(){
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading ...");
        pDialog.show();
        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<Value> call = api.getJSON();
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                pDialog.dismiss();
                Toast.makeText(MapsAllActivity.this,"Sukses",Toast.LENGTH_SHORT).show();
                Value value = response.body();
                resultAll = new ArrayList<>(Arrays.asList(value.getResult()));
                setMaps();
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(MapsAllActivity.this,"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }

    private void setMaps(){

        for (int i=0;i<=resultAll.size()-1;i++){
            //Toast.makeText(MapsAllActivity.this,resultAll.get(i).getNama(),Toast.LENGTH_SHORT).show();
            LatLng peta = new LatLng(Double.valueOf(resultAll.get(i).getLatitude()), Double.valueOf(resultAll.get(i).getLongitude()));
            Log.d("hasil_peta",resultAll.get(i).getLatitude()+"/"+resultAll.get(i).getLongitude());
            CameraPosition cameraPosition = new CameraPosition.Builder().target(peta).zoom(10).build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
           Marker mark = map.addMarker(new MarkerOptions().position(peta));
           mark.setTag(resultAll.get(i));


            map.setOnMarkerClickListener(this);
        }
    }



 @Override
    public boolean onMarkerClick(final Marker marker) {
     System.out.println(marker.getTag());
     //marker.showInfoWindow();
     LinearLayout llBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);
     // init the bottom sheet behavior
     BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
     final ResultAll res = (ResultAll) marker.getTag();
     t_nama.setText(res.getNama_ponpes());
     t_alamat.setText(res.getAlamat());
     t_jenis.setText(res.getJenis());
     t_program.setText(res.getProgram());
     Glide.with(this).load(Config.URL_IMG+res.getGambar())
             .thumbnail(0.5f)
             .diskCacheStrategy(DiskCacheStrategy.ALL)
             .crossFade()
             .placeholder(R.drawable.ponpestegal)
             .error(R.drawable.ponpestegal)
             .into(t_gambar);
     btndetail.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

             Intent intent = new Intent(MapsAllActivity.this, DetailPonPesActivity.class);
            intent.putExtra("id_ponpes", res.getId());
            intent.putExtra("nm_ponpes", res.getNama_ponpes());
            intent.putExtra("alamat", res.getAlamat());
            intent.putExtra("profil", res.getProfil());
            intent.putExtra("kegiatan", res.getKegiatan());
            intent.putExtra("lt", res.getLatitude());
            intent.putExtra("lg", res.getLongitude());
            intent.putExtra("syarat", res.getSyarat());
            intent.putExtra("gambar", res.getGambar());
            intent.putExtra("jml_pengajar", res.getJml_pengajar());
            intent.putExtra("jml_santri", res.getJml_santri());
            intent.putExtra("fasilitas", res.getFasilitas());
            intent.putExtra("sekitar", res.getSekitar());

            intent.putExtra("jenis", res.getJenis());
            intent.putExtra("program",res.getProgram());
            intent.putExtra("ijin", res.getIjin());

            startActivity(intent);

         }
     });


     bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
     bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
         @Override
         public void onStateChanged(@NonNull View bottomSheet, int newState) {

         }

         @Override
         public void onSlide(@NonNull View bottomSheet, float slideOffset) {

         }
     });
// set the peek height
     bottomSheetBehavior.setPeekHeight(340);

// set hideable or not
     bottomSheetBehavior.setHideable(true);
//

//        if (marker.equals(myMarker))
//            for (int i=0;i<=resultAll.size()-1;i++){
//            Context context = myContex;
//            Intent intent = new Intent(context, DetailPonPesActivity.class);
//            intent.putExtra("id_ponpes", resultAll.get(i).getId());
//            intent.putExtra("nm_ponpes", resultAll.get(i).getNama_ponpes());
//            intent.putExtra("alamat", resultAll.get(i).getAlamat());
//            intent.putExtra("profil", resultAll.get(i).getProfil());
//            intent.putExtra("kegiatan", resultAll.get(i).getKegiatan());
//            intent.putExtra("lt", resultAll.get(i).getLatitude());
//            intent.putExtra("lg", resultAll.get(i).getLongitude());
//            intent.putExtra("syarat", resultAll.get(i).getSyarat());
//            intent.putExtra("gambar", resultAll.get(i).getGambar());
//            intent.putExtra("jml_pengajar", resultAll.get(i).getJml_pengajar());
//            intent.putExtra("jml_santri", resultAll.get(i).getJml_santri());
//            intent.putExtra("fasilitas", resultAll.get(i).getFasilitas());
//            intent.putExtra("sekitar", resultAll.get(i).getSekitar());
//
//            intent.putExtra("jenis", resultAll.get(i).getJenis());
//            intent.putExtra("program", resultAll.get(i).getProgram());
//            intent.putExtra("ijin", resultAll.get(i).getIjin());
//
//            context.startActivity(intent);
//            //handle click here
//        }
        return false;
    }

    public void toggleBottomSheet() {
        if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            //btnBottomSheet.setText("Close sheet");
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            //btnBottomSheet.setText("Expand sheet");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        //googleMap.addMarker(new MarkerOptions().position(PETA).title(nama).snippet(alamat));
        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PETA, 15));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setCompassEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            googleMap.getUiSettings().setTiltGesturesEnabled(true);
        }

    }
}
