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

package org.catrobat.musicdroid.pocketmusic.test.instrument.noteSheet;

import android.content.Context;
import android.util.AttributeSet;

import org.catrobat.musicdroid.pocketmusic.instrument.noteSheet.NoteSheetView;
import org.catrobat.musicdroid.pocketmusic.note.draw.NoteSheetCanvas;
import org.catrobat.musicdroid.pocketmusic.note.draw.NoteSheetDrawer;
import org.catrobat.musicdroid.pocketmusic.note.symbol.SymbolPosition;
import org.catrobat.musicdroid.pocketmusic.test.note.draw.CanvasMock;

public class NoteSheetViewMock extends NoteSheetView {

    public NoteSheetViewMock(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isSymbolMarked(int index) {
        return symbolContainer.get(index).isMarked();
    }

    public void setSymbolMarked(int index) {
        symbolContainer.get(index).setMarked(true);
    }

    public void resetSymbolMarkers() {
        symbolContainer.resetSymbolMarkers();
    }

    public void deleteMarkedSymbols() {
        symbolContainer.deleteMarkedSymbols();
    }

    public void draw() {
        noteSheetCanvas = new NoteSheetCanvas(new CanvasMock());
        noteSheetDrawer = new NoteSheetDrawer(noteSheetCanvas, getResources(), symbolContainer);
        noteSheetDrawer.drawNoteSheet();
    }

    public int getSymbolsSize() {
        return symbolContainer.size();
    }

    public SymbolPosition getSymbolPosition(int elementIndex) {
        return symbolContainer.get(elementIndex).getSymbolPosition();
    }
}
