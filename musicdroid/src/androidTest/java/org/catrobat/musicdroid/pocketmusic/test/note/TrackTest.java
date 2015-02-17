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
import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.NoteEvent;
import org.catrobat.musicdroid.pocketmusic.note.NoteLength;
import org.catrobat.musicdroid.pocketmusic.note.NoteName;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.Track;

public class TrackTest extends AndroidTestCase {

    public void testSetId() {
        Track track = TrackTestDataFactory.createTrack();
        int newId = track.getId() + 1;

        track.setId(newId);

        assertEquals(newId, track.getId());
    }

	public void testGetInstrument() {
		Track track = TrackTestDataFactory.createTrack();

		assertEquals(MusicalInstrument.ACOUSTIC_GRAND_PIANO, track.getInstrument());
	}

	public void testAddNoteEvent1() {
		Track track = TrackTestDataFactory.createTrack();
		track.addNoteEvent(0, NoteEventTestDataFactory.createNoteEvent());

		assertEquals(1, track.size());
	}

	public void testAddNoteEvent2() {
		Track track = TrackTestDataFactory.createTrack();
		track.addNoteEvent(0, NoteEventTestDataFactory.createNoteEvent());
		track.addNoteEvent(0, NoteEventTestDataFactory.createNoteEvent());

		assertEquals(2, track.size());
	}

	public void testGetNoteEventsForTick() {
		Track track = TrackTestDataFactory.createTrack();
		long tick = 0;
		NoteEvent noteEvent = NoteEventTestDataFactory.createNoteEvent();
		track.addNoteEvent(tick, noteEvent);

		assertEquals(noteEvent, track.getNoteEventsForTick(tick).get(0));
	}

    public void testEquals1() {
        long tick = 0;
        Track track1 = TrackTestDataFactory.createTrack();
        track1.addNoteEvent(tick, NoteEventTestDataFactory.createNoteEvent());
        Track track2 = TrackTestDataFactory.createTrack();
        track2.addNoteEvent(tick, NoteEventTestDataFactory.createNoteEvent());

        assertTrue(track1.equals(track2));
    }

    public void testEquals2() {
        long tick = 0;
        Track track1 = TrackTestDataFactory.createTrack();
        track1.addNoteEvent(tick, NoteEventTestDataFactory.createNoteEvent(NoteName.C1));
        Track track2 = TrackTestDataFactory.createTrack();
        track2.addNoteEvent(tick, NoteEventTestDataFactory.createNoteEvent(NoteName.C2));

        assertFalse(track1.equals(track2));
    }

    public void testEquals3() {
        Track track1 = TrackTestDataFactory.createTrack();
        track1.addNoteEvent(0, NoteEventTestDataFactory.createNoteEvent());
        Track track2 = TrackTestDataFactory.createTrack();

        assertFalse(track1.equals(track2));
    }

    public void testEquals4() {
        Track track1 = TrackTestDataFactory.createTrack(MusicalInstrument.ACCORDION);
        Track track2 = TrackTestDataFactory.createTrack(MusicalInstrument.ALTO_SAX);

        assertFalse(track1.equals(track2));
    }

    public void testEquals5() {
        Track track1 = TrackTestDataFactory.createTrack(MusicalKey.BASS);
        Track track2 = TrackTestDataFactory.createTrack(MusicalKey.VIOLIN);

        assertFalse(track1.equals(track2));
    }

    public void testEquals6() {
        Track track1 = TrackTestDataFactory.createTrack(60);
        Track track2 = TrackTestDataFactory.createTrack(100);

        assertFalse(track1.equals(track2));
    }

    public void testEquals7() {
        Track track1 = TrackTestDataFactory.createTrack();
        Track track2 = TrackTestDataFactory.createTrack();
        track2.setId(track2.getId() + 1);

        assertFalse(track1.equals(track2));
    }

    public void testEquals8() {
        Track track = TrackTestDataFactory.createTrack();

        assertFalse(track.equals(null));
    }

    public void testEquals9() {
        Track track = TrackTestDataFactory.createTrack();

        assertFalse(track.equals(""));
    }

	public void testToString1() {
		Track track = TrackTestDataFactory.createTrack();
		String expectedString = "[Track] id=" + track.getId()
                + " projectName=empty"
                + " instrument=" + MusicalInstrument.ACOUSTIC_GRAND_PIANO
                + " key=" + track.getKey()
                + " beatsPerMinute=" + track.getBeatsPerMinute()
                + " size=" + track.size();

		assertEquals(expectedString, track.toString());
	}

    public void testToString2() {
        Track track = TrackTestDataFactory.createTrack();
        Project project = ProjectTestDataFactory.createProject();
        track.setProject(project);
        String expectedString = "[Track] id=" + track.getId()
                + " projectName=" + project.getName()
                + " instrument=" + MusicalInstrument.ACOUSTIC_GRAND_PIANO
                + " key=" + track.getKey()
                + " beatsPerMinute=" + track.getBeatsPerMinute()
                + " size=" + track.size();

        assertEquals(expectedString, track.toString());
    }

    public void testSetTickBasedOnTrack1() {
        Track track = TrackTestDataFactory.createTrack();

        assertEquals(0, track.getLastTick());
    }

    public void testSetTickBasedOnTrack2() {
        Track track = TrackTestDataFactory.createTrack();
        long tick = 0;

        track.addNoteEvent(tick, NoteEventTestDataFactory.createNoteEvent(true));
        tick += NoteLength.QUARTER.toTicks(track.getBeatsPerMinute());
        track.addNoteEvent(tick, NoteEventTestDataFactory.createNoteEvent(false));

        assertEquals(tick, track.getLastTick());
    }

    public void testCopyTrack() {
        Track track = TrackTestDataFactory.createSimpleTrack();
        Track copyTrack = new Track(track);

        assertTrue(track != copyTrack);
        assertTrue(track.equals(copyTrack));
    }

    public void testEmpty1() {
        Track track = TrackTestDataFactory.createTrack();

        assertTrue(track.empty());
    }

    public void testEmpty2() {
        Track track = TrackTestDataFactory.createTrack();
        long tick = 0;

        track.addNoteEvent(tick, NoteEventTestDataFactory.createNoteEvent());

        assertFalse(track.empty());
    }

    public void testGetTotalTimeInMilliseconds() {
        Track track = TrackTestDataFactory.createTrack();
        NoteLength noteLength = NoteLength.QUARTER;
        long expecteTotalTime = noteLength.toMilliseconds(track.getBeatsPerMinute());
        long tick = 0;

        track.addNoteEvent(tick, NoteEventTestDataFactory.createNoteEvent(true));
        tick += noteLength.toTicks(track.getBeatsPerMinute());
        track.addNoteEvent(tick, NoteEventTestDataFactory.createNoteEvent(false));

        assertEquals(expecteTotalTime, track.getTotalTimeInMilliseconds());
    }
}
