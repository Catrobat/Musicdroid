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

package org.catrobat.musicdroid.pocketmusic.uitest.projectselection;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiException;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;
import org.catrobat.musicdroid.pocketmusic.projectselection.ProjectSelectionActivity;
import org.catrobat.musicdroid.pocketmusic.test.note.ProjectTestDataFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ProjectSelectionActivityUiTest extends ActivityInstrumentationTestCase2<ProjectSelectionActivity> {

    private static final int NUMBER_OF_SAMPLE_PROJECTS = 10;

    private Solo solo;
    private static final String FILE_NAME = "TestProject";
    private ArrayList<File> currentProjects;
    private ArrayList<File> expectedProjects;


    public ProjectSelectionActivityUiTest() {
        super(ProjectSelectionActivity.class);
    }

    @Override
    protected void setUp() {
        solo = new Solo(getInstrumentation(), getActivity());
        currentProjects = new ArrayList<>();
        expectedProjects = new ArrayList<>();

        if (ProjectToMidiConverter.MIDI_FOLDER.isDirectory()) {
            for (File file : ProjectToMidiConverter.MIDI_FOLDER.listFiles())
                file.delete();
        }
    }

    @Override
    protected void tearDown() {
        currentProjects.clear();
        if (ProjectToMidiConverter.MIDI_FOLDER.isDirectory()) {
            for (File file : ProjectToMidiConverter.MIDI_FOLDER.listFiles())
                file.delete();
        }
        solo.finishOpenedActivities();
    }

    private void createSampleProjects(int amount) throws IOException, MidiException {
        for (int i = 0; i < amount; i++) {
            File file = new File(ProjectToMidiConverter.MIDI_FOLDER + File.separator, FILE_NAME + i + ProjectToMidiConverter.MIDI_FILE_EXTENSION);
            Project project = ProjectTestDataFactory.createProjectWithOneSimpleTrack();
            ProjectToMidiConverter converter = new ProjectToMidiConverter();
            converter.writeProjectAsMidi(project, file);
            expectedProjects.add(file);
        }
    }

    private void updateCurrentProjects() {
        if (ProjectToMidiConverter.MIDI_FOLDER.isDirectory()) {
            Collections.addAll(currentProjects, ProjectToMidiConverter.MIDI_FOLDER.listFiles());
        }
    }

    public void testContextMenuDelete() throws IOException, MidiException {

        int[] projectIndicesToDelete = {0, NUMBER_OF_SAMPLE_PROJECTS / 2, NUMBER_OF_SAMPLE_PROJECTS - 1};
        baseDeleteRoutine(projectIndicesToDelete);

    }

    public void testContextMenuDelete2() throws IOException, MidiException {
        int[] projectIndicesToDelete = {0};
        baseDeleteRoutine(projectIndicesToDelete);
    }

    public void testContextMenuDelete3() throws IOException, MidiException {
        int[] projectIndicesToDelete = new int[NUMBER_OF_SAMPLE_PROJECTS];
        for (int i = 0; i < projectIndicesToDelete.length; i++)
            projectIndicesToDelete[i] = i;
        baseDeleteRoutine(projectIndicesToDelete);
    }

    private void baseDeleteRoutine(int[] projectIndicesToDelete) throws IOException, MidiException {
        createSampleProjects(NUMBER_OF_SAMPLE_PROJECTS);

        solo.clickOnView(getActivity().findViewById(R.id.action_refresh_project));
        solo.sleep(1000);

        for (int i = 0; i < projectIndicesToDelete.length; i++) {
            if (i == 0)
                solo.clickLongOnText(FILE_NAME + projectIndicesToDelete[i]);
            else
                solo.clickOnText(FILE_NAME + projectIndicesToDelete[i]);

            expectedProjects.remove(projectIndicesToDelete[i] - i);
        }

        solo.clickOnView(getActivity().findViewById(R.id.action_delete_project));
        solo.sleep(1000);

        updateCurrentProjects();
        assertEquals(currentProjects, expectedProjects);
    }


}