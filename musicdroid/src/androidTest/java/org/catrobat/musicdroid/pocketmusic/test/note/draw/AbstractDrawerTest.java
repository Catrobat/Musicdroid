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
import android.graphics.Paint;
import android.graphics.Rect;
import android.test.AndroidTestCase;

import org.catrobat.musicdroid.pocketmusic.note.draw.NoteSheetDrawPosition;
import org.catrobat.musicdroid.pocketmusic.note.draw.NoteSheetDrawer;

public abstract class AbstractDrawerTest extends AndroidTestCase {

    public static final int START_X_POSITION = 50;
    public static final int END_X_POSITION = 500;

    public static final int NUMBER_OF_LINES_ON_SHEET = 5;
    public static final int NUMBER_OF_BARS_ON_SHEET = 2;
    public static final int NUMBER_OF_BITMAPS_ON_SHEET = 1;
    public static final int NUMBER_OF_BASIC_ELEMENTS_ON_SHEET = NUMBER_OF_LINES_ON_SHEET + NUMBER_OF_BARS_ON_SHEET + NUMBER_OF_BITMAPS_ON_SHEET;

    private static final int DISTANCE_BETWEEN_LINES = 100;

    protected int distanceBetweenLines;
    protected Paint paintDefault;
    protected Paint paintMarked;
    protected CanvasMock canvas;
    protected NoteSheetCanvasMock noteSheetCanvas;
    protected NoteSheetDrawPosition drawPosition;

    @Override
    protected void setUp() {
        distanceBetweenLines = DISTANCE_BETWEEN_LINES;
        paintDefault = new Paint();
        paintDefault.setColor(NoteSheetDrawer.COLOR_DEFAULT);
        paintMarked = new Paint();
        paintMarked.setColor(NoteSheetDrawer.COLOR_MARKED);
        canvas = new CanvasMock();
        noteSheetCanvas = new NoteSheetCanvasMock(canvas);
        drawPosition = new NoteSheetDrawPosition(START_X_POSITION, END_X_POSITION);
    }

    @Override
    protected void tearDown() {
        assertCanvasElementQueueSize(0);
    }

    protected void clearCanvasElementQueue() {
        canvas.getDrawnElements().clear();
    }

    protected void assertCanvasElementQueueSize(int expectedSize) {
        assertEquals(expectedSize, canvas.getDrawnElements().size());
    }

    protected void assertCanvasElementQueueBitmap(int bitmapId, int bitmapHeight, int xPosition, int yPosition, Paint paint) {
        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), bitmapId);

        Rect rect = noteSheetCanvas.calculateProportionalRect(bitmap, bitmapHeight, xPosition, yPosition);

        String expectedLine = CanvasMock.createString(CanvasMock.DRAW_BITMAP, rect.left, rect.top, rect.right, rect.bottom, paint.getColor());
        assertEquals(expectedLine, canvas.getDrawnElements().poll());
    }

    protected void assertCanvasElementQueueLine(float startX, float startY, float stopX, float stopY, Paint paint) {
        String expectedLine = CanvasMock.createString(CanvasMock.DRAW_LINE, startX, startY, stopX, stopY, paint.getColor());

        assertEquals(expectedLine, canvas.getDrawnElements().poll());
    }

    protected void assertCanvasElementQueueRect(int startX, int startY, int stopX, int stopY, Paint paint) {
        String expectedLine = CanvasMock.createString(CanvasMock.DRAW_RECT, startX, startY, stopX, stopY, paint.getColor());

        assertEquals(expectedLine, canvas.getDrawnElements().poll());
    }

    protected void assertCanvasElementQueueOval(float startX, float startY, float stopX, float stopY, Paint paint) {
        String expectedLine = CanvasMock.createString(CanvasMock.DRAW_OVAL, startX, startY, stopX, stopY, paint.getStyle(), paint.getColor());

        assertEquals(expectedLine, canvas.getDrawnElements().poll());
    }

    protected void assertCanvasElementQueuePath(Paint paint) {
        String expectedLine = CanvasMock.createString(CanvasMock.DRAW_PATH, paint.getColor());

        assertEquals(expectedLine, canvas.getDrawnElements().poll());
    }
}
