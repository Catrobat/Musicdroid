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
import org.catrobat.musicdroid.pocketmusic.test.instrument.InstrumentActivityTestDataFactory;
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
        player = MidiPlayerTestDataFactory.createMidiPlayer();
        activity = InstrumentActivityTestDataFactory.createInstrumentActivity();
    }

    public void testStop() {
        player.playNote(activity, MIDI_RESOURCE_ID);
        player.stop();

        assertPlayNote(0, false);
    }

    public void testPlayNote1() {
        player.playNote(activity, MIDI_RESOURCE_ID);

        assertPlayNote(0, true);
    }

    public void testPlayNote2() {
        player.playNote(activity, MIDI_RESOURCE_ID);
        player.setPlaying(false);

        assertPlayNote(0, false);
    }

    public void testPlayNote3() {
        player.playNote(activity, MIDI_RESOURCE_ID);
        player.playNote(activity, MIDI_RESOURCE_ID);

        assertPlayNote(1, true);
    }

    public void testPlayNote4() {
        player.playNote(activity, MIDI_RESOURCE_ID);
        player.setPlaying(false);
        player.playNote(activity, MIDI_RESOURCE_ID);

        assertPlayNote(0, true);
    }

    public void testPlayNote5() {
        player.playNote(activity, MIDI_RESOURCE_ID);
        player.playNote(activity, MIDI_RESOURCE_ID);
        player.setPlaying(false);
        player.playNote(activity, MIDI_RESOURCE_ID);

        assertPlayNote(2, false);
    }

    public void testPlayNote6() {
        player.playNote(activity, MIDI_RESOURCE_ID);
        player.onPlayNoteComplete(activity);

        assertPlayNote(0, false);
    }

    public void testPlayNote7() {
        player.playNote(activity, MIDI_RESOURCE_ID);
        player.playNote(activity, MIDI_RESOURCE_ID);
        player.onPlayNoteComplete(activity);

        assertPlayNote(0, true);
    }

    public void testPlayNote8() {
        player.playNote(activity, MIDI_RESOURCE_ID);
        player.playNote(activity, MIDI_RESOURCE_ID);
        player.playNote(activity, MIDI_RESOURCE_ID);
        player.onPlayNoteComplete(activity);

        assertPlayNote(1, true);
    }

    public void testPlayNote9() {
        player.playNote(activity, MIDI_RESOURCE_ID);
        player.setPlaying(false);
        player.onPlayNoteComplete(activity);

        assertPlayNote(0, false);
    }

    private void assertPlayNote(int expectedQueueSize, boolean expectedIsPlaying) {
        assertEquals("The queue size is not as expected",
                expectedQueueSize, player.getPlayQueueSize());
        assertEquals("The player is not playing as expected", expectedIsPlaying, player.isPlaying());
    }

    public void testPlayTrack1() throws IOException, MidiException {
        Track track = TrackTestDataFactory.createSimpleTrack();
        player.playTrack(activity, CACHE_DIR, track, Project.DEFAULT_BEATS_PER_MINUTE);

        assertEquals("The size of the queue should be 0", 0, player.getPlayQueueSize());
        assertPlayTrack(true);
    }

    public void testPlayTrack2() throws IOException, MidiException {
        Track track = TrackTestDataFactory.createSimpleTrack();
        FileMock tempFileToPlay = new FileMock();
        player.playTrack(activity, CACHE_DIR, track, Project.DEFAULT_BEATS_PER_MINUTE);
        player.onPlayTrackComplete(activity, tempFileToPlay);

        assertTrue("THe file should be deleted", tempFileToPlay.isDeleted());
        assertTrue("The activity should be dismissed", activity.isDismissed());
        assertPlayTrack(player.isPlaying());
    }

    private void assertPlayTrack(boolean expectedIsPlaying) {
        assertEquals("The player should play or not play - as expected",
                expectedIsPlaying, player.isPlaying());
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
