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
        if (ProjectToMidiConverter.MIDI_FOLDER.isDirectory()) {
            for (File file : ProjectToMidiConverter.MIDI_FOLDER.listFiles())
                file.delete();
        }

        try {
            createSampleProjectFiles(NUMBER_OF_SAMPLE_PROJECTS);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MidiException e) {
            e.printStackTrace();
        }

        solo = new Solo(getInstrumentation(), getActivity());
        projectSelectionActivity = getActivity();
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

    private void createSampleProjectFile(String name) throws IOException, MidiException {
        Project project = ProjectTestDataFactory.createProjectWithOneSimpleTrack(name);
        ProjectToMidiConverterTestDataFactory.writeTestProject(project);
    }

    private void tapAndHoldDeleteRoutine(int[] projectIndicesToDelete) throws IOException, MidiException {
        ArrayList<File> expectedProjects = ProjectTestDataFactory.getProjectFilesInStorage();

        for (int i = 0; i < projectIndicesToDelete.length; i++) {
            if (i == 0)
                solo.clickLongOnText(FILE_NAME + projectIndicesToDelete[i]);
            else
                solo.clickOnText(FILE_NAME + projectIndicesToDelete[i]);

            expectedProjects.remove(projectIndicesToDelete[i] - i);
        }

        solo.clickOnView(getActivity().findViewById(R.id.callback_action_delete_project));
        solo.waitForText(projectSelectionActivity.getString(R.string.delete_success));

        assertEquals(ProjectTestDataFactory.getProjectFilesInStorage(), expectedProjects);
    }

    private void deleteButtonRoutine(int[] projectIndicesToDelete) throws IOException, MidiException {
        ArrayList<File> expectedProjects = ProjectTestDataFactory.getProjectFilesInStorage();

        solo.clickOnView(getActivity().findViewById(R.id.action_delete_project));
        for (int i = 0; i < projectIndicesToDelete.length; i++) {
            solo.clickOnText(FILE_NAME + projectIndicesToDelete[i]);
            expectedProjects.remove(projectIndicesToDelete[i] - i);
        }

        solo.clickOnView(getActivity().findViewById(R.id.callback_action_delete_project));
        solo.sleep(1000);

        assertEquals(ProjectTestDataFactory.getProjectFilesInStorage(), expectedProjects);
    }

    // --------------------------------------- TESTS ----------------------------------------------
    public void testContextMenuDelete() throws IOException, MidiException {
        int[] projectIndicesToDelete = {0, NUMBER_OF_SAMPLE_PROJECTS / 2, NUMBER_OF_SAMPLE_PROJECTS - 1};
        tapAndHoldDeleteRoutine(projectIndicesToDelete);
    }

    public void testContextMenuDelete2() throws IOException, MidiException {
        int[] projectIndicesToDelete = {0};
        tapAndHoldDeleteRoutine(projectIndicesToDelete);
    }

    public void testContextMenuDelete3() throws IOException, MidiException {
        int[] projectIndicesToDelete = new int[NUMBER_OF_SAMPLE_PROJECTS];
        for (int i = 0; i < projectIndicesToDelete.length; i++)
            projectIndicesToDelete[i] = i;
        tapAndHoldDeleteRoutine(projectIndicesToDelete);
    }

    public void testContextMenuDelete4() throws IOException, MidiException {
        int[] projectIndicesToDelete = new int[NUMBER_OF_SAMPLE_PROJECTS];
        for (int i = 0; i < projectIndicesToDelete.length; i++)
            projectIndicesToDelete[i] = i;
        deleteButtonRoutine(projectIndicesToDelete);
    }

    public void testLinkToNextActivity() {
        solo.clickOnButton(getActivity().getResources().getString(R.string.plus));
        solo.waitForActivity(PianoActivity.class);
        solo.assertCurrentActivity(PianoActivity.class.getSimpleName(), PianoActivity.class);
    }

    public void testProjectListCount() throws IOException, MidiException {
        solo.sleep(1000);
        assertEquals(getActivity().getProjectSelectionFragment().getListViewAdapter().getCount(), NUMBER_OF_SAMPLE_PROJECTS);
    }

    public void testPlayButton() throws IOException, MidiException {
        solo.clickOnView(solo.getView(R.id.project_play_button, 2));
        solo.sleep(500);
        assertEquals(midiplayer.isPlaying(), true);
    }

    public void testContextMenuTitle() throws IOException, MidiException {
        int counter = 3;
        solo.clickOnView(getActivity().findViewById(R.id.action_delete_project));
        for (int i = 0; i < counter; i++)
            solo.clickOnText(FILE_NAME + i);
        solo.sleep(100);
        assertEquals(counter + "",
                projectSelectionActivity.getProjectSelectionContextMenu().getActionMode().getTitle());
    }

    public void testRandomInteraction() throws IOException, MidiException {
        solo.clickOnView(solo.getView(R.id.project_play_button, 2));
        solo.sleep(100);
        assertEquals(midiplayer.isPlaying(), true);

        solo.clickOnView(getActivity().findViewById(R.id.action_delete_project));
        solo.goBack();
        solo.clickOnButton(getActivity().getResources().getString(R.string.plus));
        solo.waitForActivity(PianoActivity.class);
    }

    public void testAutoRefresh() throws IOException, MidiException {
        String fileName = "New_file";
        solo.clickOnButton(getActivity().getResources().getString(R.string.plus));
        solo.waitForActivity(PianoActivity.class);
        createSampleProjectFile(fileName);
        solo.goBack();
        assertTrue(solo.searchText(fileName));
    }
    public void testAboutDialog() throws IOException {
        solo.clickOnMenuItem(getActivity().getResources().getString(R.string.menu_about));
        solo.waitForDialogToOpen();

        assertTrue(solo.searchText(getActivity().getResources().getString(R.string.dialog_license_info)));
        assertTrue(solo.searchText(getActivity().getResources().getString(R.string.dialog_catrobat_link_text)));
        assertTrue(solo.searchText(getActivity().getResources().getString(R.string.dialog_pocketcode_license_link_text)));
        assertTrue(solo.searchText(getActivity().getResources().getString(R.string.dialog_title)));

        solo.clickOnButton(getActivity().getResources().getString(android.R.string.ok));

        assertTrue(solo.waitForDialogToClose());
    }
}
