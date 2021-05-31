package com.example.pda;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Callable;

public class Drawer {
    public View view;
    View menus[];
    AppCompatActivity activity;

    Callable[] cmdList;

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

        cmdList = new Callable[8];
        cmdList[0] = new goMyPage();
        cmdList[1] = new goBoard1();
        cmdList[2] = new goBoard2();
        cmdList[3] = new goAlbum();
        cmdList[4] = new goMembershipFee();
        cmdList[5] = new goIntroduction();
        cmdList[6] = new goConfig();
        cmdList[7] = new logout();

        setMenus(menuID);
    }


    public void setMenus(int[] menuID){
        menus = new View[menuID.length];

        for(int i=0; i<menuID.length; i++){
            menus[i] = view.findViewById(menuID[i]);

            ImageView iconImage = menus[i].findViewById(R.id.icon);
            iconImage.setColorFilter(Color.parseColor("#909090"));

            final int finalI = i;
            menus[i].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Toast myToast = Toast.makeText(activity,"test", Toast.LENGTH_SHORT);
                    myToast.show();
                    try {
                        cmdList[finalI].call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    class goMyPage implements Callable{
        @Override
        public Object call() throws Exception {
            Intent intent = new Intent(activity, Mypage.class);
            activity.startActivity(intent);
            return null;
        }
    }

    class goBoard1 implements Callable{
        @Override
        public Object call() throws Exception {
            Intent intent = new Intent(activity, Board.class);
            activity.startActivity(intent);
            return null;
        }
    }

    class goBoard2 implements Callable{
        @Override
        public Object call() throws Exception {
            Intent intent = new Intent(activity, Board.class);
            activity.startActivity(intent);
            return null;
        }
    }

    class goMembershipFee implements Callable{
        @Override
        public Object call() throws Exception {
            Intent intent = new Intent(activity, MembershipFee.class);
            activity.startActivity(intent);
            return null;
        }
    }

    class goIntroduction implements Callable{
        @Override
        public Object call() throws Exception {
            Intent intent = new Intent(activity, Introduction.class);
            activity.startActivity(intent);
            return null;
        }
    }

    class goAlbum implements Callable{
        @Override
        public Object call() throws Exception {
            return null;
        }
    }

    class goConfig implements Callable{
        @Override
        public Object call() throws Exception {
            return null;
        }
    }

    class logout implements Callable{
        @Override
        public Object call() throws Exception {
            activity.finish();
            return null;
        }
    }

}
