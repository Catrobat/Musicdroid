/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://developer.catrobat.org/license_additional_term
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.musicdroid.pocketmusic.note.symbol;

import org.catrobat.musicdroid.pocketmusic.note.NoteEvent;
import org.catrobat.musicdroid.pocketmusic.note.NoteLength;
import org.catrobat.musicdroid.pocketmusic.note.NoteName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NoteEventsToSymbolsConverter implements Serializable {

    private static final long serialVersionUID = 15687589879927955L;

    private long lastTick;
    private Map<NoteName, Long> openNotes;
    private Symbol currentSymbol;

    public NoteEventsToSymbolsConverter() {
        lastTick = 0;
        openNotes = new HashMap<>();
        currentSymbol = null;
    }

    public List<Symbol> convertNoteEventList(long tick, List<NoteEvent> noteEvents, int beatsPerMinute) {
        List<Symbol> symbols = new LinkedList<>();

        for (NoteEvent noteEvent : noteEvents) {
            symbols.addAll(convertNoteEvent(tick, noteEvent, beatsPerMinute));
        }

        return symbols;
    }

    public List<Symbol> convertNoteEvent(long tick, NoteEvent noteEvent, int beatsPerMinute) {
        List<Symbol> symbols = new LinkedList<>();

        NoteName noteName = noteEvent.getNoteName();

        if (noteEvent.isNoteOn()) {
            if (lastTick != tick) {
                long difference = tick - lastTick;

                do {
                    NoteLength noteLength = NoteLength.getNoteLengthFromTickDuration(difference, beatsPerMinute);
                    symbols.add(new BreakSymbol(noteLength));
                    difference = difference - noteLength.toTicks(beatsPerMinute);
                } while (difference > 0);
            }

            if (openNotes.isEmpty()) {
                currentSymbol = new NoteSymbol();
            }

            openNotes.put(noteName, tick);
        } else {
            long lastTickForNote = openNotes.get(noteName);
            NoteLength noteLength = NoteLength.getNoteLengthFromTickDuration(tick - lastTickForNote, beatsPerMinute);

            NoteSymbol noteSymbol = (NoteSymbol) currentSymbol;
            noteSymbol.addNote(noteName, noteLength);

            openNotes.remove(noteName);
            lastTick = tick;

            if (openNotes.isEmpty()) {
                symbols.add(noteSymbol);
            }
        }

        return symbols;
    }
}
