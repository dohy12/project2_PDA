package com.example.pda;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class Toolbar {
    public View view;
    View toolbarButton;
    View toolbarAlarm;
    ImageView toolbarProfile;
    Drawer drawer;

    int toolbarType;

    public Toolbar(View view, Drawer drawer, int toolbarType){
        this.view = view;
        this.drawer = drawer;
        this.toolbarType = toolbarType;

        toolbarButton = view.findViewById(R.id.toolbarButton1);
        toolbarAlarm = view.findViewById(R.id.toolbarAlarm);
        toolbarProfile = view.findViewById(R.id.profile_image);

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
            ((ImageView)toolbarButton).setImageDrawable(toolbarProfile.getResources().getDrawable(R.drawable.icon10, null));
        }

    }

    public void setToolbarProfile(){
        toolbarProfile.setClipToOutline(true);
    }


}
