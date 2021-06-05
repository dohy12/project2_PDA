package com.example.pda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SelectGroup extends AppCompatActivity {

    private LinearLayout container;
    private LayoutInflater inflater;
    ArrayList<String> tempList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_group);

        System.out.println(app.getJWT());

        container = findViewById(R.id.container);
        inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

        tempList = new ArrayList<>();
        tempList.add("단체이름1");
        tempList.add("단체이름2");
        tempList.add("단체이름3");
        tempList.add("단체이름4");
        tempList.add("단체이름5");
        tempList.add("단체이름6");
        tempList.add("단체이름7");

        setProfileInfo();
        showGroupList();
    }

    private void setProfileInfo(){
        ImageView profile =(ImageView) findViewById(R.id.profile_image);
        profile.setImageBitmap(app.getProfile());
        findViewById(R.id.profile_image).setClipToOutline(true);
        ((TextView)findViewById(R.id.welcome)).setText(app.getName()+ "님 반갑습니다.");
    }

    private void showGroupList(){
        for(int i=0;i<Math.ceil(tempList.size()/3.0f);i++) {
            int maxJ = Math.min(3, tempList.size()-i*3);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.LEFT;

            LinearLayout l = new LinearLayout(this);
            l.setLayoutParams(layoutParams);
            l.setOrientation(LinearLayout.HORIZONTAL);

            container.addView(l);

            for(int j=0;j<maxJ ; j++){
                View v = inflater.inflate(R.layout.group_layout, null);
                l.addView(v);

                ((TextView)v.findViewById(R.id.group_name)).setText(tempList.get(i*3+j));

                LinearLayout groupLayout = v.findViewById(R.id.group_layout);
                groupLayout.setClipToOutline(true);
                groupLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });


            }
        }
    }

    public void applyGroup(View view){
        GroupListFragment fragment = new GroupListFragment();

        fragment.show(getSupportFragmentManager(),"tag");

    }

}