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
package org.catrobat.musicdroid.pocketmusic.instrument;

import android.app.Activity;

import org.catrobat.musicdroid.pocketmusic.instrument.tempo.AbstractTickThread;
import org.catrobat.musicdroid.pocketmusic.instrument.tempo.SimpleTickThread;
import org.catrobat.musicdroid.pocketmusic.note.MusicalInstrument;
import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.NoteEvent;
import org.catrobat.musicdroid.pocketmusic.note.Track;

public abstract class InstrumentActivity extends Activity {

    // TODO fw tests

    private AbstractTickThread tickThread;
    private Track track;

    public InstrumentActivity(MusicalKey key, MusicalInstrument instrument) {
        tickThread = new SimpleTickThread();
        track = new Track(key, instrument);
    }

    public Track getTrack() {
        return track;
    }

    public void clearTrack() {
        MusicalKey currentKey = track.getKey();
        MusicalInstrument currentInstrument = track.getInstrument();

        track = new Track(currentKey, currentInstrument);
    }

    public void addNoteEvent(NoteEvent noteEvent) {
        track.addNoteEvent(tickThread.getNextTick(noteEvent), noteEvent);
        doAfterAddNoteEvent(noteEvent);
    }

    protected abstract void doAfterAddNoteEvent(NoteEvent noteEvent);
}
