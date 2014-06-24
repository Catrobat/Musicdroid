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

package org.catrobat.musicdroid.pocketmusic;

import android.app.Fragment;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.catrobat.musicdroid.pocketmusic.note.NoteEvent;
import org.catrobat.musicdroid.pocketmusic.note.NoteName;
import org.catrobat.musicdroid.pocketmusic.note.Octave;
import org.catrobat.musicdroid.pocketmusic.note.draw.InstrumentActivity;
import org.catrobat.musicdroid.pocketmusic.note.draw.NoteSheetView;
import org.catrobat.musicdroid.pocketmusic.properties.PianoProperties;
import org.catrobat.musicdroid.pocketmusic.soundplayer.SoundPlayer;

import java.util.ArrayList;


public class PianoActivity extends InstrumentActivity {
    SoundPlayer soundPlayer;
    NoteSheetViewFragment noteSheetViewFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piano);
        noteSheetViewFragment = new NoteSheetViewFragment();

        LinearLayout fragContainer = (LinearLayout) findViewById(R.id.container);




        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container,noteSheetViewFragment).commit();
            getFragmentManager().beginTransaction().add(R.id.container, new PianoViewFragment()).commit();

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.piano, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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

      //  noteSheetViewFragment.redraw(getTrack());
    }


}
