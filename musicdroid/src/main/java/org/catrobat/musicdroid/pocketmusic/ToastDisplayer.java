package org.catrobat.musicdroid.pocketmusic;

import android.content.Context;
import android.widget.Toast;

import org.catrobat.musicdroid.pocketmusic.note.midi.MidiPlayer;

/**
 * Class to manage toasts shown while playing/stopping or when the track is done playing
 */
public class ToastDisplayer {
    public static final String STARTED_PLAYING = "Playing";
    public static final String STOPPED_PLAYING = "Stopped";
    public static final String FINISHED_PLAYING = "Done";

    private static Toast playToast;
    private static Toast stopToast;
    private static Toast doneToast;

    private static void clearAllToasts() {
        if(playToast!=null)
            playToast.cancel();
        if(stopToast!=null)
            stopToast.cancel();
        if(doneToast!=null)
            doneToast.cancel();
    }

    public static void showPlayToast(Context context) {
        clearAllToasts();
        playToast = Toast.makeText(context,STARTED_PLAYING,Toast.LENGTH_LONG);
        playToast.show();
    }

    public static void showStopToast(Context context) {
        clearAllToasts();
        stopToast = Toast.makeText(context,STOPPED_PLAYING,Toast.LENGTH_LONG);
        stopToast.show();
    }

    public static void showDoneToast(Context context) {
        clearAllToasts();
        doneToast = Toast.makeText(context,FINISHED_PLAYING,Toast.LENGTH_LONG);
        doneToast.show();
    }
}
