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

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.error.ErrorDialog;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiException;
import org.catrobat.musicdroid.pocketmusic.projectselection.ProjectSelectionActivity;

import java.io.IOException;

public class ProjectSelectionShareContextMenu extends ProjectSelectionContextMenu {

    public ProjectSelectionShareContextMenu(ProjectSelectionActivity parentActivity) {
        parent = parentActivity;
    }

    @Override
    public void enterSingleEditMode() {
        try {
            runShareRoutine();
        } catch (IOException | MidiException e) {
            ErrorDialog.createDialog(R.string.dialog_share_error, e).show(parent.getFragmentManager(), "tag");
        }
        actionMode.finish();
    }

    @Override
    public void enterMultipleEditMode() {
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        return false;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        parent.getMenuInflater().inflate(R.menu.menu_project_selection_share_callback, menu);
        super.onCreateActionMode(mode, menu);
        mode.setTitle(R.string.select_share);
        return true;
    }
}
