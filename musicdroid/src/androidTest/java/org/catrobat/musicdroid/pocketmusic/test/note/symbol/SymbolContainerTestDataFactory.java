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

import org.catrobat.musicdroid.pocketmusic.note.MusicalInstrument;
import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.NoteLength;
import org.catrobat.musicdroid.pocketmusic.note.NoteName;
import org.catrobat.musicdroid.pocketmusic.note.symbol.SymbolContainer;

public class SymbolContainerTestDataFactory {

    private SymbolContainerTestDataFactory() {
    }

    public static SymbolContainer createSymbolContainer(MusicalKey key, MusicalInstrument instrument) {
        return new SymbolContainer(key, instrument);
    }

    public static SymbolContainer createSymbolContainer(MusicalKey key) {
        return createSymbolContainer(key, MusicalInstrument.ACOUSTIC_GRAND_PIANO);
    }

    public static SymbolContainer createSymbolContainer(MusicalInstrument instrument) {
        return createSymbolContainer(MusicalKey.VIOLIN, instrument);
    }

    public static SymbolContainer createSymbolContainer() {
        return createSymbolContainer(MusicalKey.VIOLIN, MusicalInstrument.ACOUSTIC_GRAND_PIANO);
    }

    public static SymbolContainer createSimpleSymbolContainer() {
        return createSymbolContainerWithSeveralNoteSymbols(1);
    }

    public static SymbolContainer createSymbolContainerWithOneNoteSymbol(NoteName noteName) {
        SymbolContainer symbolContainer = createSymbolContainer();
        symbolContainer.add(NoteSymbolTestDataFactory.createNoteSymbol(noteName));

        return symbolContainer;
    }

    public static SymbolContainer createSymbolContainerWithThreeNoteSymbols() {
        return createSymbolContainerWithSeveralNoteSymbols(3);
    }

    public static SymbolContainer createSymbolContainerWithSeveralNoteSymbols(int amount) {
        SymbolContainer symbolContainer = createSymbolContainer();

        for (int i = 0; i < amount; i++) {
            symbolContainer.add(NoteSymbolTestDataFactory.createNoteSymbol());
        }

        return symbolContainer;
    }

    public static SymbolContainer createSymbolsWithBreak() {
        SymbolContainer symbolContainer = createSymbolContainer();

        symbolContainer.add(NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.QUARTER, NoteName.C4, NoteName.D4));
        symbolContainer.add(NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.QUARTER, NoteName.E4));
        symbolContainer.add(BreakSymbolTestDataFactory.createBreakSymbol(NoteLength.QUARTER));
        symbolContainer.add(NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.QUARTER, NoteName.C4));

        return symbolContainer;
    }

    public static SymbolContainer createSymbolsWithSeveralBreaks() {
        SymbolContainer symbolContainer = createSymbolContainer();

        symbolContainer.add(BreakSymbolTestDataFactory.createBreakSymbol(NoteLength.HALF));
        symbolContainer.add(BreakSymbolTestDataFactory.createBreakSymbol(NoteLength.SIXTEENTH));
        symbolContainer.add(NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.QUARTER, NoteName.C4));

        return symbolContainer;
    }

    public static SymbolContainer createSymbolsWithBreakAtTheEnd() {
        SymbolContainer symbolContainer = createSymbolContainer();

        symbolContainer.add(NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.QUARTER, NoteName.C4));
        symbolContainer.add(BreakSymbolTestDataFactory.createBreakSymbol(NoteLength.QUARTER));

        return symbolContainer;
    }

    public static SymbolContainer createSymbolsWithAccords() {
        SymbolContainer symbolContainer = createSymbolContainer();

        symbolContainer.add(NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.QUARTER, NoteName.C3, NoteName.C4));
        symbolContainer.add(NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.QUARTER, NoteName.C3, NoteName.C4));
        symbolContainer.add(NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.QUARTER, NoteName.C3, NoteName.C4));

        return symbolContainer;
    }
}
