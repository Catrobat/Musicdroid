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

import org.catrobat.musicdroid.pocketmusic.note.NoteLength;
import org.catrobat.musicdroid.pocketmusic.note.NoteName;
import org.catrobat.musicdroid.pocketmusic.note.symbol.Symbol;

import java.util.LinkedList;
import java.util.List;

public class SymbolsTestDataFactory {

    private SymbolsTestDataFactory() {}

    public static List<Symbol> createSymbolsWithBreak() {
        List<Symbol> symbols = new LinkedList<Symbol>();

        symbols.add(NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.QUARTER, NoteName.C4, NoteName.D4));
        symbols.add(NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.QUARTER, NoteName.E4));
        symbols.add(BreakSymbolTestDataFactory.createBreakSymbol(NoteLength.QUARTER));
        symbols.add(NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.QUARTER, NoteName.C4));

        return symbols;
    }

    public static List<Symbol> createSymbolsWithSeveralBreaks() {
        List<Symbol> symbols = new LinkedList<Symbol>();

        symbols.add(BreakSymbolTestDataFactory.createBreakSymbol(NoteLength.HALF));
        symbols.add(BreakSymbolTestDataFactory.createBreakSymbol(NoteLength.SIXTEENTH));
        symbols.add(NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.QUARTER, NoteName.C4));

        return symbols;
    }

    public static List<Symbol> createSymbolsWithBreakAtTheEnd() {
        List<Symbol> symbols = new LinkedList<Symbol>();

        symbols.add(NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.QUARTER, NoteName.C4));
        symbols.add(BreakSymbolTestDataFactory.createBreakSymbol(NoteLength.QUARTER));

        return symbols;
    }
}
