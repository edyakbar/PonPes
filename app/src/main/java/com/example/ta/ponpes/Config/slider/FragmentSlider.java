package com.example.ta.ponpes.Config.slider;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ta.ponpes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSlider extends Fragment {
    private static final String ARG_PARAM1 = "params";

    private String imageUrls;

    public FragmentSlider() {
    }

    public static FragmentSlider newInstance(String params) {
        FragmentSlider fragment = new FragmentSlider();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, params);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        imageUrls = getArguments().getString(ARG_PARAM1);
        View view = inflater.inflate(R.layout.fragment_slider_item, container, false);
        ImageView img = (ImageView) view.findViewById(R.id.img);
        Glide.with(getActivity()).load(imageUrls).into(img);
        return view;
    }

}