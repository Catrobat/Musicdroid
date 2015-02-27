package org.catrobat.musicdroid.pocketmusic.instrument;

import android.app.Fragment;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;

public abstract class AbstractPocketMusicFragment extends Fragment {

    public static int X_INDEX = 0;
    public static int Y_INDEX = 1;

    protected int getActionBarHeight() {
        TypedValue tv = new TypedValue();
        getActivity().getApplicationContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
        return getResources().getDimensionPixelSize(tv.resourceId);
    }

    private int[] initializeDisplay() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
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

    protected int getDisplayHeight() {
        return initializeDisplay()[Y_INDEX];
    }
}