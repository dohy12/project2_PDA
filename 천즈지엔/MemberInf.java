package com.example.pj;

import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class MemberInf
{
    /***
     * 회원정보 검색 메소드
     * @param condition 일이름의 일부분
     */
    public void searchUser(String condition) throws InterruptedException
    {
        //TODO: 서버가 경로를 지정은 기다리다
        String baseURL = "";
        String parameter = "?condition=" + condition;
        String url = baseURL + parameter;
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        final Call call = okHttpClient.newCall(request);
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Response response = call.execute();
                    String result = response.body().string();
                    /* 서버에서 받은 JSON는 클래스로 바꾸다   */
                    Member[] member = new Gson().fromJson(result, Member[].class);
                    //TODO :UI에 정보를 기록하다.
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread.join();

    }

    /***
     * 가입하려고 한 회원 정보받기
     */
    public void newUserReceived() throws InterruptedException
    {
        //TODO: 서버가 경로를 지정은 기다리다
        String baseURL = "";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(baseURL).build();
        final Call call = okHttpClient.newCall(request);
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Response response = call.execute();
                    String result = response.body().string();
                    Member[] member = new Gson().fromJson(result, Member[].class);
                    //TODO :UI에 정보를 기록하다.
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread.join();

    }

    /***
     *
     * @param UID 회원 식별 ID
     * @param flag 거절 또 동의
     */
    public void processingRequest(String UID, String flag) throws InterruptedException
    {
        //TODO: 서버가 경로를 지정은 기다리다
        String baseURL = "";
        String parameter = "?UID=" + UID + "&flag=" + flag;
        String url = baseURL + parameter;
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        final Call call = okHttpClient.newCall(request);
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Response response = call.execute();
                    String result = response.body().string();
                    //TODO :결과 저리
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread.join();

    }

    /***
     *권한여부 
     * @param UID 회원 식별 ID
     * @param flag 권한을 주거나 권한을 취소한다
     */
    public void grantPermissions(String UID , String flag) throws InterruptedException
    {
        //TODO: 서버가 경로를 지정은 기다리다
        String baseURL = "";
        String parameter = "?UID=" + UID + "&flag=" + flag;
        String url = baseURL + parameter;
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        final Call call = okHttpClient.newCall(request);
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Response response = call.execute();
                    String result = response.body().string();
                    //TODO :결과 저리
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread.join();
    }

    //TODO: SendMessage(); 다른 회원에게 메세지를 발송
    //TODO: Call(); 다른 회원 전화를 한다
}
