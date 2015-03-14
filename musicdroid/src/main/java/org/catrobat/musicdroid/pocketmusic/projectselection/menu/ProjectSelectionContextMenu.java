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

package org.catrobat.musicdroid.pocketmusic.projectselection.menu;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.widget.Toast;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.error.ErrorDialog;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiException;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;
import org.catrobat.musicdroid.pocketmusic.projectselection.ProjectListViewAdapter;
import org.catrobat.musicdroid.pocketmusic.projectselection.ProjectSelectionActivity;
import org.catrobat.musicdroid.pocketmusic.projectselection.dialog.EditProjectDialog;

import java.io.File;
import java.io.IOException;

public abstract class ProjectSelectionContextMenu implements ActionMode.Callback {
    protected ProjectListViewAdapter adapter;
    protected ProjectSelectionActivity parent;
    protected ActionMode actionMode;

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        this.actionMode = actionMode;
        ProjectSelectionActivity.inCallback = true;
        adapter = parent.getProjectSelectionFragment().getListViewAdapter();
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        adapter.clearProjectSelectionBackgroundStatus();
        ProjectSelectionActivity.inCallback = false;
        adapter.notifyDataSetChanged();
    }

    public abstract void enterSingleEditMode();

    public abstract void enterMultipleEditMode();

    public void runDeleteRoutine() {
        for (int i = 0; i < adapter.getCount(); i++)
            if (adapter.getProjectSelectionBackgroundFlags(i)) {
                String projectName = adapter.getItem(i).getName();
                if (adapter.deleteItemByProjectName(projectName)) {
                    Toast.makeText(parent, parent.getString(R.string.delete_success), Toast.LENGTH_LONG).show();
                    i--;
                } else {
                    ErrorDialog.createDialog(R.string.dialog_delete_error, null).show(parent.getFragmentManager(), "tag");
                }
            }
    }

    public void runEditRoutine() {
        Bundle args = new Bundle();
        args.putSerializable(EditProjectDialog.ARGUMENT_PROJECT, adapter.getSelectedProject());
        EditProjectDialog editProjectDialog = new EditProjectDialog();
        editProjectDialog.setArguments(args);
        editProjectDialog.show(parent.getFragmentManager(), "tag");
    }

    public void runShareRoutine() throws IOException, MidiException {
        File file = ProjectToMidiConverter.getMidiFileFromProjectName(adapter.getSelectedProject().getName());
        parent.notifyShareProject(file);
    }

    public void checkedItemStateChanged() {
        if (adapter.getSelectedItemsCount() != 0)
            getActionMode().setTitle(adapter.getSelectedItemsCount() + "");
    }

    public ActionMode getActionMode() {
        return actionMode;
    }
}
