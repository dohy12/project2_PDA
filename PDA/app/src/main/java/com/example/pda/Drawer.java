package com.example.pda;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

public class Drawer {
    public View view;
    View menus[];

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
        menus = new View[menuID.length];

        for(int i=0; i<menuID.length; i++){
            menus[i] = view.findViewById(menuID[i]);

            ImageView iconImage = menus[i].findViewById(R.id.icon);
            iconImage.setColorFilter(Color.parseColor("#909090"));
        }
    }


}
