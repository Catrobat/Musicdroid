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

package org.catrobat.musicdroid.pocketmusic.projectselection.io;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiException;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiToProjectConverter;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;
import org.catrobat.musicdroid.pocketmusic.projectselection.ProjectSelectionActivity;

import java.io.File;
import java.io.IOException;

public class ImportProjectHandler {

    private static final int PICKFILE_RESULT_CODE = 1;
    private ProjectSelectionActivity projectSelectionActivity;

    public ImportProjectHandler(ProjectSelectionActivity projectSelectionActivity) {
        this.projectSelectionActivity = projectSelectionActivity;
    }

    public void handleImportProject() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/midi");
        projectSelectionActivity.startActivityForResult(intent, PICKFILE_RESULT_CODE);
    }

    public void onChosenFile(int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode) {
                case PICKFILE_RESULT_CODE:
                    if (resultCode == Activity.RESULT_OK) {
                        File targetFile = new File(data.getData().getPath());
                        MidiToProjectConverter midiToProjectConverter = new MidiToProjectConverter();
                        ProjectToMidiConverter projectToMidiConverter = new ProjectToMidiConverter();

                        Project project = midiToProjectConverter.convertMidiFileToProject(targetFile);
                        projectToMidiConverter.writeProjectAsMidi(project);

                        Toast.makeText(projectSelectionActivity, projectSelectionActivity.getResources().getString(R.string.project_import_successful), Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        } catch (IOException | MidiException e) {
            Toast.makeText(projectSelectionActivity, projectSelectionActivity.getResources().getString(R.string.project_import_failed), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
