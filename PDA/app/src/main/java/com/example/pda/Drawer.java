package com.example.pda;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Drawer {
    public View view;
    View menus[];
    AppCompatActivity activity;

    public Drawer(View v, AppCompatActivity activity){
        this.view = v;
        this.activity = activity;

        v.setBackgroundColor(Color.parseColor("#FFFFFF"));

        ImageView iv = view.findViewById(R.id.profile_image);
        iv.setClipToOutline(true);

        int[] menuID = {
                R.id.drawer_menu1,
                R.id.drawer_menu2,
                R.id.drawer_menu3,
                R.id.drawer_menu4,
                R.id.drawer_menu5,
                R.id.drawer_menu6,
                R.id.drawer_menu7,
                R.id.drawer_menu8
        };

        setMenus(menuID);
    }


    public void setMenus(int[] menuID){
        menus = new View[menuID.length];

        for(int i=0; i<menuID.length; i++){
            menus[i] = view.findViewById(menuID[i]);

            ImageView iconImage = menus[i].findViewById(R.id.icon);
            iconImage.setColorFilter(Color.parseColor("#909090"));
        }
    }

    public void goMemList(View view){
        Intent intent = new Intent(activity, memberList.class);
        activity.startActivity(intent);
    }

    public void goBoard1(View view){
        Intent intent = new Intent(activity, Board.class);
        activity.startActivity(intent);
    }

    public void goBoard2(View view){
        Intent intent = new Intent(activity, Board.class);
        activity.startActivity(intent);
    }

    public void goIntroduction(View view){
        Intent intent = new Intent(activity, Introduction.class);
        activity.startActivity(intent);
    }

    public void goMembershipFee(View view){
        Intent intent = new Intent(activity, MembershipFee.class);
        activity.startActivity(intent);
    }


}
