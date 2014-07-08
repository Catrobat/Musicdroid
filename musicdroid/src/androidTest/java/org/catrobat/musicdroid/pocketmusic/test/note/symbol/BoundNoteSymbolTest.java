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

import org.catrobat.musicdroid.pocketmusic.note.symbol.BoundNoteSymbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.NoteSymbol;

public class BoundNoteSymbolTest extends AndroidTestCase {

	public void testAddNoteSymbol() {
		BoundNoteSymbol boundNoteSymbol = BoundNoteSymbolTestDataFactory.createBoundNoteSymbolWithNoteSymbol();

		assertEquals(1, boundNoteSymbol.size());
	}

	public void testGetNoteSymbol() {
		BoundNoteSymbol boundNoteSymbol = BoundNoteSymbolTestDataFactory.createBoundNoteSymbol();
		NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol();
		boundNoteSymbol.addNoteSymbol(noteSymbol);

		assertEquals(noteSymbol, boundNoteSymbol.getNoteSymbol(0));
	}

	public void testEquals1() {
		BoundNoteSymbol boundNoteSymbol1 = BoundNoteSymbolTestDataFactory.createBoundNoteSymbol();
		BoundNoteSymbol boundNoteSymbol2 = BoundNoteSymbolTestDataFactory.createBoundNoteSymbol();

		assertTrue(boundNoteSymbol1.equals(boundNoteSymbol2));
	}

	public void testEquals2() {
		BoundNoteSymbol boundNoteSymbol1 = BoundNoteSymbolTestDataFactory.createBoundNoteSymbolWithNoteSymbol();
		BoundNoteSymbol boundNoteSymbol2 = BoundNoteSymbolTestDataFactory.createBoundNoteSymbolWithNoteSymbol();

		assertTrue(boundNoteSymbol1.equals(boundNoteSymbol2));
	}

	public void testEquals3() {
		BoundNoteSymbol boundNoteSymbol1 = BoundNoteSymbolTestDataFactory.createBoundNoteSymbolWithNoteSymbol();
		BoundNoteSymbol boundNoteSymbol2 = BoundNoteSymbolTestDataFactory.createBoundNoteSymbol();

		assertFalse(boundNoteSymbol1.equals(boundNoteSymbol2));
	}

	public void testEquals4() {
		BoundNoteSymbol boundNoteSymbol = BoundNoteSymbolTestDataFactory.createBoundNoteSymbol();

		assertFalse(boundNoteSymbol.equals(null));
	}

	public void testEquals5() {
		BoundNoteSymbol boundNoteSymbol = BoundNoteSymbolTestDataFactory.createBoundNoteSymbol();

		assertFalse(boundNoteSymbol.equals(""));
	}

	public void testToString() {
		BoundNoteSymbol boundNoteSymbol = BoundNoteSymbolTestDataFactory.createBoundNoteSymbol();
		String expectedString = "[BoundNoteSymbol] size: " + boundNoteSymbol.size();

		assertEquals(expectedString, boundNoteSymbol.toString());
	}
}
