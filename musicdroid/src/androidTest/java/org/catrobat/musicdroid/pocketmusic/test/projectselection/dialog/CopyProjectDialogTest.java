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

import org.catrobat.musicdroid.pocketmusic.note.midi.MidiException;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;
import org.catrobat.musicdroid.pocketmusic.projectselection.dialog.CopyProjectDialog;
import org.catrobat.musicdroid.pocketmusic.test.note.ProjectTestDataFactory;

import java.io.IOException;

public class CopyProjectDialogTest extends AndroidTestCase {

    private String userInput;
    private CopyProjectDialogMock dialog;

    @Override
    protected void setUp() {
        userInput = "some input";
        Bundle args = new Bundle();
        args.putSerializable(CopyProjectDialog.ARGUMENT_PROJECT, ProjectTestDataFactory.createProject());
        dialog = new CopyProjectDialogMock();
        dialog.setArguments(args);
    }

    @Override
    protected void tearDown() throws IOException {
        ProjectToMidiConverter.getMidiFileFromProjectName(userInput).delete();
    }

    public void testOnNewProjectName() throws IOException, MidiException {
        dialog.onNewProjectName(userInput);

        assertTrue(ProjectToMidiConverter.getMidiFileFromProjectName(userInput).exists());
    }


}
