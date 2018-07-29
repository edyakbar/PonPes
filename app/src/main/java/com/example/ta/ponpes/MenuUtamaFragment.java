package com.example.ta.ponpes;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ta.ponpes.Config.Adapter.RecyclerAdapterListAll;
import com.example.ta.ponpes.Config.Config;
import com.example.ta.ponpes.Config.Model.ResultAll;
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
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuUtamaFragment extends Fragment {

    @BindView(R.id.spinner)
    Spinner pilihan;
    @BindView(R.id.spinnerprogram)
    Spinner pilihanprogram;
    @BindView(R.id.cari)
    Button cari;
    private ProgressDialog pDialog;
    private MenuItem mSearchMenuItem;
    private ArrayList<ResultAll> resultAlls;
    private RecyclerAdapterListAll adapter;
    @BindView(R.id.card_recycler_view)
    RecyclerView recyclerView;
    private SliderPagerAdapter mAdapter;
    private SliderIndicator mIndicator;
    private SliderView sliderView;
    private LinearLayout mLinearLayout;
    String plhprog,plhjns;
    public MenuUtamaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_utama, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        sliderView = (SliderView) view.findViewById(R.id.sliderView);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.pagesContainer);

        initViews();

        isi_dropdown();
        isidropDownProgram();
        setupSlider();


        final CardView slidetop = (CardView) view.findViewById(R.id.slidetop);
        slidetop.setVisibility(View.VISIBLE);
        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidetop.setVisibility(View.GONE);
                plhprog = pilihanprogram.getSelectedItem().toString();
                plhjns= pilihan.getSelectedItem().toString();
                if (plhprog.equals("Semua")&& plhjns.equals("Semua")){
                    getData();
                    return;
                }else if (plhjns.equals("Semua")){
                    getDataProgram();
                    return;
                }else if (plhprog.equals("Semua")){
                    getDataJenis();
                    return;

            }else {
                    getCariSemua();
                    return;
                }
            }
        });

    }

    public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
        private final Context activity;
        private ArrayList<String> asr;

        public CustomSpinnerAdapter(Context context, ArrayList<String> asr) {
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
            return (long) i;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView txt = new TextView(getActivity());
            txt.setGravity(Gravity.CENTER);
            txt.setPadding(10, 5, 10, 5);
            txt.setTextSize(18);
            txt.setText(asr.get(position));
            txt.setTextColor(Color.parseColor("#000000"));
            return txt;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewgroup) {
            TextView txt = new TextView(getActivity());
            txt.setGravity(Gravity.CENTER);
            txt.setPadding(15, 10, 10, 15);
            txt.setTextSize(16);
//            txt.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0);
            txt.setText(asr.get(i));
            txt.setTextColor(Color.parseColor("#000000"));
            return txt;
        }

    }


    private void initViews() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        getData();
    }

    private void getData() {
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
                    adapter = new RecyclerAdapterListAll(resultAlls, getActivity());
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getActivity(), "Maaf Data Tidak Ada", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
//                pDialog.dismiss();
                Toast.makeText(getActivity(), "Respon gagal", Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet", t.toString());
            }
        });
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////

    private void getDataJenis(){
//        pDialog = new ProgressDialog(this);
//        pDialog.setCancelable(false);
//        pDialog.setMessage("Loading ...");
//        pDialog.show();
        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<Value> call = api.lihat_jenis(pilihan.getSelectedItem().toString());
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
//                pDialog.dismiss();
                String value1 = response.body().getStatus();
                if (value1.equals("1")){
                    Value value = response.body();
                    resultAlls = new ArrayList<>(Arrays.asList(value.getResult()));
                    adapter = new RecyclerAdapterListAll(resultAlls,getActivity());
                    recyclerView.setAdapter(adapter);
                }else {
                    Toast.makeText(getActivity(),"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                //              pDialog.dismiss();
                Toast.makeText(getActivity(),"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void getDataProgram(){
//        pDialog = new ProgressDialog(this);
//        pDialog.setCancelable(false);
//        pDialog.setMessage("Loading ...");
//        pDialog.show();
        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<Value> call = api.lihat_program_like(plhprog);
        Log.i("Catatan", String.valueOf((plhprog)));
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
//                pDialog.dismiss();
                String value1 = response.body().getStatus();
                if (value1.equals("1")){
                    Value value = response.body();
                    resultAlls = new ArrayList<>(Arrays.asList(value.getResult()));
                    adapter = new RecyclerAdapterListAll(resultAlls,getActivity());
                    recyclerView.setAdapter(adapter);
                }else {
                    Toast.makeText(getActivity(),"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                //              pDialog.dismiss();
                Toast.makeText(getActivity(),"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }



    private void setupSlider() {
        sliderView.setDurationScroll(800);
        final List<Fragment> fragments = new ArrayList<>();

//        MultipartBody.Builder builder = new MultipartBody.Builder();
//        builder.setType(MultipartBody.FORM);
//        builder.addFormDataPart("id_rental",id_rental);
//        MultipartBody requestBody = builder.build();

        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<ResponseBody> call = api.lihat_event();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject c = jsonArray.getJSONObject(i);
                        String foto = c.getString("foto");
                        fragments.add(FragmentSlider.newInstance(Config.URL_IMG_EVENT+foto));
                    }

                    mAdapter = new SliderPagerAdapter(getFragmentManager(), fragments);
                    sliderView.setAdapter(mAdapter);
                    mIndicator = new SliderIndicator(getActivity(), mLinearLayout, sliderView, R.drawable.indicator_circle);
                    mIndicator.setPageCount(fragments.size());
                    mIndicator.show();

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(),"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }



        private void isi_dropdown() {
        ArrayList<String> pilih = new ArrayList<String>();
        pilih.add("Semua");
        pilih.add("Salaf");
        pilih.add("Modern");
        pilih.add("Komprehensif");
        CustomSpinnerAdapter customspinner = new CustomSpinnerAdapter(getActivity(), pilih);
        pilihan.setAdapter(customspinner);


        pilihan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pilih = parent.getItemAtPosition(position).toString();
                if (pilih.equals("Salaf")) {

                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    getDataSalaf();

                } else if (pilih.equals("Modern")) {

                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    getDataModern();

                } else if (pilih.equals("Komprehensif")) {

                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    getDataLainya();

                } else {
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
                    adapter = new RecyclerAdapterListAll(resultAlls,getActivity());
                    recyclerView.setAdapter(adapter);
                }else {
                    Toast.makeText(getActivity(),"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                //              pDialog.dismiss();
                Toast.makeText(getActivity(),"Respon gagal",Toast.LENGTH_SHORT).show();
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
                    adapter = new RecyclerAdapterListAll(resultAlls,getActivity());
                    recyclerView.setAdapter(adapter);
                }else {
                    Toast.makeText(getActivity(),"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                //              pDialog.dismiss();
                Toast.makeText(getActivity(),"Respon gagal",Toast.LENGTH_SHORT).show();
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
                    adapter = new RecyclerAdapterListAll(resultAlls,getActivity());
                    recyclerView.setAdapter(adapter);
                }else {
                    Toast.makeText(getActivity(),"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                //              pDialog.dismiss();
                Toast.makeText(getActivity(),"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }

    private void isidropDownProgram(){
        ArrayList<String> pilihprog = new ArrayList<String>();
        pilihprog.add("Semua");
        pilihprog.add("Takhfidul Qur'an");
        pilihprog.add("Kitab Kuning");
        pilihprog.add("Bahasa");
        pilihprog.add("Dai");
        CustomSpinnerAdapter customspinner = new CustomSpinnerAdapter(getActivity(), pilihprog);

        pilihanprogram.setAdapter(customspinner);

        pilihanprogram.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pilih = parent.getItemAtPosition(position).toString();
                if(pilih.equals("Takhfidul Qur'an")){

                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    getDataTakfidul();

                }else if(pilih.equals("Kitab Kuning")){

                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    getDataKitabKuning();

                }else if(pilih.equals("Bahasa")){

                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    getDataBahasa();

                }else if(pilih.equals("Dai")) {

                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
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
                    adapter = new RecyclerAdapterListAll(resultAlls,getActivity());
                    recyclerView.setAdapter(adapter);
                }else {
                    Toast.makeText(getActivity(),"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                //              pDialog.dismiss();
                Toast.makeText(getActivity(),"Respon gagal",Toast.LENGTH_SHORT).show();
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
                    adapter = new RecyclerAdapterListAll(resultAlls,getActivity());
                    recyclerView.setAdapter(adapter);
                }else {
                    Toast.makeText(getActivity(),"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                //              pDialog.dismiss();
                Toast.makeText(getActivity(),"Respon gagal",Toast.LENGTH_SHORT).show();
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
                    adapter = new RecyclerAdapterListAll(resultAlls,getActivity());
                    recyclerView.setAdapter(adapter);
                }else {
                    Toast.makeText(getActivity(),"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                //              pDialog.dismiss();
                Toast.makeText(getActivity(),"Respon gagal",Toast.LENGTH_SHORT).show();
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
                    adapter = new RecyclerAdapterListAll(resultAlls,getActivity());
                    recyclerView.setAdapter(adapter);
                }else {
                    Toast.makeText(getActivity(),"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                //              pDialog.dismiss();
                Toast.makeText(getActivity(),"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }

    private void getCariSemua(){
//        pDialog = new ProgressDialog(this);
//        pDialog.setCancelable(false);
//        pDialog.setMessage("Loading ...");
//        pDialog.show();
        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<Value> call = api.cari_semua_like(pilihan.getSelectedItem().toString(),pilihanprogram.getSelectedItem().toString());
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
//                pDialog.dismiss();
                String value1 = response.body().getStatus();
                if (value1.equals("1")) {
                    Value value = response.body();
                    resultAlls = new ArrayList<>(Arrays.asList(value.getResult()));
                    adapter = new RecyclerAdapterListAll(resultAlls, getActivity());
                    recyclerView.setAdapter(adapter);
                }else{
                    Toast.makeText(getActivity(),"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                //              pDialog.dismiss();
                Toast.makeText(getActivity(),"Respon gagal",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }







}
