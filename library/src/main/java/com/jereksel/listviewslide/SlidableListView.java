package com.jereksel.listviewslide;

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

public class SlidableListView extends ListView {

    View viewBackup;

    boolean swipeMode = false;

    StateChangeListener onStart;
    StateChangeListener onEnd;

    public SlidableListView(Context context) {
        super(context);
    }

    public SlidableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SlidableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            viewBackup = null;
        }

        if (event.getActionMasked() == MotionEvent.ACTION_UP) {
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
                            onSwipingStart();
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

    public void setOnStart(StateChangeListener onStart) {
        this.onStart = onStart;
    }

    public void setOnEnd(StateChangeListener onEnd) {
        this.onEnd = onEnd;
    }

    //Callbacks
    private void onSwipingStart() {

        swipeMode = true;

        if(onStart != null) {
            onStart.onChange();
        }

    }

    private void onSwipingEnd() {

        swipeMode = false;

        if(onEnd != null) {
            onEnd.onChange();
        }

    }

}
