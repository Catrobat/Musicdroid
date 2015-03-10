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

import android.test.AndroidTestCase;
import android.view.MotionEvent;

import org.catrobat.musicdroid.pocketmusic.note.symbol.SymbolPosition;
import org.catrobat.musicdroid.pocketmusic.test.note.symbol.SymbolContainerTestDataFactory;

public class NoteSheetViewTest extends AndroidTestCase {

    private NoteSheetViewMock noteSheetView;

    @Override
    protected void setUp() {
        noteSheetView = new NoteSheetViewMock(getContext(), null);
        noteSheetView.redraw(SymbolContainerTestDataFactory.createSimpleSymbolContainer());
        noteSheetView.draw();
    }

    public void testTouchElement() {
        int elementIndex = 0;
        SymbolPosition symbolPosition = noteSheetView.getSymbolPosition(elementIndex);

        touch(symbolPosition.getLeft(), symbolPosition.getTop());

        assertTrue(noteSheetView.isSymbolMarked(elementIndex));
    }

    public void testTouchNoElement() {
        touch(0, 0);

        for (int i = 0; i < noteSheetView.getSymbolsSize(); i++) {
            assertFalse(noteSheetView.isSymbolMarked(i));
        }
    }

    private void touch(float x, float y) {
        MotionEvent motionEvent = MotionEvent.obtain(1, 1, MotionEvent.ACTION_UP, x, y, 0);

        noteSheetView.onEditMode(motionEvent);
    }

    public void testResetSymbolMarkers() {
        for (int i = 0; i < noteSheetView.getSymbolsSize(); i++) {
            noteSheetView.setSymbolMarked(i);
        }

        noteSheetView.resetSymbolMarkers();

        for (int i = 0; i < noteSheetView.getSymbolsSize(); i++) {
            assertFalse(noteSheetView.isSymbolMarked(i));
        }
    }

    public void testDeleteMarkedSymbols() {
        int expectedSize = noteSheetView.getSymbolsSize() - 1;
        noteSheetView.setSymbolMarked(0);

        noteSheetView.deleteMarkedSymbols();

        assertEquals(expectedSize, noteSheetView.getSymbolsSize());
    }
}
