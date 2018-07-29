package com.example.ta.ponpes;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ta.ponpes.Config.Adapter.RecyclerAdapterListAll;
import com.example.ta.ponpes.Config.Config;
import com.example.ta.ponpes.Config.Model.ResultAll;
import com.example.ta.ponpes.Config.Model.UserAPIService;
import com.example.ta.ponpes.Config.Model.Value;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPonpesActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
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
    //Button carijenis,cariprogram;
    Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ponpes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        getSupportActionBar().setTitle("List Pondok Pesantren");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab         = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //      txt_hasil.setText(null);
                DialogForm();
            }
        });


        initViews();
       // isi_dropdown();
    }

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
                    Toast.makeText(ListPonpesActivity.this,"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                //              pDialog.dismiss();
                Toast.makeText(ListPonpesActivity.this,"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }

    public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
        private final Context activity;
        private ArrayList<String> asr;

        public CustomSpinnerAdapter(Context context,ArrayList<String> asr) {
            this.asr = asr;
            activity = context;
        }

        @Override
        public int getCount() {
            return asr.size();
        }

        @Override
        public Object getItem(int i) {
            return asr.get(i);
        }

        @Override
        public long getItemId(int i) {
            return (long)i;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView txt = new TextView(ListPonpesActivity.this);
            txt.setGravity(Gravity.CENTER);
            txt.setPadding(10, 5, 10, 5);
            txt.setTextSize(18);
            txt.setText(asr.get(position));
            txt.setTextColor(Color.parseColor("#000000"));
            return txt;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewgroup) {
            TextView txt = new TextView(ListPonpesActivity.this);
            txt.setGravity(Gravity.CENTER);
            txt.setPadding(15, 10, 10, 15);
            txt.setTextSize(16);
//            txt.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0);
            txt.setText(asr.get(i));
            txt.setTextColor(Color.parseColor("#000000"));
            return txt;
        }
    }


    private void isi_dropdown() {
        ArrayList<String> pilih = new ArrayList<String>();
        pilih.add("All");
        pilih.add("Salaf");
        pilih.add("Modern");
        pilih.add("Komprehensif");
        CustomSpinnerAdapter customspinner = new CustomSpinnerAdapter(ListPonpesActivity.this, pilih);
        txt_kodeMK= (Spinner)dialogView.findViewById(R.id.txt_kodeMK);
        txt_kodeMK.setAdapter(customspinner);

        txt_kodeMK.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pilih = parent.getItemAtPosition(position).toString();
                if(pilih.equals("Salaf")){

                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListPonpesActivity.this.getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    getDataSalaf();

                }else if(pilih.equals("Modern")){

                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListPonpesActivity.this.getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    getDataModern();

                }else if(pilih.equals("Komprehensif")){

                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListPonpesActivity.this.getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    getDataLainya();

                }else {
                    initViews();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void isidropDownProgram(){
        ArrayList<String> pilih = new ArrayList<String>();
        pilih.add("All :");
        pilih.add("Takhfidul Qur'an");
        pilih.add("Kitab Kuning");
        pilih.add("Bahasa");
        pilih.add("Dai");
        CustomSpinnerAdapter customspinner = new CustomSpinnerAdapter(ListPonpesActivity.this, pilih);
        txt_kelas= (Spinner)dialogView.findViewById(R.id.txt_kelas);
        txt_kelas.setAdapter(customspinner);

        txt_kelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pilih = parent.getItemAtPosition(position).toString();
                if(pilih.equals("Takhfidul Qur'an")){

                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListPonpesActivity.this.getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    getDataTakfidul();

                }else if(pilih.equals("Kitab Kuning")){

                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListPonpesActivity.this.getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    getDataKitabKuning();

                }else if(pilih.equals("Bahasa")){

                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListPonpesActivity.this.getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    getDataBahasa();

                }else if(pilih.equals("Dai")) {

                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListPonpesActivity.this.getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    getDataDai();

                }else {
                    initViews();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    private void getDataSalaf(){
//        pDialog = new ProgressDialog(this);
//        pDialog.setCancelable(false);
//        pDialog.setMessage("Loading ...");
//        pDialog.show();
        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<Value> call = api.lihat_jenis("Salaf");
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
//                pDialog.dismiss();
                String value1 = response.body().getStatus();
                if (value1.equals("1")){
                Value value = response.body();
                resultAlls = new ArrayList<>(Arrays.asList(value.getResult()));
                adapter = new RecyclerAdapterListAll(resultAlls,getApplicationContext());
                recyclerView.setAdapter(adapter);
            }else {
                    Toast.makeText(ListPonpesActivity.this,"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                //              pDialog.dismiss();
                Toast.makeText(ListPonpesActivity.this,"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }

    private void getDataModern(){
//        pDialog = new ProgressDialog(this);
//        pDialog.setCancelable(false);
//        pDialog.setMessage("Loading ...");
//        pDialog.show();
        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<Value> call = api.lihat_jenis("Modern");
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
//                pDialog.dismiss();
                String value1 = response.body().getStatus();
                if (value1.equals("1")){
                Value value = response.body();
                resultAlls = new ArrayList<>(Arrays.asList(value.getResult()));
                adapter = new RecyclerAdapterListAll(resultAlls,getApplicationContext());
                recyclerView.setAdapter(adapter);
            }else {
                Toast.makeText(ListPonpesActivity.this,"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                //              pDialog.dismiss();
                Toast.makeText(ListPonpesActivity.this,"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }

    private void getDataLainya(){
//        pDialog = new ProgressDialog(this);
//        pDialog.setCancelable(false);
//        pDialog.setMessage("Loading ...");
//        pDialog.show();

        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<Value> call = api.lihat_jenis("Komprehensif");
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
//                pDialog.dismiss();
                String value1= response.body().getStatus();
                if (value1.equals("1")){
                Value value = response.body();
                resultAlls = new ArrayList<>(Arrays.asList(value.getResult()));
                adapter = new RecyclerAdapterListAll(resultAlls,getApplicationContext());
                recyclerView.setAdapter(adapter);
            }else {
                Toast.makeText(ListPonpesActivity.this,"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                //              pDialog.dismiss();
                Toast.makeText(ListPonpesActivity.this,"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }


    private void getDataTakfidul(){
//        pDialog = new ProgressDialog(this);
//        pDialog.setCancelable(false);
//        pDialog.setMessage("Loading ...");
//        pDialog.show();
        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<Value> call = api.lihat_program_like("Takhfidul Qur'an");
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
//                pDialog.dismiss();
                String value1 = response.body().getStatus();
                if (value1.equals("1")){
                    Value value = response.body();
                    resultAlls = new ArrayList<>(Arrays.asList(value.getResult()));
                    adapter = new RecyclerAdapterListAll(resultAlls,getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }else {
                    Toast.makeText(ListPonpesActivity.this,"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                //              pDialog.dismiss();
                Toast.makeText(ListPonpesActivity.this,"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }

    private void getDataKitabKuning(){
//        pDialog = new ProgressDialog(this);
//        pDialog.setCancelable(false);
//        pDialog.setMessage("Loading ...");
//        pDialog.show();
        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<Value> call = api.lihat_program_like("Kitab Kuning");
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
//                pDialog.dismiss();
                String value1 = response.body().getStatus();
                if (value1.equals("1")){
                    Value value = response.body();
                    resultAlls = new ArrayList<>(Arrays.asList(value.getResult()));
                    adapter = new RecyclerAdapterListAll(resultAlls,getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }else {
                    Toast.makeText(ListPonpesActivity.this,"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                //              pDialog.dismiss();
                Toast.makeText(ListPonpesActivity.this,"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }

    private void getDataBahasa(){
//        pDialog = new ProgressDialog(this);
//        pDialog.setCancelable(false);
//        pDialog.setMessage("Loading ...");
//        pDialog.show();

        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<Value> call = api.lihat_program_like("Bahasa");
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
//                pDialog.dismiss();
                String value1= response.body().getStatus();
                if (value1.equals("1")){
                    Value value = response.body();
                    resultAlls = new ArrayList<>(Arrays.asList(value.getResult()));
                    adapter = new RecyclerAdapterListAll(resultAlls,getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }else {
                    Toast.makeText(ListPonpesActivity.this,"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                //              pDialog.dismiss();
                Toast.makeText(ListPonpesActivity.this,"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }
    private void getDataDai(){
//        pDialog = new ProgressDialog(this);
//        pDialog.setCancelable(false);
//        pDialog.setMessage("Loading ...");
//        pDialog.show();

        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<Value> call = api.lihat_program_like("Dai");
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
//                pDialog.dismiss();
                String value1= response.body().getStatus();
                if (value1.equals("1")){
                    Value value = response.body();
                    resultAlls = new ArrayList<>(Arrays.asList(value.getResult()));
                    adapter = new RecyclerAdapterListAll(resultAlls,getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }else {
                    Toast.makeText(ListPonpesActivity.this,"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                //              pDialog.dismiss();
                Toast.makeText(ListPonpesActivity.this,"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }







    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                adapter.setFilter(resultAlls);
                return true; // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true; // Return true to expand action view
            }
        });
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final ArrayList<ResultAll> filteredModelList = filter(resultAlls, newText);

        adapter.setFilter(filteredModelList);
        return true;
    }

    private ArrayList<ResultAll> filter(ArrayList<ResultAll> models, String query) {
        query = query.toLowerCase();final ArrayList<ResultAll> filteredModelList = new ArrayList<>();
        for (ResultAll model : models) {
            final String text = model.getNama_ponpes().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    private void kosong(){
        txt_kodeMK.setAdapter(null);
        txt_kelas.setAdapter(null);
        t_jam.setText(null);
        t_tanggal.setText(null);
        txt_website.setText(null);
    }

    private void DialogForm() {
        dialog = new AlertDialog.Builder(ListPonpesActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_view, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Lihat");

        isi_dropdown();
        isidropDownProgram();

        ButterKnife.bind(this);
        mContext = this;
       // mApiService = Config.getRetrofit();


        txt_kelas   = (Spinner) dialogView.findViewById(R.id.txt_kelas);
        txt_kodeMK    = (Spinner) dialogView.findViewById(R.id.txt_kodeMK);

//        carijenis = (Button) dialogView.findViewById(R.id.carijenis);
//        carijenis.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(ListPonpesActivity.this,"Coba saja sebenarnya",Toast.LENGTH_SHORT).show();
//            }
//        });

//
//        kosong();
//        UserAPIService api = com.example.ta.ponpes.Config.Config.getRetrofit().create(UserAPIService.class);
//
//        Call<ResponsePengajar> call2 = api.get_mk(id_dosen);
//
//        //txt_ruang = (Spinner) dialogView.findViewById(R.id.txt_ruang);
//        call2.enqueue(new Callback<ResponsePengajar>() {
//            @Override
//            public void onResponse(Call<ResponsePengajar> call, Response<ResponsePengajar> response) {
//                if (response.isSuccessful()) {
//                    // pDialog.dismiss();
////                    getkelas();
//                    List<SemuapengajarItem> semuadosenItems = response.body().getSemuaPengajar();
//                    //  id_mk= (semuadosenItems.get(null).getId_mk());
//                    List<String> listSpinner = new ArrayList<String>();
//                    for (int i = 0; i < semuadosenItems.size(); i++) {
//                        listSpinner.add(semuadosenItems.get(i).getKode_mk());
//
//                    }
//
//
//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
//                            android.R.layout.simple_spinner_item, listSpinner);
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    txt_kodeMK.setAdapter(adapter);
//                  //  getkelas();
//
//
//
//                } else {
//                    // pDialog.dismiss();
//                    Toast.makeText(mContext, "Gagal mengambil data dosen", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponsePengajar> call, Throwable t) {
//                //pDialog.dismiss();
//                Toast.makeText(mContext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
        dialog.setPositiveButton("Cari", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
//                kodeMK = txt_kodeMK.getSelectedItem().toString();
//                kelas = txt_kelas.getSelectedItem().toString();
             //  Toast.makeText(ListPonpesActivity.this,"Cari Semuanya ",Toast.LENGTH_SHORT).show();
                getCariSemua();
//
            }
        });
//
        dialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
//
    dialog.show();
    }

    private void getCariSemua(){
//        pDialog = new ProgressDialog(this);
//        pDialog.setCancelable(false);
//        pDialog.setMessage("Loading ...");
//        pDialog.show();
        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<Value> call = api.cari_semua_like(txt_kodeMK.getSelectedItem().toString(),txt_kelas.getSelectedItem().toString());
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
                    Toast.makeText(ListPonpesActivity.this,"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                //              pDialog.dismiss();
                Toast.makeText(ListPonpesActivity.this,"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }


}
