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
import android.graphics.RectF;

import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.NoteName;
import org.catrobat.musicdroid.pocketmusic.note.draw.NoteBodyDrawer;
import org.catrobat.musicdroid.pocketmusic.note.draw.NotePositionInformation;
import org.catrobat.musicdroid.pocketmusic.note.draw.NoteSheetDrawPosition;
import org.catrobat.musicdroid.pocketmusic.note.symbol.NoteSymbol;
import org.catrobat.musicdroid.pocketmusic.test.note.NoteSymbolTestDataFactory;

import java.util.LinkedList;
import java.util.List;

public class NoteBodyDrawerTest extends AbstractDrawerTest {

    private NoteSymbol noteSymbol;
    private MusicalKey key;
    private SymbolDrawerMock symbolDrawer;
    private NoteBodyDrawer noteBodyDrawer;

    @Override
    protected void setUp() {
        super.setUp();

        noteSymbol = NoteSymbolTestDataFactory.createNoteSymbol();
    }

    private void customSetUp(MusicalKey key) {
        this.key = key;
        symbolDrawer = new SymbolDrawerMock(noteSheetCanvas, paint, getContext().getResources(), key, drawPosition, distanceBetweenLines);
        noteBodyDrawer = new NoteBodyDrawer(symbolDrawer, noteSheetCanvas, paint, key, distanceBetweenLines);
    }

    public void testDrawBodySimple1() {
        customSetUp(MusicalKey.VIOLIN);

        NotePositionInformation positionInformation = noteBodyDrawer.drawBody(noteSymbol);

        assertCanvasElementQueueNoteBody(noteSymbol, positionInformation);
    }

    public void testDrawBodySimple2() {
        customSetUp(MusicalKey.BASS);

        NotePositionInformation positionInformation = noteBodyDrawer.drawBody(noteSymbol);

        assertCanvasElementQueueNoteBody(noteSymbol, positionInformation);
    }

    private void assertCanvasElementQueueNoteBody(NoteSymbol noteSymbol, NotePositionInformation actualPositionInformation) {
        symbolDrawer.setNoteSheetDrawPosition(new NoteSheetDrawPosition(START_X_POSITION, END_X_POSITION));
        boolean isStemUpdirected = noteSymbol.isStemUp(key);
        int lineHeight = distanceBetweenLines;
        int noteHeight = lineHeight / 2;
        int noteWidth = noteHeight * NoteBodyDrawer.NOTE_WIDTH_SCALE;

        Point centerPointOfSpaceForNote = symbolDrawer.getCenterPointForNextSymbol();
        List<RectF> noteSurroundingRects = new LinkedList<RectF>();
        NoteName prevNoteName = null;

        for (NoteName noteName : noteSymbol.getNoteNamesSorted()) {
            Point centerPointOfActualNote = new Point(centerPointOfSpaceForNote);
            centerPointOfActualNote.y += NoteName.calculateDistanceToMiddleLineCountingSignedNotesOnly(key, noteName)
                    * noteHeight;
            int left = centerPointOfActualNote.x - noteWidth;
            int top = centerPointOfActualNote.y - noteHeight;
            int right = centerPointOfActualNote.x + noteWidth;
            int bottom = centerPointOfActualNote.y + noteHeight;

            if (prevNoteName != null) {
                int differenceBetweenNotesInHalfTones = Math.abs(NoteName.calculateDistanceCountingNoneSignedNotesOnly(
                        prevNoteName, noteName));

                if (differenceBetweenNotesInHalfTones == 1) {
                    if (isStemUpdirected) {
                        right += 2 * noteWidth;
                        left += 2 * noteWidth;
                    } else {
                        left -= 2 * noteWidth;
                        right -= 2 * noteWidth;
                    }
                }
            }

            RectF rect = new RectF(left, top, right, bottom);

            noteSurroundingRects.add(rect);
            assertCanvasElementQueueOval(left, top, right, bottom);

            prevNoteName = noteName;
        }

        NotePositionInformation expectedPositionInformation = new NotePositionInformation(noteSurroundingRects);

        assertEquals(expectedPositionInformation, actualPositionInformation);
    }
}
