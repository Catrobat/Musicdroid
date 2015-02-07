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

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiException;
import org.catrobat.musicdroid.pocketmusic.projectselection.ProjectListViewAdapter;

import java.io.IOException;

public final class EditProjectDialog extends Dialog {

    private EditText renameProjectEditText;
    private Button okButton;
    private Button cancelButton;

    private Project project;
    private ProjectListViewAdapter adapter;

    public EditProjectDialog(Context context, Project project, ProjectListViewAdapter adapter) {
        super(context);

        this.setContentView(R.layout.dialog_project_edit);

        this.project = project;
        this.adapter = adapter;

        findViews();
        initDialog();
        setClickListeners();
    }

    private void findViews() {
        okButton = (Button) this.findViewById(R.id.dialog_project_edit_ok_button);
        cancelButton = (Button) this.findViewById(R.id.dialog_project_edit_cancel_button);
        renameProjectEditText = (EditText) this.findViewById(R.id.dialog_project_edit_rename_field);
    }

    private void initDialog() {
        this.setTitle(getContext().getResources().getString(R.string.edit_project));
        renameProjectEditText.setText(project.getName());
    }

    private void setClickListeners() {
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //TODO just rename now
                    if (adapter.renameItem(project.getName(), renameProjectEditText.getText().toString()))
                        Toast.makeText(getContext(), getContext().getString(R.string.edit_successful), Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getContext(), getContext().getString(R.string.edit_unsuccessful), Toast.LENGTH_LONG).show();
                } catch (IOException | MidiException e) {
                    e.printStackTrace();
                }
                dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
