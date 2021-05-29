package com.example.pda;

import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class Toolbar {
    public View view;
    View toolbarButton;
    View toolbarAlarm;
    Drawer drawer;

    public Toolbar(View v, Drawer d){
        this.view = v;
        this.drawer = d;

        toolbarButton = v.findViewById(R.id.toolbarButton1);
        toolbarAlarm = v.findViewById(R.id.toolbarAlarm);

        setToolbarButton();
    }

    public void setToolbarButton(){
        toolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DrawerLayout)drawer.view.getParent()).openDrawer(Gravity.LEFT);
            }
        });
    }

}
