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
package org.catrobat.musicdroid.pocketmusic.test.note;

import android.test.AndroidTestCase;

import org.catrobat.musicdroid.pocketmusic.note.MusicalInstrument;
import org.catrobat.musicdroid.pocketmusic.note.NoteEvent;
import org.catrobat.musicdroid.pocketmusic.note.NoteLength;
import org.catrobat.musicdroid.pocketmusic.note.NoteName;
import org.catrobat.musicdroid.pocketmusic.note.Track;

public class TrackTest extends AndroidTestCase {

	public void testGetInstrument() {
		Track track = TrackTestDataFactory.createTrack();

		assertEquals("The instrument of an empty track has to be Acoustic grand piano",
                MusicalInstrument.ACOUSTIC_GRAND_PIANO, track.getInstrument());
	}

	public void testAddNoteEvent1() {
		Track track = TrackTestDataFactory.createTrack();
		track.addNoteEvent(0, NoteEventTestDataFactory.createNoteEvent());

		assertEquals("The size of a track with one note event has to be 1", 1, track.size());
	}

	public void testAddNoteEvent2() {
		Track track = TrackTestDataFactory.createTrack();
		track.addNoteEvent(0, NoteEventTestDataFactory.createNoteEvent());
		track.addNoteEvent(0, NoteEventTestDataFactory.createNoteEvent());

		assertEquals("The size of a track with two note events has to be 2", 2, track.size());
	}

	public void testGetNoteEventsForTick() {
		Track track = TrackTestDataFactory.createTrack();
		long tick = 0;
		NoteEvent noteEvent = NoteEventTestDataFactory.createNoteEvent();
		track.addNoteEvent(tick, noteEvent);

		assertEquals("The event of the tick doesn't match the expected event", noteEvent,
                track.getNoteEventsForTick(tick).get(0));
	}

	public void testEquals1() {
		long tick = 0;
		Track track1 = TrackTestDataFactory.createTrack();
		track1.addNoteEvent(tick, NoteEventTestDataFactory.createNoteEvent());
		Track track2 = TrackTestDataFactory.createTrack();
		track2.addNoteEvent(tick, NoteEventTestDataFactory.createNoteEvent());

		assertTrue("The two tracks should be equal", track1.equals(track2));
	}

	public void testEquals2() {
		long tick = 0;
		Track track1 = TrackTestDataFactory.createTrack();
		track1.addNoteEvent(tick, NoteEventTestDataFactory.createNoteEvent(NoteName.C1));
		Track track2 = TrackTestDataFactory.createTrack();
		track2.addNoteEvent(tick, NoteEventTestDataFactory.createNoteEvent(NoteName.C2));

		assertFalse("The two tracks have different notes and are not equal", track1.equals(track2));
	}

	public void testEquals3() {
		Track track1 = TrackTestDataFactory.createTrack();
		track1.addNoteEvent(0, NoteEventTestDataFactory.createNoteEvent());
		Track track2 = TrackTestDataFactory.createTrack();

		assertFalse("The tracks mustn't be equal because onle one has an event.",
                track1.equals(track2));
	}

	public void testEquals4() {
		Track track1 = TrackTestDataFactory.createTrack(MusicalInstrument.ACCORDION);
		Track track2 = TrackTestDataFactory.createTrack();

		assertFalse("Tracks with different instruments are not equal", track1.equals(track2));
	}

	public void testEquals5() {
		Track track = TrackTestDataFactory.createTrack();

		assertFalse("A new created track must not be null", track.equals(null));
	}

	public void testEquals6() {
		Track track = TrackTestDataFactory.createTrack();

		assertFalse("A new created track must not be empty", track.equals(""));
	}

	public void testToString() {
		Track track = TrackTestDataFactory.createTrack();
		String expectedString = "[Track] instrument=" + MusicalInstrument.ACOUSTIC_GRAND_PIANO + " key=" + track.getKey() + " size="
				+ track.size();

		assertEquals("The toString method doesn't match the expected string", expectedString,
                track.toString());
	}

    public void testSetTickBasedOnTrack1() {
        Track track = TrackTestDataFactory.createTrack();

        assertEquals("A new track has to have 0 as last tick", 0, track.getLastTick());
    }

    public void testSetTickBasedOnTrack2() {
        Track track = TrackTestDataFactory.createTrack();
        long tick = 0;

        track.addNoteEvent(tick, NoteEventTestDataFactory.createNoteEvent(true));
        tick += NoteLength.QUARTER.getTickDuration();
        track.addNoteEvent(tick, NoteEventTestDataFactory.createNoteEvent(false));

        assertEquals("The last tick is not the one expected", tick, track.getLastTick());
    }

    public void testCopyTrack() {
        Track track = TrackTestDataFactory.createSimpleTrack();
        Track copyTrack = new Track(track);

        assertTrue("The objects must not be the same after copying", track != copyTrack);
        assertTrue("The objects have to be equal after copying", track.equals(copyTrack));
    }

    public void testEmpty1() {
        Track track = TrackTestDataFactory.createTrack();

        assertTrue("A new track has to be empty", track.empty());
    }

    public void testEmpty2() {
        Track track = TrackTestDataFactory.createTrack();
        long tick = 0;

        track.addNoteEvent(tick, NoteEventTestDataFactory.createNoteEvent());

        assertFalse("A track with one note event has not to be empty", track.empty());
    }
}
