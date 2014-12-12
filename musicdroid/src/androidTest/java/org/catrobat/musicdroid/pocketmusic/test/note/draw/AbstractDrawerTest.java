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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.test.AndroidTestCase;

import java.util.Queue;

public abstract class AbstractDrawerTest extends AndroidTestCase {

    public static final int DISTANCE_BETWEEN_LINES = 100;
    public static final int NUMBER_OF_LINES_ON_SHEET = 5;
    public static final int NUMBER_OF_BITMAPS_ON_SHEET = 2;
    public static final int NUMBER_OF_BASIC_ELEMENTS_ON_SHEET = NUMBER_OF_LINES_ON_SHEET + NUMBER_OF_BITMAPS_ON_SHEET;

    protected CanvasMock canvas;
    protected NoteSheetCanvasMock noteSheetCanvas;

    @Override
    protected void setUp() {
        canvas = new CanvasMock();
        noteSheetCanvas = new NoteSheetCanvasMock(canvas);
    }

    protected void assertCanvasElementQueueBitmap(int bitmapId, int bitmapHeight, int xPosition, int yPosition) {
        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), bitmapId);

        Rect rect = noteSheetCanvas.calculateProportionalRect(bitmap, bitmapHeight, xPosition, yPosition);

        String expectedLine = CanvasMock.createString(CanvasMock.DRAW_BITMAP, rect.left, rect.top, rect.right, rect.bottom);
        assertEquals(expectedLine, canvas.getDrawnElements().poll());
    }

    protected void assertCanvasElementQueueLine(int startX, int startY, int stopX, int stopY) {
        String expectedLine = CanvasMock.createString(CanvasMock.DRAW_LINE, startX, startY, stopX, stopY);

        assertEquals(expectedLine, canvas.getDrawnElements().poll());
    }

    protected void assertCanvasElementQueueBar(int startX, int startY, int stopX, int stopY) {
        String expectedLine = CanvasMock.createString(CanvasMock.DRAW_RECT, startX, startY, stopX, stopY);

        assertEquals(expectedLine, canvas.getDrawnElements().poll());
    }
}
