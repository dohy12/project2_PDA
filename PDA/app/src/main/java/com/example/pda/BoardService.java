package com.example.pda;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BoardService {
    @Headers({"ContentType: application/json"})
    @GET("/Community/{GroupId}/{isNotice}/")
    Call<List<BoardInfo>> boardList(@Path("GroupId") String GroupId, @Path("isNotice") int isNotice, @Header("JWT") String JWT);

    @Headers({"ContentType: application/json"})
    @GET("Community/{GroupId}/select/{BID}/")
    Call<BoardInfo> selectedBoard(@Path("GroupId") String GroupId, @Path("BID") int BID, @Header("JWT") String JWT);

    @Headers({"ContentType: apllication/json"})
    @GET("Community/{GroupId}/serach/{Keyword}/")
    Call<List<BoardInfo>> searchBoard(@Path("GroupId") String GroupId, @Path("Keyword") String Keyword, @Header("JWT") String JWT);

    @Headers({"ContentType: application/json"})
    @POST("Community/{GroupId}/")
    Call<String> boardPost(@Path("GroupId") String GroupId, @Path("Board") BoardInfo Board, @Header("JWT") String JWT);

    @Headers({"ContentType: application/json"})
    @DELETE("Community/{GroupId}/{BID}/")
    Call<String> boardDelete(@Path("GroupId") String GroupId, @Path("BID") String BID, @Header("JWT") String JWT);

    @Headers({"ContentType: application/json"})
    @PUT("Community/{GroupId}/{BID}")
    Call<String> boardUpdate(@Path("GroupId") String GroupId, @Path("BID") int BID, @Header("JWT") String JWT);
}
