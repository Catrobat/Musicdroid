/*
 * Musicdroid: An on-device music generator for Android
 * Copyright (C) 2010-2015 The Catrobat Team
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

import org.catrobat.musicdroid.pocketmusic.note.MusicalInstrument;
import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.NoteName;
import org.catrobat.musicdroid.pocketmusic.note.symbol.NoteSymbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.Symbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.SymbolContainer;

import java.util.LinkedList;
import java.util.List;

public class SymbolContainerTest extends AndroidTestCase {

    public void testGetAttributes() {
        MusicalKey key = MusicalKey.VIOLIN;
        MusicalInstrument instrument = MusicalInstrument.ACOUSTIC_BASS;
        SymbolContainer symbolContainer = new SymbolContainer(key, instrument);

        assertEquals(key, symbolContainer.getKey());
        assertEquals(instrument, symbolContainer.getInstrument());
    }

    public void testAdd() {
        SymbolContainer symbolContainer = SymbolContainerTestDataFactory.createSymbolContainer();

        symbolContainer.add(NoteSymbolTestDataFactory.createNoteSymbol());

        assertEquals(1, symbolContainer.size());
        assertEquals(1, symbolContainer.getSymbols().size());
    }

    public void testAddAll() {
        SymbolContainer symbolContainer = SymbolContainerTestDataFactory.createSymbolContainer();

        List<Symbol> symbols = new LinkedList<Symbol>();
        symbols.add(NoteSymbolTestDataFactory.createNoteSymbol());
        symbols.add(NoteSymbolTestDataFactory.createNoteSymbol());
        symbolContainer.addAll(symbols);

        assertEquals(symbols.size(), symbolContainer.size());
    }

    public void testClear() {
        SymbolContainer symbolContainer = SymbolContainerTestDataFactory.createSymbolContainer();

        symbolContainer.add(NoteSymbolTestDataFactory.createNoteSymbol());
        symbolContainer.clear();

        assertEquals(0, symbolContainer.size());
    }

    public void testGet() {
        SymbolContainer symbolContainer = SymbolContainerTestDataFactory.createSymbolContainer();
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol();

        symbolContainer.add(noteSymbol);

        assertEquals(noteSymbol, symbolContainer.get(0));
    }

    public void testEquals1() {
        SymbolContainer symbolContainer1 = SymbolContainerTestDataFactory.createSimpleSymbolContainer();
        SymbolContainer symbolContainer2 = SymbolContainerTestDataFactory.createSimpleSymbolContainer();

        assertTrue(symbolContainer1.equals(symbolContainer2));
    }

    public void testEquals2() {
        SymbolContainer symbolContainer1 = SymbolContainerTestDataFactory.createSymbolContainer(MusicalKey.VIOLIN);
        SymbolContainer symbolContainer2 = SymbolContainerTestDataFactory.createSymbolContainer(MusicalKey.BASS);

        assertFalse(symbolContainer1.equals(symbolContainer2));
    }

    public void testEquals3() {
        SymbolContainer symbolContainer1 = SymbolContainerTestDataFactory.createSymbolContainer(MusicalInstrument.ACOUSTIC_BASS);
        SymbolContainer symbolContainer2 = SymbolContainerTestDataFactory.createSymbolContainer(MusicalInstrument.APPLAUSE);

        assertFalse(symbolContainer1.equals(symbolContainer2));
    }

    public void testEquals4() {
        SymbolContainer symbolContainer1 = SymbolContainerTestDataFactory.createSymbolContainerWithOneNoteSymbol(NoteName.A1);
        SymbolContainer symbolContainer2 = SymbolContainerTestDataFactory.createSymbolContainerWithOneNoteSymbol(NoteName.C4);

        assertFalse(symbolContainer1.equals(symbolContainer2));
    }

    public void testEquals5() {
        SymbolContainer symbolContainer1 = SymbolContainerTestDataFactory.createSymbolContainer();
        SymbolContainer symbolContainer2 = SymbolContainerTestDataFactory.createSymbolContainerWithOneNoteSymbol(NoteName.C4);

        assertFalse(symbolContainer1.equals(symbolContainer2));
    }

    public void testEquals6() {
        SymbolContainer symbolContainer = SymbolContainerTestDataFactory.createSymbolContainer();

        assertFalse(symbolContainer.equals(null));
    }

    public void testEquals7() {
        SymbolContainer symbolContainer = SymbolContainerTestDataFactory.createSymbolContainer();

        assertFalse(symbolContainer.equals(""));
    }
}
