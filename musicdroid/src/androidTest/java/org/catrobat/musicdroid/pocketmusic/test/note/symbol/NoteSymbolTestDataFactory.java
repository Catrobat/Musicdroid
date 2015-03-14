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

import org.catrobat.musicdroid.pocketmusic.note.NoteLength;
import org.catrobat.musicdroid.pocketmusic.note.NoteName;
import org.catrobat.musicdroid.pocketmusic.note.symbol.NoteSymbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.SymbolPosition;

public class NoteSymbolTestDataFactory {

    private NoteSymbolTestDataFactory() {
    }

    public static NoteSymbol createNoteSymbol() {
        NoteSymbol noteSymbol = new NoteSymbol();

        noteSymbol.addNote(NoteName.C4, NoteLength.QUARTER);
        noteSymbol.addNote(NoteName.E4, NoteLength.QUARTER);

        return noteSymbol;
    }

    public static NoteSymbol createNoteSymbol(NoteName... noteNames) {
        return createNoteSymbol(NoteLength.QUARTER, noteNames);
    }

    public static NoteSymbol createNoteSymbol(NoteLength noteLength) {
        return createNoteSymbol(noteLength, NoteName.C4);
    }

    public static NoteSymbol createNoteSymbol(NoteLength noteLength, NoteName... noteNames) {
        NoteSymbol noteSymbol = new NoteSymbol();

        for (NoteName noteName : noteNames) {
            noteSymbol.addNote(noteName, noteLength);
        }

        return noteSymbol;
    }

    public static NoteSymbol createNoteSymbol(boolean marked) {
        NoteSymbol noteSymbol = new NoteSymbol();
        noteSymbol.setMarked(marked);
        return noteSymbol;
    }

    public static NoteSymbol createNoteSymbol(SymbolPosition symbolPosition) {
        NoteSymbol noteSymbol = new NoteSymbol();
        noteSymbol.setSymbolPosition(symbolPosition);
        return noteSymbol;
    }
}
