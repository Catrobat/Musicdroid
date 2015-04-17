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

import org.catrobat.musicdroid.pocketmusic.note.NoteLength;

public class BreakSymbol extends Symbol {

    private NoteLength noteLength;

    public BreakSymbol(NoteLength noteLength) {
        this.noteLength = noteLength;
    }

    public BreakSymbol(BreakSymbol breakSymbol) {
        super(breakSymbol);

        noteLength = breakSymbol.getNoteLength();
    }

    public NoteLength getNoteLength() {
        return noteLength;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || !(obj instanceof BreakSymbol)) {
            return false;
        }

        if (false == super.equals(obj)) {
            return false;
        }

        BreakSymbol breakSymbol = (BreakSymbol) obj;

        if (noteLength.equals(breakSymbol.getNoteLength())) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "[BreakSymbol] marked=" + marked + " noteLength=" + noteLength;
    }
}
