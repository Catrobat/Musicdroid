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

import org.catrobat.musicdroid.pocketmusic.note.Track;
import org.catrobat.musicdroid.pocketmusic.note.draw.NoteSheetDrawPosition;
import org.catrobat.musicdroid.pocketmusic.note.draw.TrackDrawer;
import org.catrobat.musicdroid.pocketmusic.note.symbol.TrackToSymbolsConverter;
import org.catrobat.musicdroid.pocketmusic.test.note.TrackTestDataFactory;

public class TrackDrawerTest extends AbstractDrawerTest {

    private Track track;
    private NoteSheetDrawPosition drawPosition;
    private TrackDrawer trackDrawer;

    @Override
    protected void setUp() {
        super.setUp();

        Paint paint = new Paint(); // TODO
        track = TrackTestDataFactory.createSimpleTrack();
        drawPosition = new NoteSheetDrawPosition(100, 100); // TODO
        trackDrawer = new TrackDrawer(noteSheetCanvas, paint, getContext().getResources(), track, drawPosition, DISTANCE_BETWEEN_LINES);
    }

    public void testDrawTrack() {
        TrackToSymbolsConverter converter = new TrackToSymbolsConverter();
        int expectedElementCount = converter.convertTrack(track).size() + NUMBER_OF_BASIC_ELEMENTS_ON_SHEET;

        trackDrawer.drawTrack();

        assertEquals(expectedElementCount, canvas.getDrawnElements().size());
    }
}
