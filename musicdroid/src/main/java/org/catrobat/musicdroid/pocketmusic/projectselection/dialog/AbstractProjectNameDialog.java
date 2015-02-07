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
import android.widget.EditText;
import android.widget.Toast;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiException;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;

import java.io.File;
import java.io.IOException;

public abstract class AbstractProjectNameDialog extends DialogFragment {

    private EditText editTextProjectName;
    private int titleId;
    private int messageId;
    private int successMessageId;
    private int errorMessageId;
    private int cancelMessageId;

    public AbstractProjectNameDialog(int titleId, int messageId, int successMessageId, int errorMessageId, int cancelMessageId) {
        this.titleId = titleId;
        this.messageId = messageId;
        this.successMessageId = successMessageId;
        this.errorMessageId = errorMessageId;
        this.cancelMessageId = cancelMessageId;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        editTextProjectName = new EditText(getActivity());
        return new AlertDialog.Builder(getActivity())
                .setTitle(titleId)
                .setMessage(messageId)
                .setView(editTextProjectName)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String input = editTextProjectName.getText().toString();

                        if ((input != null) && (false == input.equals(""))) {
                            String projectName = ProjectToMidiConverter.removeMidiExtensionFromString(input);

                            try {
                                File file = ProjectToMidiConverter.getMidiFileFromProjectName(projectName);

                                if (file.exists()) {
                                    Toast.makeText(getActivity().getBaseContext(), R.string.dialog_project_name_exists_error, Toast.LENGTH_LONG).show();
                                } else {
                                    onExistingProjectName(projectName);

                                    Toast.makeText(getActivity().getBaseContext(), successMessageId,
                                            Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(getActivity().getBaseContext(), errorMessageId,
                                        Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getActivity().getBaseContext(), cancelMessageId,
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity().getBaseContext(), cancelMessageId,
                                Toast.LENGTH_LONG).show();
                    }
                })
                .create();
    }

    protected abstract void onExistingProjectName(String name) throws IOException, MidiException;
}
