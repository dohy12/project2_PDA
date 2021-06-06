package com.example.pda;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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

    @Headers({"ContentType: application/json"})
    @POST("payments/request-infos")
    Call<String> PostRequestInfos(@Body PayReqInfos payReqInfos, @Header("JWT") String JWT);

    @Headers({"ContentType: application/json"})
    @DELETE("payments/request-infos/{GroupId}/{P_ID}")
    Call<String> DeleteRequestInfos(@Path("GroupId") String GroupId, @Path("P_ID") int P_ID, @Header("JWT") String JWT);
}
