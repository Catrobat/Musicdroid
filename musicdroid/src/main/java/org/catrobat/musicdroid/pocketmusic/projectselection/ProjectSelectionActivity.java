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

package org.catrobat.musicdroid.pocketmusic.projectselection;

import android.app.Activity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiPlayer;
import org.catrobat.musicdroid.pocketmusic.projectselection.menu.ProjectSelectionContextMenu;
import org.catrobat.musicdroid.pocketmusic.projectselection.menu.ProjectSelectionDeleteContextMenu;
import org.catrobat.musicdroid.pocketmusic.projectselection.menu.ProjectSelectionEditContextMenu;
import org.catrobat.musicdroid.pocketmusic.projectselection.menu.ProjectSelectionTapAndHoldContextMenu;

public class ProjectSelectionActivity extends Activity {
    private ProjectSelectionFragment projectSelectionFragment;
    public static boolean inCallback = false;
    private ProjectSelectionContextMenu projectSelectionContextMenu;

    private ActionMode actionMode;

    public static final String INTENT_EXTRA_FILE_NAME = "fileName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_selection);
        projectSelectionFragment = new ProjectSelectionFragment();

        if (savedInstanceState != null) {
            getFragmentManager().beginTransaction().replace(R.id.container, projectSelectionFragment).commit();
        } else {
            getFragmentManager().beginTransaction().add(R.id.container, projectSelectionFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_project_selection, menu);
        return true;
    }

    public void startTapAndHoldActionMode(){
        projectSelectionContextMenu = new ProjectSelectionTapAndHoldContextMenu(this);
        actionMode = startActionMode(getProjectSelectionContextMenu());
    }

    private void startDeleteActionMode(){
        projectSelectionContextMenu = new ProjectSelectionDeleteContextMenu(this);
        actionMode = startActionMode(getProjectSelectionContextMenu());
    }

    private void startEditActionMode(){
        projectSelectionContextMenu = new ProjectSelectionEditContextMenu(this);
        actionMode = startActionMode(getProjectSelectionContextMenu());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit_project ) {
            stopPlayingTracks();
            startEditActionMode();
            return true;
        }

        if (id == R.id.action_refresh_project ) {
            stopPlayingTracks();
            projectSelectionFragment = new ProjectSelectionFragment();
            getFragmentManager().beginTransaction().replace(R.id.container, projectSelectionFragment).commit();
            return true;
        }

        if (id == R.id.action_delete_project ) {
            stopPlayingTracks();
            startDeleteActionMode();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void stopPlayingTracks(){
        MidiPlayer.getInstance().stop();
        notifyTrackPlayed();
    }

    public void notifyTrackPlayed(){
        projectSelectionFragment.getListViewAdapter().changePlayPauseButtonState();
    }

    public ProjectSelectionFragment getProjectSelectionFragment(){
        return projectSelectionFragment;
    }

    @Override
    public void onPause() {
        super.onPause();
        stopPlayingTracks();
    }

    public void notifyNumberOfItemsSelected(int numberOfItems){
        if(numberOfItems == 0)
            actionMode.finish();
        else if(numberOfItems == 1)
            getProjectSelectionContextMenu().enterSingleEditMode();
        else
            getProjectSelectionContextMenu().enterMultipleEditMode();
    }

    public void notifyCheckedItemStateChanged() {
        getProjectSelectionContextMenu().checkedItemStateChanged();
    }

    public ProjectSelectionContextMenu getProjectSelectionContextMenu() {
        return projectSelectionContextMenu;
    }
}
