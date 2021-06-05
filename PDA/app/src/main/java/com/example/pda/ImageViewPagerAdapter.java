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

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImageViewPagerAdapter extends PagerAdapter {

    private Context mContext = null;
    private int count;

    ArrayList<String> imageUrlList;


    public ImageViewPagerAdapter(Context mContext, ArrayList<String> imageUrlList){
        this.mContext = mContext;
        this.imageUrlList = imageUrlList;
        count = imageUrlList.size();
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = null;

        if (mContext!=null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.album_content_layout, container, false);

            ImageView imageView = view.findViewById(R.id.image);
            String imageUrl = imageUrlList.get(position);
            Glide.with(mContext).load(imageUrl).into(imageView);
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
