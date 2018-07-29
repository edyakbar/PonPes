package com.example.ta.ponpes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ta.ponpes.Config.Adapter.RecyclerAdapterListAll;
import com.example.ta.ponpes.Config.Config;
import com.example.ta.ponpes.Config.Model.ResultAll;
import com.example.ta.ponpes.Config.Model.ResultAllEvent;
import com.example.ta.ponpes.Config.Model.UserAPIService;
import com.example.ta.ponpes.Config.Model.Value;
import com.example.ta.ponpes.Config.slider.FragmentSlider;
import com.example.ta.ponpes.Config.slider.SliderIndicator;
import com.example.ta.ponpes.Config.slider.SliderPagerAdapter;
import com.example.ta.ponpes.Config.slider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuUtamaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SliderPagerAdapter mAdapter;
    private SliderIndicator mIndicator;
    private SliderView sliderView;
    private LinearLayout mLinearLayout;
    private ArrayList<ResultAllEvent> resultAllEvents = new ArrayList<>();
    private ProgressDialog pDialog;
    private MenuItem mSearchMenuItem;
    private ArrayList<ResultAll> resultAlls;
    private RecyclerAdapterListAll adapter;
    @BindView(R.id.card_recycler_view)
    RecyclerView recyclerView;

    FloatingActionButton fab;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText txt_nama,t_jam, t_tanggal,txt_website;

    Spinner txt_kodeMK, txt_kelas;

    Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//
//        sliderView = (SliderView) findViewById(R.id.sliderView);
//        mLinearLayout = (LinearLayout) findViewById(R.id.pagesContainer);

      //  setupSlider();
    //    initViews();

        getSupportActionBar().setTitle("GIS PonPes Tegal");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        MenuUtamaFragment menuUtamaFragment = new MenuUtamaFragment();
//        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,menuUtamaFragment).commit();
        MainFragment menuUtamaFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,menuUtamaFragment).commit();

}

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_utama, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            //Intent i = new Intent(MenuUtamaActivity.this, ListPonpesActivity.class);
//            startActivity(i);
            MainFragment menuUtamaFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,menuUtamaFragment).commit();
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(MenuUtamaActivity.this, MapsAllActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_slideshow) {
            Intent i = new Intent(MenuUtamaActivity.this, EventActivity.class);
            startActivity(i);

        } else if (id == R.id.bantuan) {
            Intent i = new Intent(MenuUtamaActivity.this, BantuanActivity.class);
            startActivity(i);
        } else if (id == R.id.daftar) {
           open(dialogView);
//            String url = "http://www.google.com";
//            Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setData(Uri.parse(url));
//            startActivity(i);
        }else if (id == R.id.tentang) {
            Intent i = new Intent(MenuUtamaActivity.this, TentangActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



//    private void setupSlider() {
//        sliderView.setDurationScroll(800);
//        final List<Fragment> fragments = new ArrayList<>();
//
////        MultipartBody.Builder builder = new MultipartBody.Builder();
////        builder.setType(MultipartBody.FORM);
////        builder.addFormDataPart("id_rental",id_rental);
////        MultipartBody requestBody = builder.build();
//
//        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
//        Call<ResponseBody> call = api.lihat_event();
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response.body().string());
//                    JSONArray jsonArray = jsonObject.getJSONArray("result");
//
//                    for (int i=0;i<jsonArray.length();i++){
//                        JSONObject c = jsonArray.getJSONObject(i);
//                        String foto = c.getString("foto");
//                        fragments.add(FragmentSlider.newInstance(Config.URL_IMG_EVENT+foto));
//                    }
//
//                    mAdapter = new SliderPagerAdapter(getSupportFragmentManager(), fragments);
//                    sliderView.setAdapter(mAdapter);
//                    mIndicator = new SliderIndicator(MenuUtamaActivity.this, mLinearLayout, sliderView, R.drawable.indicator_circle);
//                    mIndicator.setPageCount(fragments.size());
//                    mIndicator.show();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(MenuUtamaActivity.this,"Respon gagal",Toast.LENGTH_SHORT).show();
//                Log.d("Hasil internet",t.toString());
//            }
//        });
//    }


    private void initViews(){
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        getData();
    }

    private void getData(){
//        pDialog = new ProgressDialog(this);
//        pDialog.setCancelable(false);
//        pDialog.setMessage("Loading ...");
//        pDialog.show();
        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<Value> call = api.getJSON();
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
//                pDialog.dismiss();
                String value1 = response.body().getStatus();
                if (value1.equals("1")) {
                    Value value = response.body();
                    resultAlls = new ArrayList<>(Arrays.asList(value.getResult()));
                    adapter = new RecyclerAdapterListAll(resultAlls, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }else{
                    Toast.makeText(MenuUtamaActivity.this,"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                //              pDialog.dismiss();
                Toast.makeText(MenuUtamaActivity.this,"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }

    public void open(View view){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Silakan Kunjungi Website Kami Dengan menekan 'YA'");
        alertDialogBuilder.setTitle("Daftarkan Ponpes");
        alertDialogBuilder.setPositiveButton("YA",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        String url = "http://192.168.1.29/pondok/Frontend/Registrasi";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });
       alertDialogBuilder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {

           }
       });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}





