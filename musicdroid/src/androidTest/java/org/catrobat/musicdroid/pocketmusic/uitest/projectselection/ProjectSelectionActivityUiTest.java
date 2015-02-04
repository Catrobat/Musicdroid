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
import android.widget.ListView;

import com.robotium.solo.Solo;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.instrument.piano.PianoActivity;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiException;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiPlayer;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;
import org.catrobat.musicdroid.pocketmusic.projectselection.ProjectSelectionActivity;
import org.catrobat.musicdroid.pocketmusic.test.note.ProjectTestDataFactory;
import org.catrobat.musicdroid.pocketmusic.test.note.midi.ProjectToMidiConverterTestDataFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class ProjectSelectionActivityUiTest extends ActivityInstrumentationTestCase2<ProjectSelectionActivity> {

    private static final int NUMBER_OF_SAMPLE_PROJECTS = 10;
    private MidiPlayer midiplayer = MidiPlayer.getInstance();

    private Solo solo;
    private static final String FILE_NAME = "TestProject";
    private ProjectSelectionActivity projectSelectionActivity;


    public ProjectSelectionActivityUiTest() {
        super(ProjectSelectionActivity.class);
    }

    @Override
    protected void setUp() {
        solo = new Solo(getInstrumentation(), getActivity());
        projectSelectionActivity = getActivity();

        if (ProjectToMidiConverter.MIDI_FOLDER.isDirectory()) {
            for (File file : ProjectToMidiConverter.MIDI_FOLDER.listFiles())
                file.delete();
        }
    }

    @Override
    protected void tearDown() {
        if (ProjectToMidiConverter.MIDI_FOLDER.isDirectory()) {
            for (File file : ProjectToMidiConverter.MIDI_FOLDER.listFiles())
                file.delete();
        }
        solo.finishOpenedActivities();
    }

    // --------------------------------------- HELPER ----------------------------------------------
    private void createSampleProjectFiles(int amount) throws IOException, MidiException {
        for (int i = 0; i < amount; i++) {
            Project project = ProjectTestDataFactory.createProjectWithOneSimpleTrack(FILE_NAME + i);
            ProjectToMidiConverterTestDataFactory.writeTestProject(project);
        }
    }

    private ArrayList<File> getProjectFilesInStorage() {
        ArrayList<File> projectFiles = new ArrayList<>();
        if (ProjectToMidiConverter.MIDI_FOLDER.isDirectory()) {
            Collections.addAll(projectFiles, ProjectToMidiConverter.MIDI_FOLDER.listFiles());
        }
        return projectFiles;
    }

    private void baseDeleteRoutine(int[] projectIndicesToDelete) throws IOException, MidiException {
        createSampleProjectFiles(NUMBER_OF_SAMPLE_PROJECTS);
        ArrayList<File> expectedProjects = getProjectFilesInStorage();
        clickRefreshActionButton();

        for (int i = 0; i < projectIndicesToDelete.length; i++) {
            if (i == 0)
                solo.clickLongOnText(FILE_NAME + projectIndicesToDelete[i]);
            else
                solo.clickOnText(FILE_NAME + projectIndicesToDelete[i]);

            expectedProjects.remove(projectIndicesToDelete[i] - i);
        }

        solo.clickOnView(getActivity().findViewById(R.id.callback_action_delete_project));
        solo.sleep(1000);

        assertEquals(getProjectFilesInStorage(), expectedProjects);
    }

    private void mainMenuDeleteRoutine(int[] projectIndicesToDelete) throws IOException, MidiException {
        createSampleProjectFiles(NUMBER_OF_SAMPLE_PROJECTS);
        ArrayList<File> expectedProjects = getProjectFilesInStorage();
        clickRefreshActionButton();

        solo.clickOnView(getActivity().findViewById(R.id.action_delete_project));
        for (int i = 0; i < projectIndicesToDelete.length; i++) {
            solo.clickOnText(FILE_NAME + projectIndicesToDelete[i]);
            expectedProjects.remove(projectIndicesToDelete[i] - i);
        }
        solo.goBack();
        solo.sleep(1000);

        assertEquals(getProjectFilesInStorage(), expectedProjects);
    }

    private void clickRefreshActionButton() {
        solo.clickOnView(projectSelectionActivity.findViewById(R.id.action_refresh_project));
        solo.sleep(100);
    }

    // --------------------------------------- TESTS ----------------------------------------------
    public void testRefreshActionButton() throws IOException, MidiException {
        createSampleProjectFiles(3);
        clickRefreshActionButton();
        solo.clickOnText(FILE_NAME + 2);
    }

    public void testContextMenuDelete() throws IOException, MidiException {
        int[] projectIndicesToDelete = {0, NUMBER_OF_SAMPLE_PROJECTS / 2, NUMBER_OF_SAMPLE_PROJECTS - 1};
        baseDeleteRoutine(projectIndicesToDelete);
        mainMenuDeleteRoutine(projectIndicesToDelete);
    }

    public void testContextMenuDelete2() throws IOException, MidiException {
        int[] projectIndicesToDelete = {0};
        baseDeleteRoutine(projectIndicesToDelete);
        mainMenuDeleteRoutine(projectIndicesToDelete);
    }

    public void testContextMenuDelete3() throws IOException, MidiException {
        int[] projectIndicesToDelete = new int[NUMBER_OF_SAMPLE_PROJECTS];
        for (int i = 0; i < projectIndicesToDelete.length; i++)
            projectIndicesToDelete[i] = i;
        baseDeleteRoutine(projectIndicesToDelete);
        mainMenuDeleteRoutine(projectIndicesToDelete);
    }

    public void testLinkToNextActivity() {
        solo.clickOnButton(getActivity().getResources().getString(R.string.action_project_add));
        solo.waitForActivity(PianoActivity.class);
        solo.assertCurrentActivity(PianoActivity.class.getSimpleName(), PianoActivity.class);
    }

    public void testProjectListCount() throws IOException, MidiException {
        createSampleProjectFiles(99);
        clickRefreshActionButton();
        solo.sleep(1000);
        assertEquals(getActivity().getProjectSelectionFragment().getListViewAdapter().getCount(), 99);
    }

    public void testPlayButton() throws IOException, MidiException {

        createSampleProjectFiles(3);

        clickRefreshActionButton();
        solo.clickOnView(solo.getView(R.id.project_play_button, 2));

        solo.sleep(100);
        assertEquals(midiplayer.isPlaying(), true);

        ListView list = (ListView) solo.getView(R.id.project_list_view);
        Project project = (Project) list.getAdapter().getItem(2);
        solo.sleep((int) project.getTrack(0).getTotalTimeInMilliseconds());

        assertEquals(midiplayer.isPlaying(), false);
    }
    public void testRandomInteraction() throws IOException, MidiException {
        createSampleProjectFiles(5);

        clickRefreshActionButton();
        solo.clickOnView(solo.getView(R.id.project_play_button, 2));
        solo.sleep(100);
        assertEquals(midiplayer.isPlaying(), true);

        solo.clickOnView(getActivity().findViewById(R.id.action_delete_project));
        solo.goBack();
        solo.clickOnButton(getActivity().getResources().getString(R.string.action_project_add));
        solo.waitForActivity(PianoActivity.class);

    }
    public void testRefreshActionButton2() throws IOException, MidiException {

        createSampleProjectFiles(3);

        clickRefreshActionButton();
        solo.clickOnView(solo.getView(R.id.project_play_button, 2));
        solo.sleep(100);

        clickRefreshActionButton();

        assertEquals(midiplayer.isPlaying(), false);
    }


}