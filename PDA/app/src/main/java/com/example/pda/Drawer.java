package com.example.pda;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Drawer {
    View view;

    public Drawer(View v){
        this.view = v;
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

        for(int i=0; i<menuID.length; i++){
            View menuView = view.findViewById(menuID[i]);

            ImageView iconImage = menuView.findViewById(R.id.icon);
            iconImage.setColorFilter(Color.parseColor("#909090"));
        }


    }


}
