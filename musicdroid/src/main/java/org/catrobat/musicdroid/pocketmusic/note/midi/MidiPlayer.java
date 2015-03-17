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

import org.catrobat.musicdroid.pocketmusic.ToastDisplayer;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.Track;
import org.catrobat.musicdroid.pocketmusic.projectselection.ProjectSelectionActivity;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class MidiPlayer {

    private static final String TEMP_PLAY_FILE_NAME = "tmp_play.midi";

    private static MidiPlayer instance;

    protected MediaPlayer player;
    protected Queue<Integer> playQueue;

    protected MidiPlayer() {
        playQueue = new LinkedList<Integer>();
    }

    public static MidiPlayer getInstance() {
        if (null == instance) {
            instance = new MidiPlayer();
        }

        return instance;
    }

    public boolean isPlaying() {
        if (player != null) {
            return player.isPlaying();
        }

        return false;
    }

    public void stop() {
        if ((null != player) && player.isPlaying()) {
            player.stop();
        }

        synchronized (playQueue) {
            playQueue.clear();
        }
    }

    public void clearPlayQueue() {
        synchronized (playQueue) {
            playQueue.clear();
        }
    }

    public void playNote(Activity activity, int midiResourceId) {
        synchronized (playQueue) {
            if ((null == player) || (false == player.isPlaying() && playQueue.isEmpty())) {
                createAndStartPlayer(activity, midiResourceId);
            } else {
                playQueue.add(midiResourceId);
            }
        }

    }

    public void playTrack(Activity activity, File cacheDirectory, Track track, int beatsPerMinute) throws IOException, MidiException {
        synchronized (playQueue) {
            playQueue.clear();
        }

        File tempPlayFile = new File(cacheDirectory, TEMP_PLAY_FILE_NAME);
        writeTempPlayFile(tempPlayFile, track, beatsPerMinute);

        player = createTrackPlayerWithOnCompletionListener(activity, tempPlayFile);
        player.start();
    }

    protected void writeTempPlayFile(File tempPlayFile, Track track, int beatsPerMinute) throws IOException, MidiException {
        Project project = new Project("temp", beatsPerMinute);
        project.addTrack("tempTrack", track);
        ProjectToMidiConverter converter = new ProjectToMidiConverter();
        converter.writeProjectAsMidi(project, tempPlayFile);
    }

    private void createAndStartPlayer(final Activity activity, final int midiResourceId) {
        player = createNotePlayerWithOnCompletionListener(activity, midiResourceId);
        player.start();
    }

    private MediaPlayer createNotePlayerWithOnCompletionListener(final Activity activity, final int midiResourceId) {
        MediaPlayer player = createNotePlayer(activity, midiResourceId);

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                onPlayNoteComplete(activity);
            }
        });

        return player;
    }

    protected MediaPlayer createNotePlayer(final Activity activity, final int midiResourceId) {
        return MediaPlayer.create(activity, midiResourceId);
    }

    protected void onPlayNoteComplete(final Activity activity) {
        synchronized (playQueue) {
            if (false == playQueue.isEmpty()) {
                createAndStartPlayer(activity, playQueue.poll());
            }
        }
    }

    private MediaPlayer createTrackPlayerWithOnCompletionListener(final Activity activity, final File tempPlayFile) {
        MediaPlayer player = createTrackPlayer(activity, Uri.parse(tempPlayFile.getAbsolutePath()));

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                onPlayTrackComplete(tempPlayFile, activity);
            }
        });

        return player;
    }

    protected void onPlayTrackComplete(File tempPlayFile, Activity activity) {
        tempPlayFile.delete();

        try {
            activity.invalidateOptionsMenu();
            ToastDisplayer.showDoneToast(activity.getApplicationContext());
            ProjectSelectionActivity projectSelectionActivity = (ProjectSelectionActivity) activity;
            projectSelectionActivity.notifyTrackPlayed();
        } catch (Exception e) {
        }
    }

    protected MediaPlayer createTrackPlayer(final Activity activity, final Uri uri) {
        return MediaPlayer.create(activity, uri);
    }

    public int getPlayQueueSize() {
        return playQueue.size();
    }
}
