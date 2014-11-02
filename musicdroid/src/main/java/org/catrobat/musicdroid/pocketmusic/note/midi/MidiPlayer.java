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
import android.net.Uri;

import org.catrobat.musicdroid.pocketmusic.note.NoteName;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.Track;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class MidiPlayer {

    private static final String TEMP_PLAY_FILE_NAME = "tmp_play.midi";
    private static final String R_RAW = "raw";

    private static MidiPlayer instance;

    protected MediaPlayer player;
    protected Queue<NoteName> playQueue;

    protected MidiPlayer() {
        playQueue = new LinkedList<NoteName>();
    }

    public static MidiPlayer getInstance() {
        if (null == instance) {
            instance = new MidiPlayer();
        }

        return instance;
    }

    public void playNote(Activity activity, NoteName noteName) {
        if ((null == player) || (false == player.isPlaying())) {
            createAndStartPlayer(activity, noteName);
        } else {
            playQueue.add(noteName);
        }
    }

    public void playTrack(Activity activity, Track track, int beatsPerMinute) throws IOException, MidiException {
        File tempPlayFile = createTempPlayFile(activity, track, beatsPerMinute);

        player = createPlayer(activity, tempPlayFile);
        player.start();
    }

    protected File createTempPlayFile(Activity activity, Track track, int beatsPerMinute) throws IOException, MidiException {
        File tempPlayFile = new File(activity.getApplicationContext().getCacheDir(), TEMP_PLAY_FILE_NAME);
        Project project = new Project(beatsPerMinute);
        project.addTrack(track);
        ProjectToMidiConverter converter = new ProjectToMidiConverter();
        converter.writeProjectAsMidi(project, tempPlayFile);

        return tempPlayFile;
    }

    private void createAndStartPlayer(final Activity activity, final NoteName noteName) {
        int midiFileId = getMidiResourceId(activity, noteName);

        player = createPlayer(activity, midiFileId);
        player.start();
    }

    protected int getMidiResourceId(final Activity activity, final NoteName noteName) {
        return activity.getResources().getIdentifier(noteName.toString().toLowerCase(), R_RAW, activity.getPackageName());
    }

    protected MediaPlayer createPlayer(final Activity activity, int midiFileId) {
        MediaPlayer player = MediaPlayer.create(activity, midiFileId);

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                restartPlayerThroughQueue(activity);
            }
        });

        return player;
    }

    protected void restartPlayerThroughQueue(final Activity activity) {
        if (false == playQueue.isEmpty()) {
            createAndStartPlayer(activity, playQueue.poll());
        }
    }

    protected MediaPlayer createPlayer(Activity activity, final File tempPlayFile) {
        MediaPlayer player = MediaPlayer.create(activity, Uri.parse(tempPlayFile.getAbsolutePath()));

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                tempPlayFile.delete();
            }
        });

        return player;
    }
}
