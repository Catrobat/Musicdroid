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

import org.catrobat.musicdroid.pocketmusic.note.NoteLength;

public class NoteLengthTest extends AndroidTestCase {

    public void testCalculateDuration1() {
        long expected = 384 / 48 * 60;
        long actual = NoteLength.QUARTER.getTickDuration();

        assertEquals("The note length of a Quarter-Tick-Duration doesn't match the expected " +
                        "duration", expected, actual
        );
    }

    public void testGetNoteLengthFromTick1() {
        NoteLength expectedNoteLength = NoteLength.WHOLE_DOT;
        long duration = expectedNoteLength.getTickDuration();

        NoteLength actualNoteLength = NoteLength.getNoteLengthFromTickDuration(duration);

        assertEquals("The duration was expected to be a whole with dot",
                expectedNoteLength, actualNoteLength);
    }

    public void testGetNoteLengthFromTick2() {
        NoteLength expectedNoteLength = NoteLength.WHOLE_DOT;
        long duration = expectedNoteLength.getTickDuration();
        duration += 1;

        NoteLength actualNoteLength = NoteLength.getNoteLengthFromTickDuration(duration);

        assertEquals("The duration was expected to be a whole with dot",
                expectedNoteLength, actualNoteLength);
    }

    public void testGetNoteLengthFromTick3() {
        NoteLength expectedNoteLength = NoteLength.QUARTER;
        long duration = NoteLength.QUARTER_DOT.getTickDuration();
        duration -= 1;

        NoteLength actualNoteLength = NoteLength.getNoteLengthFromTickDuration(duration);

        assertEquals("The duration was expected to be a quarter",
                expectedNoteLength, actualNoteLength);
    }

    public void testGetNoteLengthFromTick4() {
        NoteLength expectedNoteLength = NoteLength.QUARTER;
        long duration = expectedNoteLength.getTickDuration();
        duration += 1;

        NoteLength actualNoteLength = NoteLength.getNoteLengthFromTickDuration(duration);

        assertEquals("The duration was expected to be a quarter",
                expectedNoteLength, actualNoteLength);
    }

    public void testHasStem1() {
        assertFalse("A whole note should not have a stem", NoteLength.WHOLE.hasStem());
    }

    public void testHasStem2() {
        assertFalse("A whole note with dot should not have a stem", NoteLength.WHOLE_DOT.hasStem());
    }

    public void testHasStem3() {
        assertTrue("A quarter note has to have a stem", NoteLength.QUARTER.hasStem());
    }

    public void testHasDot1() {
        assertFalse("A quarter note has no dot", NoteLength.QUARTER.hasDot());
    }

    public void testHasDot2() {
        assertTrue("A whole note with dot has to have a dot", NoteLength.WHOLE_DOT.hasDot());
    }

    public void testHasDot3() {
        assertTrue("A half note with dot should have a dot", NoteLength.HALF_DOT.hasDot());
    }

    public void testHasDot4() {
        assertTrue("A quarter note with dot has to have a dot", NoteLength.QUARTER_DOT.hasDot());
    }

    public void testHasDot5() {
        assertTrue("A eight note with dot has to have a dot", NoteLength.EIGHT_DOT.hasDot());
    }

    public void testHasFlag1() {
        assertFalse("A quarter note doesn't have a flag", NoteLength.QUARTER.hasFlag());
    }

    public void testHasFlag2() {
        assertTrue("A eight note has to have a flag", NoteLength.EIGHT.hasFlag());
    }

    public void testHasFlag3() {
        assertTrue("A eight dot note has to have a flag", NoteLength.EIGHT_DOT.hasFlag());
    }

    public void testHasFlag4() {
        assertTrue("A sixteenth note has to have a flag", NoteLength.SIXTEENTH.hasFlag());
    }

    public void testGetAmountOfFlags1() {
        assertEquals("A sixteenth note has to have 2 flags",
                2, NoteLength.SIXTEENTH.getAmountOfFlags());
    }

    public void testGetAmountOfFlags2() {
        assertEquals("A eight note has to have 1 flag", 1, NoteLength.EIGHT.getAmountOfFlags());
    }

    public void testGetAmountOfFlags3() {
        assertEquals("A eight note has to have 1 flag", 1, NoteLength.EIGHT_DOT.getAmountOfFlags());
    }

    public void testGetAmountOfFlags4() {
        assertEquals("A quarter note has to have no flag", 0, NoteLength.QUARTER.getAmountOfFlags());
    }
}
