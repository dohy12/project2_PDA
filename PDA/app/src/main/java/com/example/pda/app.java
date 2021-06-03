package com.example.pda;

import android.app.Application;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class app extends Application {
    private static String JWT = "";
    private static String ID = "";
    private static String enPW = "";
    private static String uid = "";
    private static String name = "";
    private static String GroupId = "";
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static void doLogin()
    {
        OkHttpClient client = new OkHttpClient();

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("18.206.18.154")
                .port(8080)
                .addPathSegment("auth")
                .addQueryParameter("id", ID)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .addHeader("pw", enPW)
                .build();

        System.out.println(httpUrl);
        System.out.println(request.headers().toString());
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
                System.out.println("로그인 실패");

            }

            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {
                try {
                    JSONObject json = new JSONObject(response.body().string());
                    setJWT(json.getString("result"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static String getUid() {
        return uid;
    }

    public static void setUid(String uid) {
        app.uid = uid;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        app.name = name;
    }

    public static String getGroupId() {
        return GroupId;
    }

    public static void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public static String getID() {
        return ID;
    }

    public static void setID(String ID) {
        app.ID = ID;
    }

    public static String getEnPW() {
        return enPW;
    }

    public static void setEnPW(String enPW) {
        app.enPW = enPW;
    }

    public static String getJWT() {
        return JWT;
    }

    public static void setJWT(String JWT) {
        app.JWT = JWT;
    }
}
