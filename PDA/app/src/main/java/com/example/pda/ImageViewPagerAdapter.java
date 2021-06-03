package com.example.pda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ImageViewPagerAdapter extends PagerAdapter {

    private Context mContext = null;
    private int count;


    public ImageViewPagerAdapter(Context mContext, int count){
        this.mContext = mContext;
        this.count = count;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = null;

        if (mContext!=null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.album_content_layout, container, false);

            ImageView imageView = view.findViewById(R.id.image);
        }

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View)object);
    }

}
