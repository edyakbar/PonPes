package com.example.ta.ponpes;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ta.ponpes.Config.Adapter.RecyclerAdapterListAll;
import com.example.ta.ponpes.Config.Adapter.RecyclerAdapterListEvent;
import com.example.ta.ponpes.Config.Config;
import com.example.ta.ponpes.Config.Model.ResultAll;
import com.example.ta.ponpes.Config.Model.ResultAllEvent;
import com.example.ta.ponpes.Config.Model.UserAPIService;
import com.example.ta.ponpes.Config.Model.Value;
import com.example.ta.ponpes.Config.Model.ValueEvent;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    private MenuItem mSearchMenuItem;
    private ArrayList<ResultAllEvent> resultAlls;
    private RecyclerAdapterListEvent adapter;
    @BindView(R.id.card_recycler_view)
    RecyclerView recyclerView;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Event Terkini");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();

    }

    private void initViews(){
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        getData();
    }

    private void getData(){
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading ...");
        pDialog.show();
        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<ValueEvent> call = api.get_semua_event();
        call.enqueue(new Callback<ValueEvent>() {
            @Override
            public void onResponse(Call<ValueEvent> call, Response<ValueEvent> response) {
               pDialog.dismiss();
                String value1 = response.body().getStatus();
                if (value1.equals("1")) {
                    ValueEvent value = response.body();
                    resultAlls = new ArrayList<>(Arrays.asList(value.getResult()));
                    adapter = new RecyclerAdapterListEvent(resultAlls, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }else{
                    Toast.makeText(EventActivity.this,"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ValueEvent> call, Throwable t) {
                //              pDialog.dismiss();
                Toast.makeText(EventActivity.this,"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }

}
