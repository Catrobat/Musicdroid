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

import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.NoteFlag;
import org.catrobat.musicdroid.pocketmusic.note.NoteLength;
import org.catrobat.musicdroid.pocketmusic.note.NoteName;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NoteSymbol extends Symbol {

    private Map<NoteName, NoteLength> notes;

    public NoteSymbol() {
        notes = new HashMap<>();
    }

    public NoteSymbol(NoteSymbol noteSymbol) {
        super(noteSymbol);

        notes = new HashMap<>();
        for (NoteName noteName : noteSymbol.notes.keySet()) {
            notes.put(noteName, noteSymbol.notes.get(noteName));
        }
    }

    public void addNote(NoteName noteName, NoteLength noteLength) {
        notes.put(noteName, noteLength);
    }

    public int size() {
        return notes.size();
    }

    public List<NoteName> getNoteNamesSorted() {
        List<NoteName> noteNames = new LinkedList<NoteName>(notes.keySet());
        Collections.sort(noteNames);
        return noteNames;
    }

    public NoteLength getNoteLength(NoteName noteName) {
        return notes.get(noteName);
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || !(obj instanceof NoteSymbol)) {
            return false;
        }

        if (false == super.equals(obj)) {
            return false;
        }

        NoteSymbol noteSymbol = (NoteSymbol) obj;

        if (notes.equals(noteSymbol.notes)) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "[NoteSymbol] marked=" + marked + " size=" + size();
    }

    public boolean isStemUp(MusicalKey key) {
        List<NoteName> noteNames = getNoteNamesSorted();

        NoteName firstNoteName = noteNames.get(0);
        NoteName lastNoteName = noteNames.get(noteNames.size() - 1);

        int distanceFirst = NoteName.calculateDistanceToMiddleLineCountingSignedNotesOnly(key, firstNoteName);
        int distanceLast = NoteName.calculateDistanceToMiddleLineCountingSignedNotesOnly(key, lastNoteName);
        int distance = 0;

        if (Math.abs(distanceFirst) > Math.abs(distanceLast)) {
            distance = distanceFirst;
        } else {
            distance = distanceLast;
        }

        return distance > 0;
    }

    public boolean hasStem() {
        for (NoteLength noteLength : notes.values()) {
            if (noteLength.hasStem()) {
                return true;
            }
        }

        return false;
    }

    public NoteFlag getFlag() {
        for (NoteLength noteLength : notes.values()) {
            NoteFlag noteFlag = noteLength.getFlag();

            if ((NoteFlag.DOUBLE_FLAG == noteFlag) || (NoteFlag.SINGLE_FLAG == noteFlag)) {
                return noteFlag;
            }
        }

        return NoteFlag.NO_FLAG;
    }
}
