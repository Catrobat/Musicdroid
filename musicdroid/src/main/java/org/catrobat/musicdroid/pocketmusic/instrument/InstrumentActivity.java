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
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.instrument.piano.PianoActivity;
import org.catrobat.musicdroid.pocketmusic.instrument.tempo.AbstractTickThread;
import org.catrobat.musicdroid.pocketmusic.instrument.tempo.SimpleTickThread;
import org.catrobat.musicdroid.pocketmusic.note.MusicalInstrument;
import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.NoteEvent;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.Track;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiException;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiToProjectConverter;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOError;
import java.io.IOException;

public abstract class InstrumentActivity extends Activity {

    private EditText editTextMidiExportNameDialogPrompt;

    private AbstractTickThread tickThread;
    private Track track;
    private String[] midiFileList;

    public InstrumentActivity(MusicalKey key, MusicalInstrument instrument) {
        editTextMidiExportNameDialogPrompt = null;

        tickThread = new SimpleTickThread();
        track = new Track(key, instrument);

        midiFileList = null;
    }

    public Track getTrack() {
        return track;
    }

    public void addNoteEvent(NoteEvent noteEvent) {
        track.addNoteEvent(tickThread.getNextTick(noteEvent), noteEvent);
        doAfterAddNoteEvent(noteEvent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_export_midi) {
            onActionExportMidi();
            return true;
        } else if (id == R.id.action_import_midi) {
            onActionImportMidi();
            return true;
        } else if (id == R.id.action_delete_midi) {
            onActionDeleteMidi();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActionExportMidi() {
        exportMidiFileByUserInput();
    }

    protected void onActionImportMidi() {
        if (ProjectToMidiConverter.MIDI_FOLDER.exists()) {
            FilenameFilter filter = new FilenameFilter() {
                public boolean accept(File dir, String filename) {
                    File selectedFile = new File(dir, filename);
                    return filename.contains(ProjectToMidiConverter.MIDI_FILE_EXTENSION) || selectedFile.isDirectory();
                }
            };

            midiFileList = ProjectToMidiConverter.MIDI_FOLDER.list(filter);

            removeMidiExtension();

            importMidiFileByUserInput();
        }
    }

    private void onActionDeleteMidi() {
        track = new Track(track.getKey(), track.getInstrument());

        Toast.makeText(getBaseContext(), R.string.action_delete_midi_success, Toast.LENGTH_LONG).show();
    }

    private void removeMidiExtension() {
        for (int i = 0; i < midiFileList.length; i++) {
            midiFileList[i] = midiFileList[i].replaceFirst("[.][^.]+$", "");
        }
    }

    protected void importMidiFileByUserInput() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.action_import_midi_file_chooser_title);

        builder.setItems(midiFileList, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int index) {
                File midiFile = new File(ProjectToMidiConverter.MIDI_FOLDER, midiFileList[index] + ProjectToMidiConverter.MIDI_FILE_EXTENSION);

                final MidiToProjectConverter converter = new MidiToProjectConverter();

                try {
                    Project project = converter.convertMidiFileToProject(midiFile);

                    track = project.getTrack(0);

                    Toast.makeText(getBaseContext(), R.string.action_import_midi_success,
                            Toast.LENGTH_LONG).show();
                } catch (MidiException e) {
                    Toast.makeText(getBaseContext(), R.string.action_import_midi_validation_error,
                            Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(getBaseContext(), R.string.action_import_midi_io_error + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.show();
    }

    private void exportMidiFileByUserInput() {
        editTextMidiExportNameDialogPrompt = new EditText(this);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getString(R.string.action_export_dialog_title))
                .setMessage(getString(R.string.action_export_dialog_message))
                .setView(editTextMidiExportNameDialogPrompt)
                .setCancelable(false)
                .setPositiveButton(R.string.action_export_dialog_positive_button,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String userInput = editTextMidiExportNameDialogPrompt.getText().toString();

                                if ((userInput != null) && (false == userInput.equals(""))) {
                                    String filename = userInput.split(ProjectToMidiConverter.MIDI_FILE_EXTENSION)[0];

                                    final ProjectToMidiConverter converter = new ProjectToMidiConverter();
                                    final Project project = new Project(Project.DEFAULT_BEATS_PER_MINUTE);
                                    project.addTrack(getTrack());

                                    try {
                                        converter.writeProjectAsMidi(project, filename);

                                        Toast.makeText(getBaseContext(), R.string.action_export_midi_success,
                                                Toast.LENGTH_LONG).show();
                                    } catch (Exception e) {
                                        Toast.makeText(getBaseContext(), R.string.action_export_midi_error,
                                                Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(getBaseContext(), R.string.action_export_midi_cancel,
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                )
                .setNegativeButton(R.string.action_export_dialog_negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(), R.string.action_export_midi_cancel,
                                Toast.LENGTH_LONG).show();
                    }
                });

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public EditText getEditTextMidiExportNameDialogPrompt() {
        return editTextMidiExportNameDialogPrompt;
    }

    protected abstract void doAfterAddNoteEvent(NoteEvent noteEvent);
}
