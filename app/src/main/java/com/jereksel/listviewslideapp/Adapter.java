package com.jereksel.listviewslideapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.List;

public class Adapter extends ArrayAdapter<String> {

    private final MainActivity context;
    private final List<String> messages;
    private boolean[] checked;

    static class ViewHolder {
        public TextView text;
        public CheckBox button;
    }

    public Adapter(MainActivity activity, List<String> messages) {
        super(activity, R.layout.rowlayout, messages);
        this.context = activity;
        this.messages = messages;
        checked = new boolean[messages.size()];
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.rowlayout, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) rowView.findViewById(R.id.label);
            viewHolder.button = (CheckBox) rowView
                    .findViewById(R.id.checkbox);
            rowView.setTag(viewHolder);
        }


        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();

        holder.text.setText(messages.get(position));

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked[position] = !checked[position];
            }
        });

        holder.button.setChecked(checked[position]);

        return rowView;
    }

}
