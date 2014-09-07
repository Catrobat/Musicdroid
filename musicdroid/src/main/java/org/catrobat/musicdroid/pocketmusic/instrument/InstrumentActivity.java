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
import android.content.DialogInterface;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.instrument.tempo.AbstractTickThread;
import org.catrobat.musicdroid.pocketmusic.instrument.tempo.SimpleTickThread;
import org.catrobat.musicdroid.pocketmusic.note.MusicalInstrument;
import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.NoteEvent;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.Track;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;

public abstract class InstrumentActivity extends Activity {

    private EditText editTextMidiExportNameDialogPrompt;

    private AbstractTickThread tickThread;
    private Track track;

    public InstrumentActivity(MusicalKey key, MusicalInstrument instrument) {
        editTextMidiExportNameDialogPrompt = null;

        tickThread = new SimpleTickThread();
        track = new Track(key, instrument);
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
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActionExportMidi() {
        promptUserForFilenameAndExportMidi();
    }

    private void promptUserForFilenameAndExportMidi() {
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
                                // TODO fw validate filename
                                String filename = editTextMidiExportNameDialogPrompt.getText().toString();

                                if ((filename != null) && (false == filename.equals(""))) {
                                    final ProjectToMidiConverter converter = new ProjectToMidiConverter();
                                    final Project project = new Project(Project.DEFAULT_BEATS_PER_MINUTE);
                                    project.addTrack(getTrack());

                                    try {
                                        converter.convertProjectAndWriteMidi(project, filename);

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
