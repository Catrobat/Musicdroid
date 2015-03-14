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
import org.catrobat.musicdroid.pocketmusic.projectselection.ProjectSelectionActivity;

public class ProjectSelectionEditContextMenu extends ProjectSelectionContextMenu {

    public ProjectSelectionEditContextMenu(ProjectSelectionActivity parentActivity) {
        parent = parentActivity;
    }

    @Override
    public void enterSingleEditMode() {
        runEditRoutine();
        actionMode.finish();
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            default:
                return false;
        }
    }

    @Override
    public void enterMultipleEditMode() {
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        super.onDestroyActionMode(mode);
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        parent.getMenuInflater().inflate(R.menu.menu_project_selection_edit_callback, menu);
        super.onCreateActionMode(mode, menu);
        mode.setTitle(R.string.select_edit);
        return true;
    }
}
