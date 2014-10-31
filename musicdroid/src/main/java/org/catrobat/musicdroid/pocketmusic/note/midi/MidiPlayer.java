/*
 * Musicdroid: An on-device music generator for Android
 * Copyright (C) 2010-2014 The Catrobat Team
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

package org.catrobat.musicdroid.pocketmusic.note.midi;

import android.app.Activity;
import android.media.MediaPlayer;

import org.catrobat.musicdroid.pocketmusic.note.NoteEvent;
import org.catrobat.musicdroid.pocketmusic.note.NoteName;

import java.util.LinkedList;
import java.util.Queue;

public class MidiPlayer {

    private static final String R_RAW = "raw";

    protected Queue<NoteName> playQueue;
    protected MediaPlayer player;

    public MidiPlayer() {
        playQueue = new LinkedList<NoteName>();
    }

    public void play(NoteName noteName, Activity activity) {
        if ((null == player) || (false == player.isPlaying())) {
            createAndStartPlayer(activity, noteName);
        } else {
            playQueue.add(noteName);
        }
    }

    private void createAndStartPlayer(final Activity activity, final NoteName noteName) {
        int midiFileId = getMidiResourceId(activity, noteName);

        player = createPlayer(activity, midiFileId);

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                restartPlayerThroughPlayQueue(activity);
            }
        });

        player.start();
    }

    protected void restartPlayerThroughPlayQueue(Activity activity) {
        if (false == playQueue.isEmpty()) {
            createAndStartPlayer(activity, playQueue.poll());
        }
    }

    protected int getMidiResourceId(final Activity activity, final NoteName noteName) {
        return activity.getResources().getIdentifier(noteName.toString().toLowerCase(), R_RAW, activity.getPackageName());
    }

    protected MediaPlayer createPlayer(Activity activity, int midiFileId) {
        return MediaPlayer.create(activity, midiFileId);
    }
}
