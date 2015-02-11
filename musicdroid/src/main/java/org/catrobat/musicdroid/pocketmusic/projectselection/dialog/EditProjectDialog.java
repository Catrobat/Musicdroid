/*
 * Musicdroid: An on-device music generator for Android
 * Copyright (C) 2010-2015 The Catrobat Team
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

package org.catrobat.musicdroid.pocketmusic.projectselection.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiException;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;
import org.catrobat.musicdroid.pocketmusic.projectselection.ProjectSelectionActivity;

import java.io.File;
import java.io.IOException;

public class EditProjectDialog extends DialogFragment {

    private EditText renameField;
    private Project projectToEdit;

    public EditProjectDialog() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        projectToEdit = (Project) getArguments().getSerializable("project");

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_project_edit, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle(R.string.dialog_project_edit_title);
        dialogBuilder.setCancelable(false);

        renameField = (EditText) dialogView.findViewById(R.id.dialog_project_edit_rename_field);
        renameField.setText(projectToEdit.getName());

        dialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String input = renameField.getText().toString();

                if ((input != null) && (false == input.equals(""))) {
                    try {
                        File file = ProjectToMidiConverter.getMidiFileFromProjectName(input);

                        if (file.exists()) {
                            Toast.makeText(getActivity().getBaseContext(), R.string.dialog_project_name_exists_error, Toast.LENGTH_LONG).show();
                        } else {
                            onProjectEdit();

                            Toast.makeText(getActivity().getBaseContext(), R.string.edit_successful,
                                    Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity().getBaseContext(), R.string.edit_unsuccessful,
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity().getBaseContext(), R.string.edit_canceled,
                            Toast.LENGTH_LONG).show();
                }
            }
        })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity().getBaseContext(), R.string.edit_canceled,
                                Toast.LENGTH_LONG).show();
                    }
                });

        return dialogBuilder.create();
    }

    private void onProjectEdit() throws IOException, MidiException {
        ProjectSelectionActivity projectSelectionActivity = (ProjectSelectionActivity) getActivity();
        projectSelectionActivity.getProjectSelectionFragment().getListViewAdapter().renameItem(projectToEdit.getName(), renameField.getText().toString());
    }
}
