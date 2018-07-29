package com.example.ta.ponpes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class TentangActivity extends AppCompatActivity {
    private Toolbar tentang;
    private ImageView foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang);
        tentang = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(tentang);
        getSupportActionBar().setTitle("Tentang");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        foto = (ImageView) findViewById(R.id.foto);
        foto.setImageResource(R.drawable.ponpestegal);
    }

}
