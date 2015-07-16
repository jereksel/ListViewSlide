package com.jereksel.listviewslideapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import com.jereksel.listviewslide.SlidableListView;
import com.jereksel.listviewslideapp.Adapter;
import com.jereksel.listviewslideapp.R;

import java.util.ArrayList;

public class OneFinger extends Fragment {

    public static OneFinger mainActivity;
    SlidableListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.listlayout, container, false);

        mainActivity = this;

        listView = (SlidableListView) rootView.findViewById(R.id.listview);

        ArrayList<String> data = new ArrayList<String>();

        for (int i = 0; i < 21; i++) {
            data.add("Setting " + i);
        }

        listView.addClass(CheckBox.class);

        listView.setAdapter(new Adapter(getActivity().getBaseContext(), data));

        return rootView;

    }

}
