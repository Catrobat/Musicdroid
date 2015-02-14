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
import android.widget.HorizontalScrollView;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.instrument.edit.menu.EditModeContextMenu;
import org.catrobat.musicdroid.pocketmusic.instrument.InstrumentActivity;
import org.catrobat.musicdroid.pocketmusic.instrument.noteSheet.NoteSheetViewFragment;
import org.catrobat.musicdroid.pocketmusic.note.MusicalInstrument;
import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiException;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiToProjectConverter;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;
import org.catrobat.musicdroid.pocketmusic.projectselection.ProjectSelectionActivity;

import java.io.File;
import java.io.IOException;

public class PianoActivity extends InstrumentActivity {

    public static boolean inCallback = false;

    private PianoViewFragment pianoViewFragment;
    private NoteSheetViewFragment noteSheetViewFragment;

    private EditModeContextMenu editModeContextMenu;

    public PianoActivity() {
        super(MusicalKey.VIOLIN, MusicalInstrument.ACOUSTIC_GRAND_PIANO);
    }

    public PianoViewFragment getPianoViewFragment() {
        return pianoViewFragment;
    }

    public String getTrackSizeString() {
        return noteSheetViewFragment.getTrackSizeTextViewText();
    }

    public void startEditMode(){
        editModeContextMenu = new EditModeContextMenu(this);
        startActionMode(editModeContextMenu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piano);

        handleExtras();

        noteSheetViewFragment = new NoteSheetViewFragment();
        pianoViewFragment = new PianoViewFragment();

        if (savedInstanceState != null) {
            getFragmentManager().beginTransaction().replace(R.id.container, noteSheetViewFragment).commit();
            getFragmentManager().beginTransaction().replace(R.id.container, pianoViewFragment).commit();
        } else {
            getFragmentManager().beginTransaction().add(R.id.container, noteSheetViewFragment).commit();
            getFragmentManager().beginTransaction().add(R.id.container, pianoViewFragment).commit();
        }
    }

    private void handleExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            MidiToProjectConverter converter = new MidiToProjectConverter();
            File midiFile = new File(ProjectToMidiConverter.MIDI_FOLDER,
                                extras.getString(ProjectSelectionActivity.INTENT_EXTRA_FILE_NAME) +
                                ProjectToMidiConverter.MIDI_FILE_EXTENSION);
            setTitle(extras.getString(ProjectSelectionActivity.INTENT_EXTRA_FILE_NAME));
            try {
                Project project = converter.convertMidiFileToProject(midiFile);
                //TODO: consider more tracks
                setTrack(project.getTrack(0));
            } catch (MidiException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void redraw() {
        noteSheetViewFragment.redraw(getTrack());
    }

    @Override
    protected void onStart() {
        super.onStart();
        noteSheetViewFragment.redraw(getTrack());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.piano, menu);
        return true;
    }

    public void scrollNoteSheet() {
        if (noteSheetViewFragment.checkForScrollAndRecalculateWidth()) {
            HorizontalScrollView hv = (HorizontalScrollView) findViewById(R.id.scroll_note_sheet_view);
            hv.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
        }
    }

    public void resetSymbolMarkers() {
        noteSheetViewFragment.resetSymbolMarkers();
    }
}
