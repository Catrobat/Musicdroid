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

package org.catrobat.musicdroid.pocketmusic.projectselection.menu;

import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;
import org.catrobat.musicdroid.pocketmusic.projectselection.ProjectListViewAdapter;
import org.catrobat.musicdroid.pocketmusic.projectselection.ProjectSelectionActivity;

import java.io.File;

public class DeleteMenuCallback implements ActionMode.Callback {
    private ProjectSelectionActivity parent;
    private ProjectListViewAdapter adapter;

    public DeleteMenuCallback(ProjectSelectionActivity parentActivity) {
        parent = parentActivity;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {


        for (int currentProjectIndex = 0; currentProjectIndex < adapter.getCount(); currentProjectIndex++) {
            if (adapter.getProjectSelectionCheckBoxStatus(currentProjectIndex)) {
                String projectName = adapter.getItemName(currentProjectIndex);
                File file = new File(ProjectToMidiConverter.MIDI_FOLDER, projectName + ProjectToMidiConverter.MIDI_FILE_EXTENSION);
                boolean deleted = file.delete();
                adapter.deleteItemByProjectName(projectName);
                currentProjectIndex--;

            }
        }
        adapter.clearProjectSelectionCheckBoxStatus();
        adapter.setDelMode(false);

    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        parent.getMenuInflater().inflate(R.menu.menu_project_selection_delete_callback, menu);
        mode.setTitle(R.string.project_selection_delete_menu_title);
        adapter = parent.getProjectSelectionFragment().getListViewAdapter();
        adapter.setDelMode(true);
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return true;
    }

}

