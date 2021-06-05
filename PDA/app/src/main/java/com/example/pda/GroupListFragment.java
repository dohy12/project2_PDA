package com.example.pda;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class GroupListFragment extends DialogFragment {
    private LinearLayout container;
    private LayoutInflater inflater;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;

        View v = inflater.inflate(R.layout.group_list_fragment, container, false);
        this.container = v.findViewById(R.id.container);

        setSearchButtonOnclick(v.findViewById(R.id.searchButton));

        return v;
    }

    private void setSearchButtonOnclick(View searchButton){
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showList();
            }
        });
    }

    private void showList(){
        for(int i=0;i<10;i++){
            View v = inflater.inflate(R.layout.group_list_fragment_content, null, false);
            container.addView(v);
        }
    }
}
