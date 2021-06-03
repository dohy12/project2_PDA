package com.example.pda;

import android.content.Context;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.pda", appContext.getPackageName());
    }

    @Test
    public void okhttp() {
        OkHttpClient client = new OkHttpClient();

        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("18.206.18.154")
                .port(8080)
                .addPathSegment("connectTest")
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .build();
        System.out.println(httpUrl);
        System.out.println(request.headers().toString());
        try {
            Response res =client.newCall(request).execute();
            System.out.println(res.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}