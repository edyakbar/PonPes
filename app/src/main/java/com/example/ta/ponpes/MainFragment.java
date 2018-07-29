package com.example.ta.ponpes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ta.ponpes.Config.Adapter.RecyclerAdapterListAll;
import com.example.ta.ponpes.Config.Config;
import com.example.ta.ponpes.Config.Model.ResponseJenis;
import com.example.ta.ponpes.Config.Model.ResponseProgram;
import com.example.ta.ponpes.Config.Model.ResultAll;
import com.example.ta.ponpes.Config.Model.SemuajenisItem;
import com.example.ta.ponpes.Config.Model.SemuaprogramItem;
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


public class   MainFragment extends Fragment {
    @BindView(R.id.spinner)
    Spinner pilihan;
    @BindView(R.id.spinnerprogram)
    Spinner pilihanprogram;
    @BindView(R.id.cari)
    Button cari;
//    @BindView(R.id.carijenis)
//    Button carijenis;
//    @BindView(R.id.cariprogram)
//    Button cariprogram;
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
    public MainFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        sliderView = (SliderView) view.findViewById(R.id.sliderView);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.pagesContainer);

        initViews();
        initSpinnerJenis();
        initSpinnerProgram();
        setupSlider();


        final CardView slidetop = (CardView) view.findViewById(R.id.slidetop);
        slidetop.setVisibility(View.VISIBLE);
        sliderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),EventActivity.class);
                getActivity().startActivity(i);
            }
        });


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

    private void initViews() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        getData();
    }

    private void getData() {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading ...");
        pDialog.show();

        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<Value> call = api.getJSON();
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
              pDialog.dismiss();
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
                pDialog.dismiss();
                Toast.makeText(getActivity(), "Respon gagal", Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet", t.toString());
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


    private void initSpinnerProgram(){
        //   loading = ProgressDialog.show(mContext, null, "harap tunggu...", true, false);

        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<ResponseProgram> call = api.get_all_programq();
        call.enqueue(new Callback<ResponseProgram>() {
            @Override
            public void onResponse(Call<ResponseProgram> call, Response<ResponseProgram> response) {
                if (response.isSuccessful()) {
                    // loading.dismiss();
                    List<SemuaprogramItem> semuadosenItems = response.body().getResult();
                    List<String> listSpinner = new ArrayList<String>();

                    for (int i = semuadosenItems.size()-1; i >=0; i--){
                        listSpinner.add(semuadosenItems.get(i).getNama_program());
                        //String id_kecamatan = (semuadosenItems.get(i).getId());
                        //Log.i("Catatan",id_kecamatan);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    pilihanprogram.setAdapter(adapter);

                    // initSpinnerDesa();

                } else {
                    //  loading.dismiss();
                    Toast.makeText(getActivity(), "Gagal Mengambil Ddata Jenis Ponpes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseProgram> call, Throwable t) {
                // loading.dismiss();
                Toast.makeText(getActivity(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSpinnerJenis(){
        //   loading = ProgressDialog.show(mContext, null, "harap tunggu...", true, false);

        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<ResponseJenis> call = api.get_all_jenis();
        call.enqueue(new Callback<ResponseJenis>() {
            @Override
            public void onResponse(Call<ResponseJenis> call, Response<ResponseJenis> response) {
                if (response.isSuccessful()) {
                    // loading.dismiss();
                    List<SemuajenisItem> semuadosenItems = response.body().getResult();
                    List<String> listSpinner = new ArrayList<String>();

                    for (int i = semuadosenItems.size()-1; i >=0; i--){
                        listSpinner.add(semuadosenItems.get(i).getNama());
                        //String id_kecamatan = (semuadosenItems.get(i).getId());
                        //Log.i("Catatan",id_kecamatan);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    pilihan.setAdapter(adapter);
                    // initSpinnerDesa();

                } else {
                    //  loading.dismiss();
                    Toast.makeText(getActivity(), "Gagal Mengambil Ddata Jenis Ponpes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseJenis> call, Throwable t) {
                // loading.dismiss();
                Toast.makeText(getActivity(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataProgram(){
//        pDialog = new ProgressDialog(this);
//        pDialog.setCancelable(false);
//        pDialog.setMessage("Loading ...");
//        pDialog.show();
        UserAPIService api = Config.getRetrofit().create(UserAPIService.class);
        Call<Value> call = api.lihat_program_like(pilihanprogram.getSelectedItem().toString());
 //       Log.i("Catatan", String.valueOf((pilihanprogram)));
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
                }else if (value1.equals("0")){
                    Toast.makeText(getActivity(),value1,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"Gagal Respone",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                //              pDialog.dismiss();
                Toast.makeText(getActivity(),"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }



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
                Toast.makeText(getActivity(),"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();
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
                }else if (value1.equals("0")){
                    Toast.makeText(getActivity(),value1,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"Gagal Respone",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                //              pDialog.dismiss();

                Toast.makeText(getActivity(),"Maaf Data Tidak Ada",Toast.LENGTH_SHORT).show();
                Log.d("Hasil internet",t.toString());
            }
        });
    }



}
