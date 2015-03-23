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

package org.catrobat.musicdroid.pocketmusic.test.note.symbol;

import android.graphics.RectF;
import android.test.AndroidTestCase;

import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.NoteFlag;
import org.catrobat.musicdroid.pocketmusic.note.NoteLength;
import org.catrobat.musicdroid.pocketmusic.note.NoteName;
import org.catrobat.musicdroid.pocketmusic.note.symbol.NoteSymbol;

import java.util.List;

public class NoteSymbolTest extends AndroidTestCase {

    public void testConstructor() {
        NoteSymbol noteSymbol = new NoteSymbol();

        assertEquals(0, noteSymbol.size());
    }

    public void testAddNote() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol(NoteName.C4);

        assertEquals(1, noteSymbol.size());
    }

    public void testGetNoteLength() {
        NoteName noteName = NoteName.C4;
        NoteLength noteLength = NoteLength.QUARTER;
        NoteSymbol noteSymbol = new NoteSymbol();
        noteSymbol.addNote(noteName, noteLength);

        assertEquals(noteLength, noteSymbol.getNoteLength(noteName));
    }

    public void testToString() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol();

        assertEquals("[NoteSymbol] marked=" + noteSymbol.isMarked() + " size=" + noteSymbol.size(), noteSymbol.toString());
    }

    public void testGetNoteNamesSorted() {
        NoteName c4 = NoteName.C4;
        NoteName c5 = NoteName.C5;
        NoteName c3 = NoteName.C3;
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol(c4, c5, c3);

        List<NoteName> noteNames = noteSymbol.getNoteNamesSorted();

        assertEquals(3, noteNames.size());
        assertEquals(c3, noteNames.get(0));
        assertEquals(c4, noteNames.get(1));
        assertEquals(c5, noteNames.get(2));
    }

    public void testEquals1() {
        NoteSymbol noteSymbol1 = NoteSymbolTestDataFactory.createNoteSymbol();
        NoteSymbol noteSymbol2 = NoteSymbolTestDataFactory.createNoteSymbol();

        assertTrue(noteSymbol1.equals(noteSymbol2));
    }

    public void testEquals2() {
        NoteSymbol noteSymbol1 = NoteSymbolTestDataFactory.createNoteSymbol(NoteName.C4);
        NoteSymbol noteSymbol2 = NoteSymbolTestDataFactory.createNoteSymbol(NoteName.A0);

        assertFalse(noteSymbol1.equals(noteSymbol2));
    }

    public void testEquals3() {
        NoteSymbol noteSymbol1 = NoteSymbolTestDataFactory.createNoteSymbol(NoteName.C4, NoteName.A0);
        NoteSymbol noteSymbol2 = NoteSymbolTestDataFactory.createNoteSymbol(NoteName.C4);

        assertFalse(noteSymbol1.equals(noteSymbol2));
    }

    public void testEquals4() {
        NoteSymbol noteSymbol1 = NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.QUARTER);
        NoteSymbol noteSymbol2 = NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.WHOLE);

        assertFalse(noteSymbol1.equals(noteSymbol2));
    }

    public void testEquals5() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol();

        assertFalse(noteSymbol.equals(""));
    }

    public void testEquals6() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol();

        assertFalse(noteSymbol.equals(null));
    }

    public void testEquals7() {
        NoteSymbol noteSymbol1 = NoteSymbolTestDataFactory.createNoteSymbol(true);
        NoteSymbol noteSymbol2 = NoteSymbolTestDataFactory.createNoteSymbol(false);

        assertTrue(noteSymbol1.equals(noteSymbol2));
    }

    public void testEquals8() {
        NoteSymbol noteSymbol1 = NoteSymbolTestDataFactory.createNoteSymbol(SymbolPositionTestDataFactory.createSymbolPosition());
        NoteSymbol noteSymbol2 = NoteSymbolTestDataFactory.createNoteSymbol(SymbolPositionTestDataFactory.createSymbolPosition());

        assertTrue(noteSymbol1.equals(noteSymbol2));
    }

    public void testEquals9() {
        NoteSymbol noteSymbol1 = NoteSymbolTestDataFactory.createNoteSymbol(SymbolPositionTestDataFactory.createSymbolPosition(new RectF(0, 0, 100, 100)));
        NoteSymbol noteSymbol2 = NoteSymbolTestDataFactory.createNoteSymbol(SymbolPositionTestDataFactory.createSymbolPosition(new RectF(0, 0, 101, 101)));

        assertFalse(noteSymbol1.equals(noteSymbol2));
    }

    public void testHasStem1() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.WHOLE);

        assertFalse(noteSymbol.hasStem());
    }

    public void testHasStem2() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.QUARTER);

        assertTrue(noteSymbol.hasStem());
    }

    public void testGetFlag1() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.QUARTER);

        assertEquals(NoteFlag.NO_FLAG, noteSymbol.getFlag());
    }

    public void testGetFlag2() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.EIGHT);

        assertEquals(NoteFlag.SINGLE_FLAG, noteSymbol.getFlag());
    }

    public void testGetFlag3() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.EIGHT_DOT);

        assertEquals(NoteFlag.SINGLE_FLAG, noteSymbol.getFlag());
    }

    public void testGetFlag4() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.SIXTEENTH);

        assertEquals(NoteFlag.DOUBLE_FLAG, noteSymbol.getFlag());
    }

    public void testIsStemUp1() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol(NoteName.C4, NoteName.D4);

        assertTrue(noteSymbol.isStemUp(MusicalKey.VIOLIN));
    }

    public void testIsStemUp2() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol(NoteName.C5, NoteName.C5);

        assertFalse(noteSymbol.isStemUp(MusicalKey.VIOLIN));
    }

    public void testCopy() {
        NoteSymbol symbol = NoteSymbolTestDataFactory.createNoteSymbol();
        NoteSymbol copySymbol = new NoteSymbol(symbol);

        assertTrue(symbol != copySymbol);
        assertTrue(symbol.equals(copySymbol));
    }
}
