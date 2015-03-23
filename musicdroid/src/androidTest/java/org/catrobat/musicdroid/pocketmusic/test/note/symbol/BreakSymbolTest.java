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

import android.test.AndroidTestCase;

import org.catrobat.musicdroid.pocketmusic.note.NoteLength;
import org.catrobat.musicdroid.pocketmusic.note.symbol.BreakSymbol;

public class BreakSymbolTest extends AndroidTestCase {

    public void testGetNoteLength() {
        NoteLength noteLength = NoteLength.QUARTER;
        BreakSymbol breakSymbol = BreakSymbolTestDataFactory.createBreakSymbol(noteLength);

        assertEquals(noteLength, breakSymbol.getNoteLength());
    }

    public void testToString() {
        NoteLength noteLength = NoteLength.QUARTER;
        BreakSymbol breakSymbol = BreakSymbolTestDataFactory.createBreakSymbol(noteLength);

        String expectedString = "[BreakSymbol] marked=" + breakSymbol.isMarked() + " noteLength=" + noteLength;

        assertEquals(expectedString, breakSymbol.toString());
    }

    public void testEquals1() {
        BreakSymbol breakSymbol1 = BreakSymbolTestDataFactory.createBreakSymbol();
        BreakSymbol breakSymbol2 = BreakSymbolTestDataFactory.createBreakSymbol();

        assertTrue(breakSymbol1.equals(breakSymbol2));
    }

    public void testEquals2() {
        BreakSymbol breakSymbol1 = BreakSymbolTestDataFactory.createBreakSymbol(NoteLength.QUARTER);
        BreakSymbol breakSymbol2 = BreakSymbolTestDataFactory.createBreakSymbol(NoteLength.EIGHT);

        assertFalse(breakSymbol1.equals(breakSymbol2));
    }

    public void testEquals3() {
        BreakSymbol breakSymbol = BreakSymbolTestDataFactory.createBreakSymbol();

        assertFalse(breakSymbol.equals(null));
    }

    public void testEquals4() {
        BreakSymbol breakSymbol = BreakSymbolTestDataFactory.createBreakSymbol();

        assertFalse(breakSymbol.equals(""));
    }

    public void testEquals5() {
        BreakSymbol breakSymbol1 = BreakSymbolTestDataFactory.createBreakSymbol(true);
        BreakSymbol breakSymbol2 = BreakSymbolTestDataFactory.createBreakSymbol(false);

        assertTrue(breakSymbol1.equals(breakSymbol2));
    }

    public void testCopy() {
        BreakSymbol symbol = BreakSymbolTestDataFactory.createBreakSymbol();
        BreakSymbol copySymbol = new BreakSymbol(symbol);

        assertTrue(symbol != copySymbol);
        assertTrue(symbol.equals(copySymbol));
    }
}
