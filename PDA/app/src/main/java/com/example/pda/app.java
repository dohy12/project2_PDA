package com.example.pda;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.pda.entity.User;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class app extends Application {
    private static String JWT = "";
    private static String ID = "";
    private static String enPW = "";
    private static String uid = "";
    private static String name = "";
    private static String GroupId = "";
    private static String GroupName = "";
    private static String age = "";
    private static String phone = "";
    private static String email = "";
    private static String intro = "";
    private static String profilesrc = "";
    private static Bitmap profile;

    @Override
    public void onCreate() {
        super.onCreate();
    }
    public static  void getUserInf()
    {
        String Host = "http://10.0.2.2:";
        String port = "8080";
        String AccessPath = "/UserInf";
        final String url = Host + port + AccessPath;
        final OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("JWT", JWT)
                .build();
        final Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TAG", "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                String string = response.body().string();
                System.out.println("string = " + string);
                User user = gson.fromJson(string, User.class);
                name = user.getName();
                phone = user.getPhone();
                age = user.getAge().toString();
                email = user.getEmail();
                profilesrc = user.getProfileimg();
                if (user.getIntroduction() == null)
                    intro = "";
                else
                    intro = user.getIntroduction();

            }
        });


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

    public static String getProfilesrc() {
        return profilesrc;
    }

    public static void setProfilesrc(String profilesrc) {
        app.profilesrc = profilesrc;
    }

    public static Bitmap getProfile() {
        return profile;
    }

    public static void setProfile(Bitmap profile) {
        app.profile = profile;
    }

    public static String getGroupName() {
        return GroupName;
    }

    public static void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public static String getAge() {
        return age;
    }

    public static void setAge(String age) {
        app.age = age;
    }

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        app.phone = phone;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        app.email = email;
    }

    public static String getIntro() {
        return intro;
    }

    public static void setIntro(String intro) {
        app.intro = intro;
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
