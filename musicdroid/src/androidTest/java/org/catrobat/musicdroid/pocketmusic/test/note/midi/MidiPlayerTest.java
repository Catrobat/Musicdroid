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

package org.catrobat.musicdroid.pocketmusic.test.note.midi;

import android.test.AndroidTestCase;

import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.Track;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiException;
import org.catrobat.musicdroid.pocketmusic.test.instrument.InstrumentActivityMock;
import org.catrobat.musicdroid.pocketmusic.test.note.TrackTestDataFactory;

import java.io.File;
import java.io.IOException;

public class MidiPlayerTest extends AndroidTestCase {

    private static final File CACHE_DIR = new File("");

    private static final int MIDI_RESOURCE_ID = 0;

    private MidiPlayerMock player;
    private InstrumentActivityMock activity;

    @Override
    protected void setUp() {
        player = new MidiPlayerMock();
        activity = new InstrumentActivityMock();
    }

    public void testStop1() {
        player.playNote(activity, MIDI_RESOURCE_ID);
        player.stop();

        assertPlayNote(0, false);
    }

    public void testStop2() {
        player.playNote(activity, MIDI_RESOURCE_ID);
        player.playNote(activity, MIDI_RESOURCE_ID);

        player.stop();

        assertPlayNote(0, false);
    }

    public void testPlayNote() {
        player.playNote(activity, MIDI_RESOURCE_ID);

        assertPlayNote(0, true);
    }

    public void testPlayNoteSingleNotPlaying() {
        player.playNote(activity, MIDI_RESOURCE_ID);
        player.setPlaying(false);

        assertPlayNote(0, false);
    }

    public void testPlayNoteQueue() {
        player.playNote(activity, MIDI_RESOURCE_ID);
        player.playNote(activity, MIDI_RESOURCE_ID);

        assertPlayNote(1, true);
    }

    public void testPlayNoteQueueWithStop1() {
        player.playNote(activity, MIDI_RESOURCE_ID);
        player.setPlaying(false);
        player.playNote(activity, MIDI_RESOURCE_ID);

        assertPlayNote(0, true);
    }

    public void testPlayNoteQueueWithStop2() {
        player.playNote(activity, MIDI_RESOURCE_ID);
        player.playNote(activity, MIDI_RESOURCE_ID);
        player.setPlaying(false);
        player.playNote(activity, MIDI_RESOURCE_ID);

        assertPlayNote(2, false);
    }

    public void testPlayNoteCompleteEvent1() {
        player.playNote(activity, MIDI_RESOURCE_ID);
        player.onPlayNoteComplete(activity);

        assertPlayNote(0, false);
    }

    public void testPlayNoteCompleteEvent2() {
        player.playNote(activity, MIDI_RESOURCE_ID);
        player.playNote(activity, MIDI_RESOURCE_ID);
        player.onPlayNoteComplete(activity);

        assertPlayNote(0, true);
    }

    public void testPlayNoteCompleteEvent3() {
        player.playNote(activity, MIDI_RESOURCE_ID);
        player.playNote(activity, MIDI_RESOURCE_ID);
        player.playNote(activity, MIDI_RESOURCE_ID);
        player.onPlayNoteComplete(activity);

        assertPlayNote(1, true);
    }

    public void testPlayNoteCompleteEvent4() {
        player.playNote(activity, MIDI_RESOURCE_ID);
        player.setPlaying(false);
        player.onPlayNoteComplete(activity);

        assertPlayNote(0, false);
    }

    private void assertPlayNote(int expectedQueueSize, boolean expectedIsPlaying) {
        assertEquals(expectedQueueSize, player.getPlayQueueSize());
        assertEquals(expectedIsPlaying, player.isPlaying());
    }

    public void testPlayTrack1() throws IOException, MidiException {
        Track track = TrackTestDataFactory.createSimpleTrack();
        player.playTrack(activity, CACHE_DIR, track, Project.DEFAULT_BEATS_PER_MINUTE);

        assertEquals(0, player.getPlayQueueSize());
        assertEquals(true, player.isPlaying());
    }

    public void testPlayTrackCompleteEvent() throws IOException, MidiException {
        Track track = TrackTestDataFactory.createSimpleTrack();
        FileMock tempFileToPlay = new FileMock();
        player.playTrack(activity, CACHE_DIR, track, Project.DEFAULT_BEATS_PER_MINUTE);
        player.onPlayTrackComplete(tempFileToPlay, activity);

        assertTrue(tempFileToPlay.isDeleted());
    }

    private class FileMock extends File {

        private boolean isDeleted;

        public FileMock() {
            super("");
            isDeleted = false;
        }

        public boolean isDeleted() {
            return isDeleted;
        }

        @Override
        public boolean delete() {
            isDeleted = true;

            return isDeleted;
        }
    }
}
