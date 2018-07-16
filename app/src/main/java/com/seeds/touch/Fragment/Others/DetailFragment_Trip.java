package com.seeds.touch.Fragment.Others;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seeds.touch.R;

public class DetailFragment_Trip extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_detail_fragment__trip, container, false);

        findViews(view);
        manageListeners(view);
        return view;

    }
    private void manageListeners(View view) {
    }

    private void findViews(View view) {
    }
}
