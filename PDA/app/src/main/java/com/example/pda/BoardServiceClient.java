package com.example.pda;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoardServiceClient {

    Retrofit retrofit = new Retrofit.Builder().baseUrl("http://18.206.18.154:8080/").addConverterFactory(GsonConverterFactory.create()).build();

    public BoardService boardService = retrofit.create(BoardService.class);
}
