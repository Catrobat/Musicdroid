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
import android.widget.Toast;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.instrument.InstrumentActivity;
import org.catrobat.musicdroid.pocketmusic.note.MusicalInstrument;
import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.NoteEvent;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;

public class PianoActivity extends InstrumentActivity {

    // TODO: fix orientation (NullPointerException on changing orientation)
    private PianoViewFragment pianoViewFragment;

    public PianoActivity() {
        super(MusicalKey.VIOLIN, MusicalInstrument.ACOUSTIC_GRAND_PIANO);
    }

    public PianoViewFragment getPianoViewFragment() {
        return pianoViewFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piano);

        if (savedInstanceState == null) {
            pianoViewFragment = new PianoViewFragment();
            getFragmentManager().beginTransaction().add(R.id.container, pianoViewFragment).commit();
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
        } else if (id == R.id.action_export_midi) {
            onActionSave();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void doAfterAddNoteEvent(NoteEvent noteEvent) {
    }

    private void onActionSave() {
        ProjectToMidiConverter converter = new ProjectToMidiConverter();
        Project project = new Project(Project.DEFAULT_BEATS_PER_MINUTE);

        project.addTrack(getTrack());

        try {
            // TODO change the static string Durp1.midi
            converter.convertProjectAndWriteMidi(project, "Durp1.midi");

            Toast.makeText(getBaseContext(), R.string.action_export_midi_success,
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), R.string.action_export_midi_error,
                    Toast.LENGTH_LONG).show();
        }
    }
}
