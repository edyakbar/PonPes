package com.example.ta.ponpes.Config.Model;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by edy akbar on 26/06/2018.
 */

public interface UserAPIService {
    @GET("get_all")
    Call<Value> getJSON();

    @GET("get_all_program_asli")
    Call<ResponseProgram> get_all_programq();

    @GET("get_all_jenis")
    Call<ResponseJenis> get_all_jenis();

    @FormUrlEncoded
    @POST("lihat_jenis")
    Call<Value> lihat_jenis(@Field("jenis")String jenis);

    @FormUrlEncoded
    @POST("lihat_program")
    Call<Value> lihat_program(@Field("program")String program);

    @FormUrlEncoded
    @POST("lihat_program_like")
    Call<Value> lihat_program_like(@Field("program")String program);

    @GET("lihat_event")
    Call<ResponseBody> lihat_event();

    @GET("lihat_event")
    Call<ValueEvent> lihat_event2();

    @GET("get_semua_event")
    Call<ValueEvent> get_semua_event();

    @FormUrlEncoded
    @POST("cari_semua")
    Call<Value> cari_semua(@Field("jenis")String jenis,
                           @Field("program")String program);

    @FormUrlEncoded
    @POST("cari_semua_like")
    Call<Value> cari_semua_like(@Field("jenis")String jenis,
                           @Field("program")String program);
}
