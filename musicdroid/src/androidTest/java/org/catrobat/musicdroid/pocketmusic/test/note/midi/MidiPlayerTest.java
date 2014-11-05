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

import org.catrobat.musicdroid.pocketmusic.instrument.InstrumentActivity;
import org.catrobat.musicdroid.pocketmusic.note.NoteName;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.Track;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiException;
import org.catrobat.musicdroid.pocketmusic.test.instrument.InstrumentActivityTestDataFactory;
import org.catrobat.musicdroid.pocketmusic.test.note.TrackTestDataFactory;

import java.io.IOException;


public class MidiPlayerTest extends AndroidTestCase {

    private NoteName noteName;
    private MidiPlayerMock player;
    private InstrumentActivity activity;

    @Override
    protected void setUp() {
        noteName = NoteName.C4;
        player = MidiPlayerTestDataFactory.createMidiPlayer();
        activity = InstrumentActivityTestDataFactory.createInstrumentActivity();
    }

    public void testPlayNote1() {
        player.playNote(activity, noteName);

        assertTrue(player.isPlaying());
    }

    public void testPlayNote2() {
        player.playNote(activity, noteName);
        player.playNote(activity, noteName);

        int expectedQueueSize = 1;
        int actualQueueSize = player.getPlayQueue().size();

        assertEquals(expectedQueueSize, actualQueueSize);
    }

    public void testPlayNote3() {
        player.playNote(activity, noteName);
        player.stop();
        player.playNote(activity, noteName);

        int expectedQueueSize = 0;
        int actualQueueSize = player.getPlayQueue().size();

        assertEquals(expectedQueueSize, actualQueueSize);
        assertTrue(player.isPlaying());
    }

    public void testPlayNote4() {
        player.playNote(activity, noteName);
        player.onPlayNoteCompletionCallback(activity);

        int expectedQueueSize = 0;
        int actualQueueSize = player.getPlayQueue().size();

        assertEquals(expectedQueueSize, actualQueueSize);
        assertTrue(player.isPlaying());
    }

    public void testPlayAll() throws IOException, MidiException {
        Track track = TrackTestDataFactory.createSimpleTrack();
        player.playTrack(activity, track, Project.DEFAULT_BEATS_PER_MINUTE);

        assertTrue(player.isPlaying());
    }
}
