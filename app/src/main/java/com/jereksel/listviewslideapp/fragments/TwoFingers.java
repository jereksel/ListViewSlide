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

public class TwoFingers extends Fragment {

    SlidableListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.listlayout, container, false);


        listView = (SlidableListView) rootView.findViewById(R.id.listview);

        ArrayList<String> data = new ArrayList<String>();

        for (int i = 0; i < 21; i++) {
            data.add("Setting " + i);
        }

        listView.addClass(CheckBox.class);

        listView.setAdapter(new Adapter(getActivity().getBaseContext(), data));

        listView.setMode(SlidableListView.Mode.TWO_FINGERS);

        return rootView;

    }

}
