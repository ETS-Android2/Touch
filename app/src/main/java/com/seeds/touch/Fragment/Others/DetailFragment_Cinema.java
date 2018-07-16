package com.seeds.touch.Fragment.Others;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seeds.touch.R;


public class DetailFragment_Cinema extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_detail_fragment__cinema, container, false);
        findViews(view);
        manageListeners(view);

        return view;
    }

    private void manageListeners(View view) {
    }

    private void findViews(View view) {
    }
}
