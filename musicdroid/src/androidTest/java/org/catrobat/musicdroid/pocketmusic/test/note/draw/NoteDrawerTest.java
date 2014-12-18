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
import org.catrobat.musicdroid.pocketmusic.note.draw.NoteSheetDrawPosition;
import org.catrobat.musicdroid.pocketmusic.note.symbol.NoteSymbol;
import org.catrobat.musicdroid.pocketmusic.test.note.NoteSymbolTestDataFactory;

public class NoteDrawerTest extends AbstractDrawerTest {

    private MusicalKey key;
    private NoteDrawerMock noteDrawer;

    @Override
    protected void setUp() {
        super.setUp();

        key = MusicalKey.VIOLIN;
        noteDrawer = new NoteDrawerMock(noteSheetCanvas, paint, getContext().getResources(), key, drawPosition, DISTANCE_BETWEEN_LINES);
    }

    public void testDrawCross() {
        NoteName noteName = NoteName.C4S;
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol(noteName);
        int xPositionForCross = noteDrawer.getCenterPointForNextSmallSymbolNoDrawPositionChange().x;
        int crossHeight = 2 * DISTANCE_BETWEEN_LINES;
        int yPositionForCross = noteSheetCanvas.getHeightHalf() + NoteName.calculateDistanceToMiddleLineCountingSignedNotesOnly(key, noteName) * DISTANCE_BETWEEN_LINES / 2;

        noteDrawer.drawCross(noteSymbol);

        assertCanvasElementQueueBitmap(R.drawable.cross, crossHeight, xPositionForCross, yPositionForCross);
    }

    public void testDrawBody() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol();

        noteDrawer.drawBody(noteSymbol);

        assertCanvasElementQueueSize(noteSymbol.size());
    }

    public void testDrawStem() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.QUARTER);

        noteDrawer.drawBody(noteSymbol);
        noteDrawer.drawStem(noteSymbol);

        int expectedElementsCount = 2;
        assertCanvasElementQueueSize(expectedElementsCount);
    }

    public void testDrawHelpLines() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol(NoteName.C4);

        noteDrawer.drawBody(noteSymbol);
        noteDrawer.drawHelpLines();

        // TODO
        assertTrue(false);
    }

    public void testDrawSymbol() {
        NoteSymbol noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol(NoteLength.QUARTER, NoteName.C4S);
        noteDrawer.drawSymbol(noteSymbol);

        int stemCount = 1;
        int crossCount = 1;
        int helpLinesCount = 1;
        int bodyCount = 1;
        int expectedElementsCount = stemCount + crossCount + helpLinesCount + bodyCount;

        assertCanvasElementQueueSize(expectedElementsCount);
    }
}
