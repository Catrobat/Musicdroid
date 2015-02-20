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

package org.catrobat.musicdroid.pocketmusic.test.projectselection.dialog;

import android.os.Bundle;
import android.test.AndroidTestCase;

import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiException;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;
import org.catrobat.musicdroid.pocketmusic.projectselection.dialog.EditProjectDialog;
import org.catrobat.musicdroid.pocketmusic.test.note.ProjectTestDataFactory;

import java.io.IOException;

public class EditProjectDialogTest extends AndroidTestCase {

    private String existingProjectName = "Franz";
    private String newProjectName = "Hans";
    private EditProjectDialogMock dialog;

    @Override
    protected void setUp() throws IOException, MidiException {
        Project project = ProjectTestDataFactory.createProject(existingProjectName);
        ProjectToMidiConverter converter = new ProjectToMidiConverter();
        converter.writeProjectAsMidi(project);

        assertTrue(ProjectToMidiConverter.getMidiFileFromProjectName(project.getName()).exists());

        Bundle args = new Bundle();
        args.putSerializable(EditProjectDialog.ARGUMENT_PROJECT, project);
        dialog = new EditProjectDialogMock();
        dialog.setArguments(args);
        dialog.initDialog();
    }

    @Override
    protected void tearDown() throws IOException {
        ProjectToMidiConverter.getMidiFileFromProjectName(existingProjectName).delete();
        ProjectToMidiConverter.getMidiFileFromProjectName(newProjectName).delete();
    }

    public void testInitDialog() {
        assertEquals(existingProjectName, dialog.getEditTextProjectName());
    }

    public void testOnNewProjectName() throws IOException, MidiException {
        dialog.onNewProjectName(newProjectName);

        assertFalse(ProjectToMidiConverter.getMidiFileFromProjectName(existingProjectName).exists());
        assertTrue(ProjectToMidiConverter.getMidiFileFromProjectName(newProjectName).exists());
    }
}
