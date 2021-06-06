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

    @Headers({"ContentType: application/json"})
    @GET("groups/image/{GroupId}/{image_src}")
    Call<String> SaveintroImage(@Path("GroupId") String GroupId, @Path("image_src") String image_src);

    @Headers({"ContentType: application/json"})
    @GET("groups/image/{GroupId}")
    Call<List<String>> GetintroImage(@Path("GroupId") String GroupId);

    @Headers({"ContentType: application/json"})
    @DELETE("groups/image/{GroupId}")
    Call<String> DeleteintroImage(@Path("GroupId") String GroupId);

    @Headers({"ContentType: application/json"})
    @GET("groups/contents/{GroupId}/{contents}")
    Call<String> SaveintroContents(@Path("GroupId") String GroupId, @Path("contents") String contents);

    @Headers({"ContentType: application/json"})
    @GET("groups/contents/{GroupId}")
    Call<String> GetintroContents(@Path("GroupId") String GroupId);

    @Headers({"ContentType: application/json"})
    @DELETE("groups/contents/{GroupId}")
    Call<String> DeleteintroContents(@Path("GroupId") String GroupId);
}
