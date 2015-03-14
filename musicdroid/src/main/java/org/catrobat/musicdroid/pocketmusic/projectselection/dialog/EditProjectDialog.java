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

package org.catrobat.musicdroid.pocketmusic.projectselection.dialog;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiException;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;
import org.catrobat.musicdroid.pocketmusic.projectselection.ProjectSelectionActivity;

import java.io.IOException;

public class EditProjectDialog extends AbstractProjectNameDialog {

    public static final String ARGUMENT_PROJECT = "project";

    public EditProjectDialog() {
        super(R.string.dialog_edit_project, R.string.dialog_enter_name, R.string.edit_success, R.string.dialog_edit_error, R.string.edit_cancel);
    }

    @Override
    protected void initDialog() {
        setEditTextProjectName(((Project) getArguments().getSerializable(ARGUMENT_PROJECT)).getName());
    }

    @Override
    protected void onNewProjectName(String name) throws IOException, MidiException {
        Project selectedProject = (Project) getArguments().getSerializable(ARGUMENT_PROJECT);
        String oldProjectName = selectedProject.getName();
        selectedProject.setName(name);

        ProjectToMidiConverter converter = new ProjectToMidiConverter();
        converter.writeProjectAsMidi(selectedProject);
        converter.deleteMidiByName(oldProjectName);
    }

    @Override
    protected void updateActivity() {
        ((ProjectSelectionActivity) getActivity()).getProjectSelectionFragment().fetchProjectInformation();
    }
}
