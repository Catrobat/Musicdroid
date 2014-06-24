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
package org.catrobat.musicdroid.pocketmusic.note.symbol;

import java.util.LinkedList;
import java.util.List;

public class BoundNoteSymbol implements Symbol {

	private List<NoteSymbol> noteSymbols;

	public BoundNoteSymbol() {
		noteSymbols = new LinkedList<NoteSymbol>();
	}

	public void addNoteSymbol(NoteSymbol noteSymbol) {
		noteSymbols.add(noteSymbol);
	}

	public NoteSymbol getNoteSymbol(int location) {
		return noteSymbols.get(location);
	}

	public int size() {
		return noteSymbols.size();
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof BoundNoteSymbol)) {
			return false;
		}

		BoundNoteSymbol accordSymbol = (BoundNoteSymbol) obj;

		if (noteSymbols.equals(accordSymbol.noteSymbols)) {
			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		return "[BoundNoteSymbol] size: " + size();
	}
}
