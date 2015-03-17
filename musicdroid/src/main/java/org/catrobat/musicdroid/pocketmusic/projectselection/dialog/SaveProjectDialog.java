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
import org.catrobat.musicdroid.pocketmusic.instrument.InstrumentActivity;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.Track;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiException;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;
import org.catrobat.musicdroid.pocketmusic.note.symbol.SymbolContainer;
import org.catrobat.musicdroid.pocketmusic.note.symbol.SymbolContainerToTrackConverter;

import java.io.IOException;

public class SaveProjectDialog extends AbstractProjectNameDialog {

    public static final String ARGUMENT_SYMBOLS = "SavedSymbols";
    public static final String ARGUMENTS_BPM = "SavedBPM";

    private Project project;

    public SaveProjectDialog() {
        super(R.string.menu_save, R.string.dialog_enter_name, R.string.save_success, R.string.dialog_save_error, R.string.save_cancel);
        project = null;
    }

    @Override
    protected void initDialog() {
    }

    @Override
    protected void onNewProjectName(String name) throws IOException, MidiException {
        SymbolContainer symbolContainer = (SymbolContainer) getArguments().getSerializable(ARGUMENT_SYMBOLS);
        int beatsPerMinute = getArguments().getInt(ARGUMENTS_BPM);
        SymbolContainerToTrackConverter symbolsConverter = new SymbolContainerToTrackConverter();
        Track track = symbolsConverter.convertSymbols(symbolContainer, beatsPerMinute);

        project = new Project(name, beatsPerMinute);
        // TODO fw consider for more tracks
        project.addTrack("changeThisName", track);

        ProjectToMidiConverter projectConverter = new ProjectToMidiConverter();
        projectConverter.writeProjectAsMidi(project);
    }

    @Override
    protected void updateActivity() {
        InstrumentActivity instrumentActivity = (InstrumentActivity) getActivity();
        instrumentActivity.setProject(project);
    }
}
