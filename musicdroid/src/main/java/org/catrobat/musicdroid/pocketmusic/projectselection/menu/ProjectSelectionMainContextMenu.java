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

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.projectselection.ProjectSelectionActivity;
import org.catrobat.musicdroid.pocketmusic.projectselection.dialog.EditProjectDialog;

public class ProjectSelectionMainContextMenu extends ProjectSelectionContextMenu {
    private MenuItem editItem;

    public ProjectSelectionMainContextMenu(ProjectSelectionActivity parentActivity) {
        parent = parentActivity;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.callback_action_delete_project:
                runDeleteRoutine();
                mode.finish();
                return true;
            case R.id.callback_action_edit_project:
                EditProjectDialog editProjectDialog = new EditProjectDialog(parent);
                editProjectDialog.show();
                mode.finish();
                return true;
            default:
                return false;
        }
    }

    public void enterSingleEditMode() {
        editItem.setVisible(true);
    }

    public void enterMultipleEditMode() {
        editItem.setVisible(false);
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        super.onDestroyActionMode(actionMode);
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        parent.getMenuInflater().inflate(R.menu.menu_project_selection_main_callback, menu);
        super.onCreateActionMode(mode,menu);
        editItem = menu.findItem(R.id.callback_action_edit_project);
        return true;
    }

}

