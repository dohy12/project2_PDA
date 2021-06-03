package com.example.pda;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface RetrofitService {
    //GET
    @Headers({"ContentType: application/json"})
    @GET("payments/pay-infos/{GroupId}")
    Call<List<PayInfos>> getPayInfos(@Path("GroupId") String GroupId, @Header("JWT") String JWT);

    @Headers({"ContentType: application/json"})
    @GET("payments/due-infos/{GroupId}")
    Call<List<DueInfos>> getDueInfos(@Path("GroupId") String GroupId, @Header("JWT") String JWT);

    @Headers({"ContentType: application/json"})
    @GET("payments/user-due-infos/{GroupId}")
    Call<List<Integer>> getUserDueInfos(@Path("GroupId") String GroupId, @Header("JWT") String JWT);
}
