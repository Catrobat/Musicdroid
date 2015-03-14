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

import org.catrobat.musicdroid.pocketmusic.note.NoteName;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.Track;
import org.catrobat.musicdroid.pocketmusic.note.draw.SymbolsDrawer;
import org.catrobat.musicdroid.pocketmusic.note.symbol.BreakSymbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.NoteSymbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.Symbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.SymbolContainer;
import org.catrobat.musicdroid.pocketmusic.note.symbol.TrackToSymbolContainerConverter;
import org.catrobat.musicdroid.pocketmusic.test.note.TrackTestDataFactory;

public class SymbolsDrawerTest extends AbstractDrawerTest {

    private Track track;
    private SymbolContainer symbolContainer;
    private SymbolsDrawer symbolsDrawer;

    @Override
    protected void setUp() {
        super.setUp();

        TrackToSymbolContainerConverter trackConverter = new TrackToSymbolContainerConverter();
        track = TrackTestDataFactory.createTrackWithBreak();
        symbolContainer = trackConverter.convertTrack(track, Project.DEFAULT_BEATS_PER_MINUTE);
        symbolsDrawer = new SymbolsDrawer(noteSheetCanvas, paintDefault, getContext().getResources(), symbolContainer, drawPosition, distanceBetweenLines);
    }

    public void testDrawSymbols1() {
        int expectedElementCount = getSymbolCountFromTrack(track);

        symbolsDrawer.drawSymbols();

        assertCanvasElementQueueSize(expectedElementCount);
        clearCanvasElementQueue();
    }

    private int getSymbolCountFromTrack(Track track) {
        TrackToSymbolContainerConverter converter = new TrackToSymbolContainerConverter();
        SymbolContainer symbols = converter.convertTrack(track, Project.DEFAULT_BEATS_PER_MINUTE);
        int distanceWhenHelpLinesStart = 5;

        int count = 0;

        for (int i = 0; i < symbols.size(); i++) {
            Symbol symbol = symbols.get(i);

            if (symbol instanceof NoteSymbol) {
                NoteSymbol noteSymbol = (NoteSymbol) symbol;

                if (noteSymbol.hasStem()) {
                    count++;
                }

                for (NoteName noteName : noteSymbol.getNoteNamesSorted()) {
                    count++;
                    int distance = Math.abs(NoteName.calculateDistanceToMiddleLineCountingSignedNotesOnly(track.getKey(), noteName));

                    if (distance > distanceWhenHelpLinesStart) {
                        count += distance - distanceWhenHelpLinesStart;
                    }
                }
            } else if (symbol instanceof BreakSymbol) {
                count++;
            }
        }

        return count;
    }
}