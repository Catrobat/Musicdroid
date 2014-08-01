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

package org.catrobat.musicdroid.pocketmusic.instrument;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;

public class MidiExportHelper {

    private EditText editText;
    private InstrumentActivity activity;

    public MidiExportHelper(InstrumentActivity activity) {
        this.editText = new EditText(activity);
        this.activity = activity;
    }

    public void promptUserForFilename() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle(activity.getString(R.string.action_export_dialog_title))
                .setMessage(activity.getString(R.string.action_export_dialog_message))
                .setView(editText)
                .setCancelable(false)
                .setPositiveButton(R.string.action_export_dialog_positive_button,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO fw validate filename
                                String filename = editText.getText().toString();

                                if (filename != null) {
                                    final ProjectToMidiConverter converter = new ProjectToMidiConverter();
                                    final Project project = new Project(Project.DEFAULT_BEATS_PER_MINUTE);
                                    project.addTrack(activity.getTrack());

                                    try {
                                        converter.convertProjectAndWriteMidi(project, filename);

                                        Toast.makeText(activity.getBaseContext(), R.string.action_export_midi_success,
                                                Toast.LENGTH_LONG).show();
                                    } catch (Exception e) {
                                        Toast.makeText(activity.getBaseContext(), R.string.action_export_midi_error,
                                                Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(activity.getBaseContext(), R.string.action_export_midi_cancel,
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                )
                .setNegativeButton(R.string.action_export_dialog_negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(activity.getBaseContext(), R.string.action_export_midi_cancel,
                                Toast.LENGTH_LONG).show();
                    }
                });

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
