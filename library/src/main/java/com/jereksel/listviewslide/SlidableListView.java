package com.jereksel.listviewslide;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;

public class SlidableListView extends ListView {

    public enum Mode {
        ONE_FINGER,
        TWO_FINGERS;
    }

    Mode mode = Mode.ONE_FINGER;

    View viewBackup;

    //It will be false by default
    boolean swipeMode;

    ArrayList<Class> choosable = new ArrayList<Class>();

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

        switch (mode) {

            case ONE_FINGER:
                return oneFinger(event);

            case TWO_FINGERS:
                return twoFingers(event);

            default:
                return true;

        }

    }

    //TODO: Use design pattern (Strategy maybe?)

    private boolean oneFinger(MotionEvent event) {


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

                if (choosable.contains(children.getClass())) {

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

    private boolean twoFingers(MotionEvent event) {


        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            onSwipingStart();
        }

        if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            onSwipingEnd();
        }

        //Two fingers
        if (event.getPointerCount() == 2) {
            onSwipingEnd();
        }

        if (swipeMode) {

            float x = event.getX();
            float y = event.getY();

            int position = pointToPosition((int) x, (int) y);

            //http://stackoverflow.com/questions/6766625/listview-getchildat-returning-null-for-visible-children
            position = position - getFirstVisiblePosition();

            //Poszukiwany wiersz
            View v = getChildAt(position);

            if (v != null) {

                for (int i = 0; i < ((ViewGroup) v).getChildCount(); i++) {

                    View children = ((ViewGroup) v).getChildAt(i);

                    if (choosable.contains(children.getClass())) {

                        Rect frame = new Rect();

                        children.getHitRect(frame);

                        //Finger location relative to listview row
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

        } else {
            super.onTouchEvent(event);
        }

        return true;


    }


    public void addClass(Class<? extends View> clazz) {
        choosable.add(clazz);
    }

    public void clearClasses() {
        choosable.clear();
    }

    public void setOnStart(StateChangeListener onStart) {
        this.onStart = onStart;
    }

    public void setOnEnd(StateChangeListener onEnd) {
        this.onEnd = onEnd;
    }

    //Callbacks
    private void onSwipingStart() {

        viewBackup = null;
        swipeMode = true;

        if (onStart != null) {
            onStart.onChange();
        }

    }

    private void onSwipingEnd() {

        viewBackup = null;
        swipeMode = false;

        if (onEnd != null) {
            onEnd.onChange();
        }

    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }
}
