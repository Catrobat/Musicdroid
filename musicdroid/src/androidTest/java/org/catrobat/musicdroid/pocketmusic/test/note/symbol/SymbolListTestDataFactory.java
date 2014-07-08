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
import org.catrobat.musicdroid.pocketmusic.note.symbol.BoundNoteSymbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.BreakSymbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.NoteSymbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.Symbol;

import java.util.LinkedList;
import java.util.List;

public final class SymbolListTestDataFactory {
	private SymbolListTestDataFactory() {
	}

	public static List<Symbol> createSimpleSymbolList() {
		List<Symbol> symbols = new LinkedList<Symbol>();

		NoteSymbol noteSymbol1 = new NoteSymbol();
		noteSymbol1.addNote(NoteName.C1, NoteLength.QUARTER);
		noteSymbol1.addNote(NoteName.D1, NoteLength.QUARTER);
		symbols.add(noteSymbol1);

		NoteSymbol noteSymbol2 = new NoteSymbol();
		noteSymbol2.addNote(NoteName.E1, NoteLength.QUARTER_DOT);
		symbols.add(noteSymbol2);

		NoteSymbol noteSymbol3 = new NoteSymbol();
		noteSymbol3.addNote(NoteName.F1, NoteLength.QUARTER);
		symbols.add(noteSymbol3);

		NoteSymbol noteSymbol4 = new NoteSymbol();
		noteSymbol4.addNote(NoteName.C1, NoteLength.QUARTER);
		symbols.add(noteSymbol4);

		NoteSymbol noteSymbol5 = new NoteSymbol();
		noteSymbol5.addNote(NoteName.C2, NoteLength.QUARTER);
		symbols.add(noteSymbol5);

		return symbols;
	}

	public static List<Symbol> createSymbolListWithBreak() {
		List<Symbol> symbols = new LinkedList<Symbol>();

		NoteSymbol noteSymbol1 = new NoteSymbol();
		noteSymbol1.addNote(NoteName.C1, NoteLength.QUARTER);
		noteSymbol1.addNote(NoteName.D1, NoteLength.QUARTER);
		symbols.add(noteSymbol1);

		NoteSymbol noteSymbol2 = new NoteSymbol();
		noteSymbol2.addNote(NoteName.E1, NoteLength.QUARTER_DOT);
		symbols.add(noteSymbol2);

		BreakSymbol breakSymbol1 = new BreakSymbol(NoteLength.QUARTER);
		symbols.add(breakSymbol1);

		NoteSymbol noteSymbol3 = new NoteSymbol();
		noteSymbol3.addNote(NoteName.C1, NoteLength.QUARTER);
		symbols.add(noteSymbol3);

		return symbols;
	}

	public static List<Symbol> createComplexSymbolList() {
		List<Symbol> symbols = new LinkedList<Symbol>();

		NoteSymbol noteSymbol1 = new NoteSymbol();
		noteSymbol1.addNote(NoteName.C1, NoteLength.QUARTER);
		noteSymbol1.addNote(NoteName.D1, NoteLength.HALF);

		NoteSymbol noteSymbol2 = new NoteSymbol();
		noteSymbol2.addNote(NoteName.E1, NoteLength.QUARTER);
		noteSymbol2.addNote(NoteName.F1, NoteLength.QUARTER);

		BoundNoteSymbol boundNoteSymbol1 = new BoundNoteSymbol();
		boundNoteSymbol1.addNoteSymbol(noteSymbol1);
		boundNoteSymbol1.addNoteSymbol(noteSymbol2);
		symbols.add(boundNoteSymbol1);

		BreakSymbol breakSymbol1 = new BreakSymbol(NoteLength.QUARTER_DOT);
		symbols.add(breakSymbol1);

		NoteSymbol noteSymbol3 = new NoteSymbol();
		noteSymbol3.addNote(NoteName.C2, NoteLength.QUARTER_DOT);
		noteSymbol3.addNote(NoteName.D2, NoteLength.QUARTER_DOT);
		symbols.add(noteSymbol3);

		NoteSymbol noteSymbol4 = new NoteSymbol();
		noteSymbol4.addNote(NoteName.B3, NoteLength.QUARTER);
		symbols.add(noteSymbol4);

		return symbols;
	}
}
