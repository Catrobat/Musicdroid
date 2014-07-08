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

public final class NoteSymbolTestDataFactory {
	private NoteSymbolTestDataFactory() {
	}

	public static NoteSymbol createNoteSymbol() {
		return new NoteSymbol();
	}

	public static NoteSymbol createNoteSymbolWithNote() {
		NoteSymbol noteSymbol = createNoteSymbol();
		noteSymbol.addNote(NoteName.C4, NoteLength.QUARTER);

		return noteSymbol;
	}

	public static NoteSymbol createNoteSymbolWithNote(NoteLength noteLength) {
		NoteSymbol noteSymbol = createNoteSymbol();
		noteSymbol.addNote(NoteName.C4, noteLength);

		return noteSymbol;
	}

	public static NoteSymbol createNoteSymbolWithNote(NoteName noteName) {
		NoteSymbol noteSymbol = createNoteSymbol();
		noteSymbol.addNote(noteName, NoteLength.QUARTER);

		return noteSymbol;
	}

	public static NoteSymbol createNoteSymbolWithAccord(NoteLength noteLength1, NoteLength noteLength2,
			NoteLength noteLength3) {
		NoteSymbol noteSymbol = createNoteSymbol();
		noteSymbol.addNote(NoteName.F4, noteLength1);
		noteSymbol.addNote(NoteName.D4, noteLength2);
		noteSymbol.addNote(NoteName.E4, noteLength3);

		return noteSymbol;
	}

	public static NoteSymbol createNoteSymbolWithAccord(NoteName noteName1, NoteName noteName2, NoteName noteName3) {
		NoteSymbol noteSymbol = createNoteSymbol();
		noteSymbol.addNote(noteName1, NoteLength.QUARTER);
		noteSymbol.addNote(noteName2, NoteLength.QUARTER);
		noteSymbol.addNote(noteName3, NoteLength.QUARTER);

		return noteSymbol;
	}
}
