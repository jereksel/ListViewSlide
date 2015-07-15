package com.jereksel.slidecheckboxesapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.jereksel.slidecheckboxes.CustomListView;
import com.jereksel.slidecheckboxesapp.R;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    public static MainActivity mainActivity;
    CustomListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = this;

        listView = (CustomListView) findViewById(R.id.listview);

        ArrayList<String> data = new ArrayList<String>();

        for (int i = 0; i < 11; i++) {
            data.add("Setting " + i);
        }

        listView.setAdapter(new Adapter(this, data));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
