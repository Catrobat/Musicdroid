package org.catrobat.musicdroid.pocketmusic;

import android.content.Context;
import android.widget.Toast;

import org.catrobat.musicdroid.pocketmusic.note.midi.MidiPlayer;

public class ToastDisplayer {

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
        playToast = Toast.makeText(context,R.string.action_midi_playing,Toast.LENGTH_LONG);
        playToast.show();
    }

    public static void showStopToast(Context context) {
        clearAllToasts();
        stopToast = Toast.makeText(context,R.string.action_midi_stopped,Toast.LENGTH_LONG);
        stopToast.show();
    }

    public static void showDoneToast(Context context) {
        clearAllToasts();
        doneToast = Toast.makeText(context,R.string.action_midi_finished,Toast.LENGTH_LONG);
        doneToast.show();
    }
}
