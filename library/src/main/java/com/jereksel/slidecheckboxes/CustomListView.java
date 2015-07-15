package com.jereksel.slidecheckboxes;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.Objects;

public class CustomListView extends ListView {

    View viewBackup;

    boolean swipeMode = false;

    public CustomListView(Context context) {
        super(context);
    }

    public CustomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            viewBackup = null;
        }

        if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            Log.v("Swipe", "Swipe mode deactivated");
            swipeMode = false;
            onSwipingEnd();
        }

        float x = event.getX();
        float y = event.getY();

        int position = pointToPosition((int) x, (int) y);

        //http://stackoverflow.com/questions/6766625/listview-getchildat-returning-null-for-visible-children
        position = position - getFirstVisiblePosition();

        // Log.d("Position: ", String.valueOf(position));

        //Poszukiwany wiersz
        View v = getChildAt(position);

        if (v != null) {

            for (int i = 0; i < ((ViewGroup) v).getChildCount(); i++) {

                View children = ((ViewGroup) v).getChildAt(i);

                if (children instanceof CheckBox) {

                    Rect frame = new Rect();

                    children.getHitRect(frame);

                    //Listview row position
                    float newX = x - v.getX();
                    float newY = y - v.getY();


                    if (frame.contains((int) newX, (int) newY) && !Objects.equals(viewBackup, children)) {

                        if (!swipeMode) {
                            Log.v("Swipe", "Swipe mode activated");
                            onSwipingStart();
                            swipeMode = true;
                        }

                        viewBackup = children;
                        children.performClick();
                    }

                    if (!frame.contains((int) newX, (int) newY)) {
                        viewBackup = null;
                    }

                }
            }


        }

        if (!swipeMode) {
            super.onTouchEvent(event);
        }

        return true;


    }


    //Callbacks
    private void onSwipingStart() {
    }

    private void onSwipingEnd() {
    }

}
