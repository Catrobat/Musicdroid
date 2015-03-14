package org.catrobat.musicdroid.pocketmusic.tools;

import android.app.Activity;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;

public class DisplayMeasurements {

    public static int X_INDEX = 0;
    public static int Y_INDEX = 1;

    private Activity activity;

    public DisplayMeasurements(Activity activity) {
        this.activity = activity;
    }

    public int getActionBarHeight() {
        TypedValue tv = new TypedValue();
        activity.getApplicationContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
        return activity.getResources().getDimensionPixelSize(tv.resourceId);
    }

    private int[] initializeDisplay() {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int[] widthAndHeight = new int[2];
        widthAndHeight[X_INDEX] = size.x;
        widthAndHeight[Y_INDEX] = size.y;

        return widthAndHeight;
    }

    public int getDisplayWidth() {
        return initializeDisplay()[X_INDEX];
    }

    public int getDisplayHeight() {
        return initializeDisplay()[Y_INDEX];
    }

    public int getHalfDisplayHeight() {
        return (getDisplayHeight() - getActionBarHeight()) / 2;
    }
}
