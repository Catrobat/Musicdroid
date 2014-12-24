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

package org.catrobat.musicdroid.pocketmusic.test.instrument.tempo;

import android.test.AndroidTestCase;

import org.catrobat.musicdroid.pocketmusic.instrument.tempo.SimpleTickProvider;
import org.catrobat.musicdroid.pocketmusic.note.NoteLength;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.Track;
import org.catrobat.musicdroid.pocketmusic.test.note.NoteEventTestDataFactory;
import org.catrobat.musicdroid.pocketmusic.test.note.TrackTestDataFactory;

public class SimpleTickProviderTest extends AndroidTestCase {

    public void testGetNextTickThroughStartNoteEvent() {
        long expected = 0;

        SimpleTickProvider clock = new SimpleTickProvider();
        long actual = clock.getNextTick(NoteEventTestDataFactory.createNoteEvent(true));

        assertEquals(expected, actual);
    }

    public void testGetNextTickThroughTwoStartNoteEvents() {
        long expected = 0;

        SimpleTickProvider clock = new SimpleTickProvider();
        clock.getNextTick(NoteEventTestDataFactory.createNoteEvent(true));
        long actual = clock.getNextTick(NoteEventTestDataFactory.createNoteEvent(true));

        assertEquals(expected, actual);
    }

    public void testGetNextTickThroughEndNoteEvent() {
        long expected = NoteLength.QUARTER.toTicks(Project.DEFAULT_BEATS_PER_MINUTE);

        SimpleTickProvider clock = new SimpleTickProvider();
        clock.getNextTick(NoteEventTestDataFactory.createNoteEvent(true));
        long actual = clock.getNextTick(NoteEventTestDataFactory.createNoteEvent(false));

        assertEquals(expected, actual);
    }

    public void testGetNextTickThroughTwoEndNoteEvents() {
        long expected = NoteLength.QUARTER.toTicks(Project.DEFAULT_BEATS_PER_MINUTE);

        SimpleTickProvider clock = new SimpleTickProvider();
        clock.getNextTick(NoteEventTestDataFactory.createNoteEvent(true));
        clock.getNextTick(NoteEventTestDataFactory.createNoteEvent(true));
        clock.getNextTick(NoteEventTestDataFactory.createNoteEvent(false));
        long actual = clock.getNextTick(NoteEventTestDataFactory.createNoteEvent(false));

        assertEquals(expected, actual);
    }

    public void testSetTickBasedOnTrack() {
        SimpleTickProvider clock = new SimpleTickProvider();
        Track track = TrackTestDataFactory.createTrack();

        clock.setTickBasedOnTrack(track);

        assertEquals(track.getLastTick(), clock.getCurrentTick());
    }
}
