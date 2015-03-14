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

import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiException;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiToProjectConverter;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;
import org.catrobat.musicdroid.pocketmusic.projectselection.ProjectSelectionActivity;

import java.io.File;
import java.io.IOException;

public class ImportProjectHandler extends IOHandler {

    public static final int IMPORT_RESULT_CODE = 1;

    public ImportProjectHandler(ProjectSelectionActivity projectSelectionActivity) {
        super(projectSelectionActivity);
    }

    @Override
    public void onSend(File file) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/midi");
        startIntent(intent, IMPORT_RESULT_CODE);
    }

    @Override
    public void onReceive(int requestCode, int resultCode, File targetFile) throws IOException, MidiException {
        switch (requestCode) {
            case IMPORT_RESULT_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    MidiToProjectConverter midiToProjectConverter = new MidiToProjectConverter();
                    ProjectToMidiConverter projectToMidiConverter = new ProjectToMidiConverter();

                    Project project = midiToProjectConverter.convertMidiFileToProject(targetFile);

                    if ((ProjectToMidiConverter.getMidiFileFromProjectName(project.getName()).exists())) {
                        throw new IOException();
                    }

                    projectToMidiConverter.writeProjectAsMidi(project);
                }
                break;
        }
    }
}
