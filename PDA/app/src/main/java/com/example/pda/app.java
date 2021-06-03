package com.example.pda;

import android.app.Application;

public class app extends Application {
    private static String JWT = "";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static String getJWT() {
        return JWT;
    }

    public static void setJWT(String JWT) {
        app.JWT = JWT;
    }
}
