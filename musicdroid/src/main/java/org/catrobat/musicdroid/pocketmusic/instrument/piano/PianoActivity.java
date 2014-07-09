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

package org.catrobat.musicdroid.pocketmusic.instrument.piano;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.instrument.InstrumentActivity;
import org.catrobat.musicdroid.pocketmusic.note.NoteEvent;
import org.catrobat.musicdroid.pocketmusic.note.draw.NoteSheetViewFragment;
import org.catrobat.musicdroid.pocketmusic.soundplayer.SoundPlayer;


public class PianoActivity extends InstrumentActivity {
    private SoundPlayer soundPlayer;
    // TODO: fix orientation (NullPointerException on changing orientation)
    private NoteSheetViewFragment noteSheetViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piano);

        if (savedInstanceState == null) {
            noteSheetViewFragment = new NoteSheetViewFragment();
            getFragmentManager().beginTransaction().add(R.id.container, noteSheetViewFragment).commit();
            getFragmentManager().beginTransaction().add(R.id.container, new PianoViewFragment()).commit();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.piano, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public SoundPlayer getSoundPlayer() {
        return this.soundPlayer;
    }

    @Override
    protected void doAfterAddNoteEvent(NoteEvent noteEvent) {
        noteSheetViewFragment.redraw(getTrack());
    }

}