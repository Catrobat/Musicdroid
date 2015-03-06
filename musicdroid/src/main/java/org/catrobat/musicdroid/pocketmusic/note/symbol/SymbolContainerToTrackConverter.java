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

package org.catrobat.musicdroid.pocketmusic.note.symbol;

import org.catrobat.musicdroid.pocketmusic.note.NoteEvent;
import org.catrobat.musicdroid.pocketmusic.note.NoteLength;
import org.catrobat.musicdroid.pocketmusic.note.NoteName;
import org.catrobat.musicdroid.pocketmusic.note.Track;

public class SymbolContainerToTrackConverter {

    public Track convertSymbols(SymbolContainer symbolContainer, int beatsPerMinute) {
        Track track = new Track(symbolContainer.getKey(), symbolContainer.getInstrument());
        long tick = 0;

        for (int i = 0; i < symbolContainer.size(); i++) {
            Symbol symbol = symbolContainer.get(i);

            if (symbol instanceof NoteSymbol) {
                NoteSymbol noteSymbol = (NoteSymbol) symbol;
                long currentMaxTickOffset = 0;

                for (NoteName noteName : noteSymbol.getNoteNamesSorted()) {
                    NoteLength noteLength = noteSymbol.getNoteLength(noteName);
                    long tickOffset = noteLength.toTicks(beatsPerMinute);

                    track.addNoteEvent(tick, new NoteEvent(noteName, true));
                    track.addNoteEvent(tick + tickOffset, new NoteEvent(noteName, false));

                    if (tickOffset > currentMaxTickOffset) {
                        currentMaxTickOffset = tickOffset;
                    }
                }

                tick += currentMaxTickOffset;
            } else if (symbol instanceof BreakSymbol) {
                long difference = ((BreakSymbol) symbol).getNoteLength().toTicks(beatsPerMinute);

                track.increaseLastTick(difference);

                tick += difference;
            } else {
                throw new IllegalArgumentException("Illegal symbol: " + symbol);
            }
        }

        return track;
    }
}
