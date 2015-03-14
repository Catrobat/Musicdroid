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
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.Track;
import org.catrobat.musicdroid.pocketmusic.note.draw.NoteSheetDrawPosition;
import org.catrobat.musicdroid.pocketmusic.note.draw.NoteSheetDrawer;
import org.catrobat.musicdroid.pocketmusic.note.symbol.TrackToSymbolContainerConverter;
import org.catrobat.musicdroid.pocketmusic.test.note.TrackTestDataFactory;

public class NoteSheetDrawerTest extends AbstractDrawerTest {

    private NoteSheetDrawerMock noteSheetDrawer;

    @Override
    protected void setUp() {
        super.setUp();

        Track track = TrackTestDataFactory.createTrack();
        TrackToSymbolContainerConverter trackConverter = new TrackToSymbolContainerConverter();
        noteSheetDrawer = new NoteSheetDrawerMock(noteSheetCanvas, getContext().getResources(), trackConverter.convertTrack(track, Project.DEFAULT_BEATS_PER_MINUTE));
        drawPosition = new NoteSheetDrawPosition(NoteSheetDrawer.NOTE_SHEET_PADDING, canvas.getWidth() - NoteSheetDrawer.NOTE_SHEET_PADDING);
    }

    public void testCalculateDistanceBetweenLines() {
        int expectedLineHeight = canvas.getHeight() / NoteSheetDrawer.POSSIBLE_LINE_SPACES_ON_SCREEN;

        if (expectedLineHeight % 2 != 0) {
            expectedLineHeight -= 1;
        }

        int actualLineHeight = noteSheetDrawer.getDistanceBetweenLines();

        assertEquals(expectedLineHeight, actualLineHeight);
    }

    public void testDrawLines() {
        noteSheetDrawer.drawLines();

        for (int i = noteSheetDrawer.getYPositionOfBarTop(); i <= noteSheetDrawer.getYPositionOfBarBottom(); i += noteSheetDrawer.getDistanceBetweenLines()) {
            assertCanvasElementQueueLine(drawPosition.getStartXPositionForNextElement(), i, drawPosition.getEndXPositionForDrawingElements(), i, paintDefault);
        }
    }

    public void testDrawBars() {
        noteSheetDrawer.drawBars();

        assertCanvasElementQueueRect(drawPosition.getStartXPositionForNextElement(), noteSheetDrawer.getYPositionOfBarTop(), drawPosition.getStartXPositionForNextElement() + NoteSheetDrawer.BOLD_BAR_WIDTH, noteSheetDrawer.getYPositionOfBarBottom(), paintDefault);
        assertCanvasElementQueueRect(drawPosition.getEndXPositionForDrawingElements() - NoteSheetDrawer.BOLD_BAR_WIDTH, noteSheetDrawer.getYPositionOfBarTop(), drawPosition.getEndXPositionForDrawingElements(), noteSheetDrawer.getYPositionOfBarBottom(), paintDefault);
    }

    public void testDrawKey() {
        noteSheetDrawer.drawKey();

        int expectedHeight = noteSheetDrawer.getDistanceBetweenLines() * NoteSheetDrawer.HEIGHT_OF_KEY_IN_LINE_SPACES;

        assertCanvasElementQueueBitmap(R.drawable.violine, expectedHeight, drawPosition.getStartXPositionForNextElement(), noteSheetCanvas.getHalfHeight(), paintDefault);
    }

    public void testDrawNoteSheet() {
        noteSheetDrawer.drawNoteSheet();

        assertCanvasElementQueueSize(NUMBER_OF_BASIC_ELEMENTS_ON_SHEET);
        clearCanvasElementQueue();
    }
}
