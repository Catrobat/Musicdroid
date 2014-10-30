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
import android.graphics.Point;
import android.graphics.Rect;
import android.test.AndroidTestCase;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.note.Track;
import org.catrobat.musicdroid.pocketmusic.note.draw.NoteSheetCanvas;
import org.catrobat.musicdroid.pocketmusic.note.draw.PianoNoteSheetCanvas;
import org.catrobat.musicdroid.pocketmusic.note.draw.PictureTools;
import org.catrobat.musicdroid.pocketmusic.test.note.TrackTestDataFactory;

import java.util.Queue;

/**
 * Created by Daniel on 21.10.2014.
 */
public class PianoNoteSheetTest extends AndroidTestCase {

    private int startXPositionForNextElement;
    private CanvasMock canvas;
    private PianoNoteSheetCanvasMock noteSheetCanvas;

    @Override
    protected void setUp() {
        startXPositionForNextElement = NoteSheetCanvas.NOTE_SHEET_PADDING;
        canvas = CanvasTestDataFactory.createCanvasMock();
        Track track = TrackTestDataFactory.createTrack();
        noteSheetCanvas = PianoNoteSheetCanvasTestDataFactory.createPianoNoteSheetCanvas(getContext(), canvas, track);

        noteSheetCanvas.draw();
    }

    public void testDraw() {
        assertCanvas(noteSheetCanvas, canvas);
    }

    private void assertCanvas(PianoNoteSheetCanvasMock noteSheetCanvas, CanvasMock canvas) {
        assertCanvasLines(noteSheetCanvas, canvas);
        assertCanvasLineBars(noteSheetCanvas, canvas);
        assertCanvasKey(noteSheetCanvas, canvas);
        assertCanvasTactUnit(noteSheetCanvas, canvas);
        assertTrue((canvas.getDrawnElements().isEmpty()));
    }

    private void assertCanvasLines(PianoNoteSheetCanvas noteSheetCanvas, CanvasMock canvas) {
        Queue<String> drawnElements = canvas.getDrawnElements();

        int distanceBetweenLines = noteSheetCanvas.getDistanceBetweenLines();
        int yPositionBottomLine = noteSheetCanvas.getYPositionOfCenterLine() + NoteSheetCanvas.NUMBER_OF_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS * distanceBetweenLines;
        int yPositionTopLine = noteSheetCanvas.getYPositionOfCenterLine() - NoteSheetCanvas.NUMBER_OF_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS * distanceBetweenLines;
        int startX = NoteSheetCanvas.START_X_POSITION_FOR_DRAWING;
        int stopX = canvas.getWidth() - NoteSheetCanvas.NOTE_SHEET_PADDING;

        for (int yPositionLine = yPositionTopLine; yPositionLine <= yPositionBottomLine; yPositionLine += distanceBetweenLines) {
            String expectedLine = CanvasMock.createString(startX, yPositionLine, stopX, yPositionLine);
            String actualLine = drawnElements.poll();

            assertEquals(expectedLine, actualLine);
        }
    }

    private void assertCanvasLineBars(PianoNoteSheetCanvasMock noteSheetCanvas, CanvasMock canvas) {
        int yPositionOfBarTop = noteSheetCanvas.getYPositionOfBarTop();
        int yPositionOfBarBottom = noteSheetCanvas.getYPositionOfBarBottom();
        int endXPositionForDrawingElements = noteSheetCanvas.getEndXPositionForDrawingElements();

        Queue<String> drawnElements = canvas.getDrawnElements();

        String expectedLine = CanvasMock.createString(startXPositionForNextElement, yPositionOfBarTop, startXPositionForNextElement + NoteSheetCanvas.BOLD_BAR_WIDTH, yPositionOfBarBottom);
        String actualLine = drawnElements.poll();

        assertEquals(expectedLine, actualLine);

        startXPositionForNextElement += 2 * NoteSheetCanvas.BOLD_BAR_WIDTH;

        expectedLine = CanvasMock.createString(startXPositionForNextElement, yPositionOfBarTop, startXPositionForNextElement + NoteSheetCanvas.THIN_BAR_WIDTH, yPositionOfBarBottom);
        actualLine = drawnElements.poll();

        assertEquals(expectedLine, actualLine);

        startXPositionForNextElement += NoteSheetCanvas.BOLD_BAR_WIDTH;

        expectedLine = CanvasMock.createString(endXPositionForDrawingElements - NoteSheetCanvas.BOLD_BAR_WIDTH, yPositionOfBarTop, endXPositionForDrawingElements, yPositionOfBarBottom);
        actualLine = drawnElements.poll();

        assertEquals(expectedLine, actualLine);
    }

    private void assertCanvasKey(PianoNoteSheetCanvas noteSheetCanvas, CanvasMock canvas) {
        int keyPictureHeight = noteSheetCanvas.getDistanceBetweenLines() * PianoNoteSheetCanvas.HEIGHT_OF_KEY_IN_LINE_SPACES;

        assertCanvasBitmap(canvas, R.drawable.violine, keyPictureHeight, startXPositionForNextElement, noteSheetCanvas.getYPositionOfCenterLine());
    }

    private void assertCanvasTactUnit(PianoNoteSheetCanvas noteSheetCanvas, CanvasMock canvas) {
        int tactPictureHeight = noteSheetCanvas.getDistanceBetweenLines() * PianoNoteSheetCanvas.HEIGHT_OF_TACT_UNIT_IN_LINE_SPACES;

        assertCanvasBitmap(canvas, R.drawable.tact_3_4, tactPictureHeight, startXPositionForNextElement, noteSheetCanvas.getYPositionOfCenterLine());
    }

    private void assertCanvasBitmap(CanvasMock canvas, int bitmapId, int bitmapHeight, int xPosition, int yPosition) {
        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), bitmapId);

        Rect rect = PictureTools.calculateProportionalPictureContourRect(bitmap, bitmapHeight,
                xPosition, yPosition);

        String expectedLine = CanvasMock.createString(rect.left, rect.top, rect.right, rect.bottom);
        String actualLine = canvas.getDrawnElements().poll();

        assertEquals(expectedLine, actualLine);

        startXPositionForNextElement = rect.right;
    }

    public void testGetCenterPointForNextSmallSymbol() {
        Point expectedPoint = new Point(noteSheetCanvas.getWidthForDrawingTrack() + noteSheetCanvas.getWidthForOneSmallSymbol() / 2, noteSheetCanvas.getYPositionOfCenterLine());
        Point actualPoint = noteSheetCanvas.getCenterPointForNextSmallSymbol();

        assertEquals(expectedPoint, actualPoint);
    }

    public void testGetCenterPointForNextSymbol() {
        Point expectedPoint = new Point(noteSheetCanvas.getWidthForDrawingTrack() + noteSheetCanvas.getWidthForOneSymbol() / 2, noteSheetCanvas.getYPositionOfCenterLine());
        Point actualPoint = noteSheetCanvas.getCenterPointForNextSymbol();

        assertEquals(expectedPoint, actualPoint);
    }
}
