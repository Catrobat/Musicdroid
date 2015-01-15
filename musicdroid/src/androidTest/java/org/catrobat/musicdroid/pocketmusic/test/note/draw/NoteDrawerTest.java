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

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.NoteLength;
import org.catrobat.musicdroid.pocketmusic.note.NoteName;
import org.catrobat.musicdroid.pocketmusic.note.draw.SymbolCoordinates;
import org.catrobat.musicdroid.pocketmusic.note.draw.NoteSheetDrawer;
import org.catrobat.musicdroid.pocketmusic.note.symbol.NoteSymbol;
import org.catrobat.musicdroid.pocketmusic.test.note.symbol.NoteSymbolTestDataFactory;

public class NoteDrawerTest extends AbstractDrawerTest {

    private MusicalKey key;
    private NoteDrawerMock noteDrawer;

    @Override
    protected void setUp() {
        super.setUp();

        key = MusicalKey.VIOLIN;
        noteDrawer = new NoteDrawerMock(noteSheetCanvas, paint, getContext().getResources(), key, drawPosition, distanceBetweenLines);
    }

    public void testDrawCross() {
        NoteName noteName = NoteName.C4S;
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol(noteName);
        int xPositionForCross = noteDrawer.getCenterPointForNextSmallSymbolNoDrawPositionChange().x;
        int crossHeight = 2 * distanceBetweenLines;
        int yPositionForCross = noteSheetCanvas.getHeightHalf() + NoteName.calculateDistanceToMiddleLineCountingSignedNotesOnly(key, noteName) * distanceBetweenLines / 2;

        noteDrawer.drawCross(noteSymbol);

        assertCanvasElementQueueBitmap(R.drawable.cross, crossHeight, xPositionForCross, yPositionForCross);
    }

    public void testDrawBody() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol();

        noteDrawer.drawBody(noteSymbol, paint);

        assertCanvasElementQueueSize(noteSymbol.size());
        clearCanvasElementQueue();
    }

    public void testDrawStem1() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.QUARTER);

        noteDrawer.drawBody(noteSymbol, paint);
        noteDrawer.drawStem(noteSymbol, paint);

        int stemCount = 2;
        assertCanvasElementQueueSize(stemCount);
        clearCanvasElementQueue();
    }

    public void testDrawStem2() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.WHOLE);

        noteDrawer.drawBody(noteSymbol, paint);
        noteDrawer.drawStem(noteSymbol, paint);

        int stemCount = 1;
        assertCanvasElementQueueSize(stemCount);
        clearCanvasElementQueue();
    }

    public void testDrawHelpLines1() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol(NoteName.A3);

        noteDrawer.drawBody(noteSymbol, paint);
        noteDrawer.drawHelpLines(paint);

        int bodyCount = 1;
        int helpLineCount = 2;
        assertCanvasElementQueueSize(bodyCount + helpLineCount);
        clearCanvasElementQueue();
    }

    public void testDrawHelpLines2() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol(NoteName.C4);

        noteDrawer.drawBody(noteSymbol, paint);
        noteDrawer.drawHelpLines(paint);

        canvas.getDrawnElements().poll();
        assertHelpLines(noteDrawer.getNotePositionInformation());
    }

    private void assertHelpLines(SymbolCoordinates symbolCoordinates) {
        float topEndOfNoteLines = noteSheetCanvas.getHeightHalf() -
                distanceBetweenLines * NoteSheetDrawer.NUMBER_OF_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS;
        float bottomEndOfNoteLines = noteSheetCanvas.getHeightHalf() +
                distanceBetweenLines * NoteSheetDrawer.NUMBER_OF_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS;

        float topEndOfHelpLines = symbolCoordinates.getTop() + distanceBetweenLines / 2;
        float bottomEndOfHelpLines = symbolCoordinates.getBottom() - distanceBetweenLines / 2;

        int lengthOfHelpLine = ((int) symbolCoordinates.getRight() - (int) symbolCoordinates.getLeft()) / 3;

        topEndOfNoteLines -= distanceBetweenLines;
        while(topEndOfHelpLines <= topEndOfNoteLines) {
            int startX = (int) (symbolCoordinates.getLeft() - lengthOfHelpLine);
            int stopX = (int) (symbolCoordinates.getRight() + lengthOfHelpLine);
            int startY = (int) topEndOfNoteLines;
            int stopY = startY;

            assertCanvasElementQueueLine(startX, startY, stopX, stopY);

            topEndOfNoteLines -= distanceBetweenLines;
        }

        bottomEndOfNoteLines += distanceBetweenLines;
        while(bottomEndOfHelpLines >= bottomEndOfNoteLines) {
            int startX = (int) (symbolCoordinates.getLeft() - lengthOfHelpLine);
            int stopX = (int) (symbolCoordinates.getRight() + lengthOfHelpLine);
            int startY = (int) bottomEndOfNoteLines;
            int stopY = startY;

            assertCanvasElementQueueLine(startX, startY, stopX, stopY);

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
