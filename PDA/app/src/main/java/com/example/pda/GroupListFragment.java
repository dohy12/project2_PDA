package com.example.pda;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.pda.entity.Group;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GroupListFragment extends DialogFragment {
    private LinearLayout container;
    private LayoutInflater inflater;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;

        View v = inflater.inflate(R.layout.group_list_fragment, container, false);
        this.container = v.findViewById(R.id.container);

        setSearchButtonOnclick(v.findViewById(R.id.searchButton), v);

        return v;
    }

    private void setSearchButtonOnclick(View searchButton, final View v) {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = v.findViewById(R.id.search);
                try {
                    getGroupInf(textView.getText().toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getGroupInf(final String Search) throws UnsupportedEncodingException {
        String Host = "http://18.206.18.154:";
        String port = "8080";
        String AccessPath = "/groups/";
        final String url = Host + port + AccessPath + URLEncoder.encode(Search,"utf-8");
        final OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("JWT", app.getJWT())
                .build();
        final Call call = okHttpClient.newCall(request);
        Runnable networkTask = new Runnable() {
            //        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String string = null;
                try {
                    string = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                Group group = gson.fromJson(string, Group.class);
                showList(Search,group.getGroupId());

            }
        };
        new Thread(networkTask).run();
    }

    private void showList(String groupName, final String GID) {
        String GroupId = GID;
        View v = inflater.inflate(R.layout.group_list_fragment_content, null, false);
        container.addView(v);

        v.findViewById(R.id.groupListImage).setClipToOutline(true);
        TextView viewById = v.findViewById(R.id.groupListName);
        viewById.setText(groupName);
        ///
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinGroup(GID);
                Toast myToast = Toast.makeText(inflater.getContext(), "test", Toast.LENGTH_SHORT);
                myToast.show();
            }
        });
    }

    private void joinGroup(String GID)
    {
        String Host = "http://18.206.18.154:";
        String port = "8080";
        String AccessPath = "/JoinGroup/";
        final String url = Host + port + AccessPath + GID;
        final OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("JWT", app.getJWT())
                .build();
        final Call call = okHttpClient.newCall(request);
        Runnable networkTask = new Runnable() {
            //        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String string = null;
                try {
                    string = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        new Thread(networkTask).run();
    }
}
