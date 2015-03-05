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

package org.catrobat.musicdroid.pocketmusic.test.note.draw;

import android.graphics.Point;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.NoteLength;
import org.catrobat.musicdroid.pocketmusic.note.draw.BreakDrawer;
import org.catrobat.musicdroid.pocketmusic.note.symbol.BreakSymbol;
import org.catrobat.musicdroid.pocketmusic.test.note.symbol.BreakSymbolTestDataFactory;

public class BreakDrawerTest extends AbstractDrawerTest {

    private BreakDrawerMock breakDrawer;

    @Override
    protected void setUp() {
        super.setUp();
        MusicalKey key = MusicalKey.VIOLIN;

        breakDrawer = new BreakDrawerMock(noteSheetCanvas, paintDefault, getContext().getResources(), key, drawPosition, distanceBetweenLines);
    }

    public void testDrawSymbolBitmap() {
        testDrawSymbolBitmap(false);
    }

    public void testDrawSymbolBitmapMarked() {
        testDrawSymbolBitmap(true);
    }

    private void testDrawSymbolBitmap(boolean marked) {
        int breakHeight = BreakDrawer.QUARTER_BREAK_HEIGHT * distanceBetweenLines;
        int xPosition = breakDrawer.getCenterPointForNextSymbolNoDrawPositionChange().x;
        BreakSymbol breakSymbol = BreakSymbolTestDataFactory.createBreakSymbol(NoteLength.QUARTER);
        breakSymbol.setMarked(marked);

        breakDrawer.drawSymbol(breakSymbol);

        assertCanvasElementQueueBitmap(R.drawable.break_4, breakHeight, xPosition, noteSheetCanvas.getHalfHeight(), marked ? paintMarked : paintDefault);
    }

    public void testDrawSymbolRect() {
        testDrawSymbolRect(NoteLength.HALF, false);
    }

    public void testDrawSymbolRect2() {
        testDrawSymbolRect(NoteLength.WHOLE, false);
    }

    public void testDrawSymbolRectMarked() {
        testDrawSymbolRect(NoteLength.HALF, true);
    }

    public void testDrawSymbolRectMarked2() {
        testDrawSymbolRect(NoteLength.WHOLE, true);
    }

    private void testDrawSymbolRect(NoteLength noteLength, boolean marked) {
        BreakSymbol breakSymbol = BreakSymbolTestDataFactory.createBreakSymbol(noteLength);
        breakSymbol.setMarked(marked);

        Point centerPoint = breakDrawer.getCenterPointForNextSymbolNoDrawPositionChange();
        int breakWidthHalf = distanceBetweenLines / 2;
        int startX = centerPoint.x - breakWidthHalf;
        int stopX = centerPoint.x + breakWidthHalf;

        int startY = 0;
        int stopY = 0;

        if (NoteLength.HALF == noteLength) {
            startY = centerPoint.y - breakWidthHalf;
            stopY = centerPoint.y;
        } else if (NoteLength.WHOLE == noteLength) {
            startY = centerPoint.y;
            stopY = centerPoint.y + breakWidthHalf;
        }

        breakDrawer.drawSymbol(breakSymbol);

        assertCanvasElementQueueRect(startX, startY, stopX, stopY, marked ? paintMarked : paintDefault);
    }

    public void testDrawSymbolDot() {
        BreakSymbol breakSymbol = BreakSymbolTestDataFactory.createBreakSymbol(NoteLength.QUARTER_DOT);

        breakDrawer.drawSymbol(breakSymbol);

        int breakCount = 1;
        int dotCount = 1;
        assertCanvasElementQueueSize(breakCount + dotCount);
        clearCanvasElementQueue();
    }
}
