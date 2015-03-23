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

package org.catrobat.musicdroid.pocketmusic.instrument.edit.menu;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.instrument.piano.PianoActivity;
import org.catrobat.musicdroid.pocketmusic.note.symbol.SymbolContainer;

public class EditModeContextMenu implements ActionMode.Callback {

    private PianoActivity parent;
    private ActionMode actionMode;

    public EditModeContextMenu(PianoActivity parent) {
        this.parent = parent;
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        this.actionMode = actionMode;
        parent.getMenuInflater().inflate(R.menu.menu_project_edit_callback, menu);

        PianoActivity.inCallback = true;

        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.edit_callback_action_delete_project:
                onActionDelete();
                actionMode.finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        PianoActivity.inCallback = false;
        parent.getSymbolContainer().resetSymbolMarkers();
        parent.getNoteSheetView().invalidate();
    }

    public void checkedItemStateChanged() {
        actionMode.setTitle("" + parent.getSymbolContainer().getMarkedSymbolCount());
    }

    private void onActionDelete() {
        parent.deleteMarkedSymbols();
        parent.getNoteSheetViewFragment().setSymbolCountText(parent.getSymbolContainer().size());
        parent.getNoteSheetView().invalidate();
    }
}
