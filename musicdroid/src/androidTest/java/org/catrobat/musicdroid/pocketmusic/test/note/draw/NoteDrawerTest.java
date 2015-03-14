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

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.NoteLength;
import org.catrobat.musicdroid.pocketmusic.note.NoteName;
import org.catrobat.musicdroid.pocketmusic.note.draw.NoteSheetDrawer;
import org.catrobat.musicdroid.pocketmusic.note.symbol.NoteSymbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.SymbolPosition;
import org.catrobat.musicdroid.pocketmusic.test.note.symbol.NoteSymbolTestDataFactory;

public class NoteDrawerTest extends AbstractDrawerTest {

    private MusicalKey key;
    private NoteDrawerMock noteDrawer;

    @Override
    protected void setUp() {
        super.setUp();

        key = MusicalKey.VIOLIN;
        noteDrawer = new NoteDrawerMock(noteSheetCanvas, paintDefault, getContext().getResources(), key, drawPosition, distanceBetweenLines);
    }

    public void testDrawCross() {
        NoteName noteName = NoteName.C4S;
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol(noteName);
        int xPositionForCross = noteDrawer.getCenterPointForNextSmallSymbolNoDrawPositionChange().x;
        int crossHeight = 2 * distanceBetweenLines;
        int yPositionForCross = noteSheetCanvas.getHalfHeight() + NoteName.calculateDistanceToMiddleLineCountingSignedNotesOnly(key, noteName) * distanceBetweenLines / 2;

        noteDrawer.drawCross(noteSymbol, paintDefault);

        assertCanvasElementQueueBitmap(R.drawable.cross, crossHeight, xPositionForCross, yPositionForCross, paintDefault);
    }

    public void testDrawBody() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol();

        noteDrawer.drawBody(noteSymbol, paintDefault);

        assertCanvasElementQueueSize(noteSymbol.size());
        clearCanvasElementQueue();
    }

    public void testDrawStem1() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.QUARTER);

        SymbolPosition bodyPosition = noteDrawer.drawBody(noteSymbol, paintDefault);
        noteDrawer.drawStem(noteSymbol, bodyPosition, paintDefault);

        int stemCount = 2;
        assertCanvasElementQueueSize(stemCount);
        clearCanvasElementQueue();
    }

    public void testDrawStem2() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.WHOLE);

        SymbolPosition bodyPosition = noteDrawer.drawBody(noteSymbol, paintDefault);
        noteDrawer.drawStem(noteSymbol, bodyPosition, paintDefault);

        int stemCount = 1;
        assertCanvasElementQueueSize(stemCount);
        clearCanvasElementQueue();
    }

    public void testDrawHelpLines2() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol(NoteName.A3);

        SymbolPosition bodyPosition = noteDrawer.drawBody(noteSymbol, paintDefault);
        noteDrawer.drawHelpLines(bodyPosition, paintDefault);

        int bodyCount = 1;
        int helpLineCount = 2;
        assertCanvasElementQueueSize(bodyCount + helpLineCount);
        clearCanvasElementQueue();
    }

    public void testDrawHelpLines() {
        testDrawHelpLines(false);
    }

    public void testDrawHelpLinesMarked() {
        testDrawHelpLines(true);
    }

    private void testDrawHelpLines(boolean marked) {
        Paint paint = marked ? paintMarked : paintDefault;
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol(NoteName.C4);
        noteSymbol.setMarked(marked);

        SymbolPosition bodyPosition = noteDrawer.drawBody(noteSymbol, paint);
        noteDrawer.drawHelpLines(bodyPosition, paint);

        canvas.getDrawnElements().poll();
        assertHelpLines(bodyPosition, paint);
    }

    private void assertHelpLines(SymbolPosition symbolPosition, Paint paint) {
        float topEndOfNoteLines = noteSheetCanvas.getHalfHeight() -
                distanceBetweenLines * NoteSheetDrawer.NUMBER_OF_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS;
        float bottomEndOfNoteLines = noteSheetCanvas.getHalfHeight() +
                distanceBetweenLines * NoteSheetDrawer.NUMBER_OF_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS;

        float topEndOfHelpLines = symbolPosition.getTop() + distanceBetweenLines / 2;
        float bottomEndOfHelpLines = symbolPosition.getBottom() - distanceBetweenLines / 2;

        int lengthOfHelpLine = ((int) symbolPosition.getRight() - (int) symbolPosition.getLeft()) / 3;

        topEndOfNoteLines -= distanceBetweenLines;
        while (topEndOfHelpLines <= topEndOfNoteLines) {
            int startX = (int) (symbolPosition.getLeft() - lengthOfHelpLine);
            int stopX = (int) (symbolPosition.getRight() + lengthOfHelpLine);
            int startY = (int) topEndOfNoteLines;
            int stopY = startY;

            assertCanvasElementQueueLine(startX, startY, stopX, stopY, paint);

            topEndOfNoteLines -= distanceBetweenLines;
        }

        bottomEndOfNoteLines += distanceBetweenLines;
        while (bottomEndOfHelpLines >= bottomEndOfNoteLines) {
            int startX = (int) (symbolPosition.getLeft() - lengthOfHelpLine);
            int stopX = (int) (symbolPosition.getRight() + lengthOfHelpLine);
            int startY = (int) bottomEndOfNoteLines;
            int stopY = startY;

            assertCanvasElementQueueLine(startX, startY, stopX, stopY, paint);

            bottomEndOfNoteLines += distanceBetweenLines;
        }
    }

    public void testDrawSymbol() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.QUARTER, NoteName.C4S);
        noteDrawer.drawSymbol(noteSymbol);

        int stemCount = 1;
        int crossCount = 1;
        int helpLinesCount = 1;
        int bodyCount = 1;
        assertCanvasElementQueueSize(stemCount + crossCount + helpLinesCount + bodyCount);
        clearCanvasElementQueue();
    }
}
