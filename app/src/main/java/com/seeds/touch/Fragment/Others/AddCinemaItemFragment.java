package com.seeds.touch.Fragment.Others;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seeds.touch.R;

public class AddCinemaItemFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_cinema_item, container, false);
        findViews(view);
        handleListeners(view);
        return view;
    }

    private void handleListeners(View view) {
    }

    private void findViews(View view) {
    }
}
