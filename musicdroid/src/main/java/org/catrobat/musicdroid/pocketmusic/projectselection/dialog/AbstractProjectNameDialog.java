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
import org.catrobat.musicdroid.pocketmusic.error.ErrorDialog;
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
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        editTextProjectName = new EditText(getActivity());

        initDialog();

        return new AlertDialog.Builder(getActivity())
                .setTitle(titleId)
                .setMessage(messageId)
                .setView(editTextProjectName)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onPositiveButton();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onNegativeButton();
                    }
                })
                .create();
    }

    protected void setEditTextProjectName(String projectName) {
        editTextProjectName.setText(projectName);
    }

    protected abstract void initDialog();

    protected abstract void onNewProjectName(String name) throws IOException, MidiException;

    protected abstract void updateActivity();

    protected void onPositiveButton() {
        String input = getUserInput();

        if ((input != null) && (false == input.equals(""))) {
            String projectName = ProjectToMidiConverter.removeMidiExtensionFromString(input);

            try {
                if (projectNameExists(projectName)) {
                    makeToastText(R.string.dialog_name_error, Toast.LENGTH_LONG);
                } else {
                    onNewProjectName(projectName);
                    updateActivity();
                    makeToastText(successMessageId, Toast.LENGTH_LONG);
                }
            } catch (Exception e) {
                ErrorDialog.createDialog(errorMessageId, e).show(getFragmentManager(), "tag");
            }
        } else {
            makeToastText(cancelMessageId, Toast.LENGTH_LONG);
        }
    }

    protected String getUserInput() {
        return editTextProjectName.getText().toString();
    }

    protected boolean projectNameExists(String projectName) throws IOException {
        File file = ProjectToMidiConverter.getMidiFileFromProjectName(projectName);

        return file.exists();
    }

    protected void onNegativeButton() {
        makeToastText(cancelMessageId, Toast.LENGTH_LONG);
    }

    protected void makeToastText(int resId, int toastLength) {
        Toast.makeText(getActivity().getBaseContext(), resId, toastLength).show();
    }
}
