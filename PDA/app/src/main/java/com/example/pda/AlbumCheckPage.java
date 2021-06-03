package com.example.pda;

import android.content.Context;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class AlbumCheckPage {
    private Context mContext;
    private LinearLayout container;
    private int count;
    private int position;
    private ArrayList<ImageView> pageCheckerList;

    public AlbumCheckPage(Context mContext, LinearLayout container, int count){
        this.mContext = mContext;
        this.container = container;
        this.count = count;
        position = 0;

        pageCheckerList = new ArrayList<>();

        createPageChecker();
        setPageChecker(0);
    }

    public int getCount() {
        return count;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private void createPageChecker(){
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, mContext.getResources().getDisplayMetrics());
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, mContext.getResources().getDisplayMetrics());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                size, size, 0);

        layoutParams.setMargins(margin,0,margin,0);

        for(int i=0; i<count; i++){
            ImageView iv = new ImageView(mContext);
            iv.setLayoutParams(layoutParams);

            container.addView(iv);
            pageCheckerList.add(iv);
        }

        return;
    }

    public void setPageChecker(int pos){
        this.position = pos;

        for(int i=0;i<count;i++){
            ImageView iv = pageCheckerList.get(i);
            if(i!=pos)
                iv.setImageResource(R.drawable.icon19);
            else
                iv.setImageResource(R.drawable.icon18);
        }

        return;
    }
}
