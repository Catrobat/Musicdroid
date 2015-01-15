/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://developer.catrobat.org/license_additional_term
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.musicdroid.pocketmusic.note.draw;

import android.content.res.Resources;
import android.graphics.Paint;

import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.symbol.BreakSymbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.NoteSymbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.Symbol;

import java.util.List;

public class SymbolsDrawer {

    private List<Symbol> symbols;

    private NoteDrawer noteDrawer;
    private BreakDrawer breakDrawer;

    public SymbolsDrawer(NoteSheetCanvas noteSheetCanvas, Paint paint, Resources resources, List<Symbol> symbols, MusicalKey key, NoteSheetDrawPosition drawPosition, int distanceBetweenLines) {
        this.symbols = symbols;

        noteDrawer = new NoteDrawer(noteSheetCanvas, paint, resources, key, drawPosition, distanceBetweenLines);
        breakDrawer = new BreakDrawer(noteSheetCanvas, paint, resources, key, drawPosition, distanceBetweenLines);
    }

	public void drawTrack() {
		for (Symbol symbol : symbols) {
            if (symbol instanceof NoteSymbol) {
                noteDrawer.drawSymbol(symbol);
            } else if (symbol instanceof BreakSymbol) {
                breakDrawer.drawSymbol(symbol);
            } else {
                throw new IllegalArgumentException("Not supported symbol: " + symbol);
            }
		}
	}
}
