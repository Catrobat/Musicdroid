/*
 * Musicdroid: An on-device music generator for Android
 * Copyright (C) 2010-2015 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.musicdroid.pocketmusic;

import android.content.Context;
import android.widget.Toast;

/**
 * Class to manage toasts shown while playing/stopping or when the track is done playing
 */
public class ToastDisplayer {

    private static Toast playToast;
    private static Toast stopToast;
    private static Toast doneToast;

    private static void clearAllToasts() {
        if (playToast != null)
            playToast.cancel();
        if (stopToast != null)
            stopToast.cancel();
        if (doneToast != null)
            doneToast.cancel();
    }

    public static void showPlayToast(Context context) {
        clearAllToasts();
        playToast = Toast.makeText(context, context.getString(R.string.playing), Toast.LENGTH_LONG);
        playToast.show();
    }

    public static void showStopToast(Context context) {
        clearAllToasts();
        stopToast = Toast.makeText(context, context.getString(R.string.stopped), Toast.LENGTH_LONG);
        stopToast.show();
    }

    public static void showDoneToast(Context context) {
        clearAllToasts();
        doneToast = Toast.makeText(context, context.getString(R.string.done), Toast.LENGTH_LONG);
        doneToast.show();
    }
}
