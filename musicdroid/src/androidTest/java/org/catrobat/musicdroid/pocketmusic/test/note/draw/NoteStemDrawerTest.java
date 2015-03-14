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

import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.NoteLength;
import org.catrobat.musicdroid.pocketmusic.note.NoteName;
import org.catrobat.musicdroid.pocketmusic.note.draw.NoteStemDrawer;
import org.catrobat.musicdroid.pocketmusic.note.symbol.SymbolPosition;
import org.catrobat.musicdroid.pocketmusic.test.note.symbol.NoteSymbolTestDataFactory;
import org.catrobat.musicdroid.pocketmusic.test.note.symbol.SymbolPositionTestDataFactory;

public class NoteStemDrawerTest extends AbstractDrawerTest {

    private static final float RECT_LEFT = 100;
    private static final float RECT_TOP = 500;
    private static final float RECT_RIGHT = 300;
    private static final float RECT_BOTTOM = 200;

    private NoteStemDrawer noteStemDrawer;
    private SymbolPosition symbolPosition;
    private int distanceBetweenLinesHalf;
    private int stemLength;

    @Override
    protected void setUp() {
        super.setUp();

        noteStemDrawer = new NoteStemDrawer(noteSheetCanvas, distanceBetweenLines);
        symbolPosition = SymbolPositionTestDataFactory.createSymbolPosition(new RectF(RECT_LEFT, RECT_TOP, RECT_RIGHT, RECT_BOTTOM));

        distanceBetweenLinesHalf = distanceBetweenLines / 2;
        stemLength = (int) (Math.round(NoteStemDrawer.LENGTH_OF_STEM_IN_NOTE_LINE_DISTANCES * distanceBetweenLines));
    }

    public void testDrawStemUpDirected() {
        testDrawStemUpDirected(paintDefault);
    }

    public void testDrawStemUpDirectedMarked() {
        testDrawStemUpDirected(paintMarked);
    }

    private void testDrawStemUpDirected(Paint paint) {
        PointF expectedStartPointOfStem = new PointF(symbolPosition.getRight(), symbolPosition.getBottom() - distanceBetweenLinesHalf);
        PointF expectedEndPointOfStem = new PointF(expectedStartPointOfStem.x, symbolPosition.getTop() - stemLength);

        noteStemDrawer.drawStem(symbolPosition, NoteSymbolTestDataFactory.createNoteSymbol(NoteName.C4), MusicalKey.VIOLIN, paint);

        assertCanvasElementQueueLine(expectedStartPointOfStem.x, expectedStartPointOfStem.y, expectedEndPointOfStem.x, expectedEndPointOfStem.y, paint);
    }

    public void testDrawStemDownDirected() {
        testDrawStemDownDirected(paintDefault);
    }

    public void testDrawStemDownDirectedMarked() {
        testDrawStemDownDirected(paintMarked);
    }

    private void testDrawStemDownDirected(Paint paint) {
        noteStemDrawer.drawStem(symbolPosition, NoteSymbolTestDataFactory.createNoteSymbol(NoteName.C5), MusicalKey.VIOLIN, paint);

        assertCanvasElementQueueLine(symbolPosition.getLeft(), symbolPosition.getBottom() + stemLength, symbolPosition.getLeft(), symbolPosition.getTop() + distanceBetweenLinesHalf, paint);
    }

    public void testDrawStemWithFlag1() {
        noteStemDrawer.drawStem(symbolPosition, NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.EIGHT), MusicalKey.VIOLIN, paintDefault);

        int stemCount = 1;
        int flagCount = 1;

        assertCanvasElementQueueSize(stemCount + flagCount);
        clearCanvasElementQueue();
    }

    public void testDrawStemWithFlag2() {
        noteStemDrawer.drawStem(symbolPosition, NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.SIXTEENTH), MusicalKey.VIOLIN, paintDefault);

        int stemCount = 1;
        int flagCount = 2;

        assertCanvasElementQueueSize(stemCount + flagCount);
        clearCanvasElementQueue();
    }
}
