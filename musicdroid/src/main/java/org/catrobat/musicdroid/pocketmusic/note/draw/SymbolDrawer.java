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
package org.catrobat.musicdroid.pocketmusic.note.draw;

import android.content.Context;


import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;

import org.catrobat.musicdroid.pocketmusic.note.symbol.BoundNoteSymbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.BreakSymbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.NoteSymbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.Symbol;

public class SymbolDrawer {

	private NoteSheetCanvas noteSheetCanvas;
	private Context context;
	private MusicalKey key;
	private NoteDrawer noteDrawer;
	private BreakDrawer breakDrawer;

	public SymbolDrawer(NoteSheetCanvas noteSheetCanvas, Context context, MusicalKey key) {
		this.noteSheetCanvas = noteSheetCanvas;
		this.key = key;
		this.context = context;
		this.noteDrawer = new NoteDrawer(noteSheetCanvas, key, context);
		this.breakDrawer = new BreakDrawer(noteSheetCanvas, context);
	}

	public void drawSymbol(Symbol symbol) {
		if (symbol instanceof BreakSymbol) {
			breakDrawer.drawBreak((BreakSymbol) symbol);
		} else if (symbol instanceof NoteSymbol) {
			noteDrawer.drawNoteSymbol((NoteSymbol) symbol);
		} else if (symbol instanceof BoundNoteSymbol) {
			drawBoundNoteSymbol((BoundNoteSymbol) symbol, noteSheetCanvas, context);
		} else {
			throw new IllegalArgumentException();
		}
	}

	private void drawBoundNoteSymbol(BoundNoteSymbol symbol, NoteSheetCanvas noteSheetCanvas, Context context) {
		// TODO das Dream Team Eli und Flo :D
	}

}
