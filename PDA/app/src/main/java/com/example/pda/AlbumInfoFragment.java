package com.example.pda;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AlbumInfoFragment extends DialogFragment {
    LinearLayout view;

    private String title;
    private String location;
    private String intro;
    private String date;

    public AlbumInfoFragment(String title, String loc, String intro, String date){
        this.title = title;
        this.location = loc;
        this.intro = intro;

        int yy = Integer.parseInt(date.substring(0,4));
        int mm = Integer.parseInt(date.substring(4,6));
        int dd = Integer.parseInt(date.substring(6,8));

        this.date = String.format("%d-%02d-%02d",yy,mm,dd);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.album_detail, container, false);
        view = (LinearLayout)v;

        ((TextView)view.findViewById(R.id.album_title)).setText(title);
        ((TextView)view.findViewById(R.id.album_location)).setText(location);
        ((TextView)view.findViewById(R.id.album_info)).setText(intro);
        ((TextView)view.findViewById(R.id.album_date)).setText(date);
        return v;
    }


}
