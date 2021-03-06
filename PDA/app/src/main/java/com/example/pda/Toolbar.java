package com.example.pda;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.concurrent.Callable;

public class Toolbar {
    public View view;
    View toolbarButton;
    View toolbarAlarm;
    View toolbarProfile;
    Drawer drawer;
    AppCompatActivity activity;

    int toolbarType;

    public Toolbar(View view, Drawer drawer, int toolbarType, AppCompatActivity activity){
        this.view = view;
        this.drawer = drawer;
        this.toolbarType = toolbarType;
        this.activity = activity;

        toolbarButton = view.findViewById(R.id.toolbarButton1);
        toolbarAlarm = view.findViewById(R.id.toolbarAlarm);
        toolbarProfile = view.findViewById(R.id.toolbar_profile_image);
        ((TextView) view.findViewById(R.id.toolbarGname)).setText(app.getGroupName());
        setToolbarButton();
        setToolbarProfile();
    }

    public void setToolbarButton(){
        if (toolbarType == 1){
            toolbarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((DrawerLayout)drawer.view.getParent()).openDrawer(Gravity.LEFT);
                }
            });
        }
        if (toolbarType == 2){
            ((ImageView)toolbarButton.findViewById(R.id.toolbarImage)).setImageDrawable(toolbarProfile.getResources().getDrawable(R.drawable.icon15, null));

            toolbarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.finish();
                }
            });
        }

    }

    public void setToolbarProfile(){
        ((ImageView)toolbarProfile.findViewById(R.id.toolbarImage)).setImageBitmap(app.getProfile());
        toolbarProfile.findViewById(R.id.toolbarImage).setClipToOutline(true);

        toolbarProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goMypage();
            }
        });
    }

    public void goMypage(){
        Intent intent = new Intent(activity, Mypage.class);
        intent.putExtra("Name",app.getName());
        intent.putExtra("Phone",app.getPhone());
        intent.putExtra("Email",app.getEmail());
        intent.putExtra("Intro",app.getIntro());
        intent.putExtra("Age",app.getAge());
        intent.putExtra("UID",app.getUid());
        intent.putExtra("Receiver",app.getUid());
        intent.putExtra("profile",app.getProfileString());
        activity.startActivity(intent);
    }

    public AppCompatActivity getActivity() {
        return activity;
    }
}
