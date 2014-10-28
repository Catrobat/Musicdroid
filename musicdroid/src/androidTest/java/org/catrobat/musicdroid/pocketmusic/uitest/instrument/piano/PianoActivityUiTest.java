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

package org.catrobat.musicdroid.pocketmusic.uitest.instrument.piano;

import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.instrument.piano.PianoActivity;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.Track;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiException;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;
import org.catrobat.musicdroid.pocketmusic.test.note.ProjectTestDataFactory;
import org.catrobat.musicdroid.pocketmusic.test.note.midi.ProjectToMidiConverterTestDataFactory;

import java.io.File;
import java.io.IOException;

public class PianoActivityUiTest extends ActivityInstrumentationTestCase2<PianoActivity> {

    private static final int MENU_BUTTON_ID = 1;
    private static final String PIANO_BUTTON = "C";

    private PianoActivity pianoActivity;
    private Solo solo;

    public PianoActivityUiTest() {
        super(PianoActivity.class);
    }

    @Override
    protected void setUp() {
        pianoActivity = getActivity();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown() {
        for(File file: ProjectToMidiConverter.MIDI_FOLDER.listFiles())
            file.delete();

        solo.finishOpenedActivities();
    }

    public void testExportMidi1() {
        String filename = "music";
        boolean saveFile = true;
        boolean expectedFileExists = true;

        exportMidi(filename, saveFile);

        assertFileExists(filename, expectedFileExists);
    }

    public void testExportMidi2() {
        String filename = "";
        boolean saveFile = true;
        boolean expectedFileExists = false;

        exportMidi(filename, saveFile);

        assertFileExists(filename, expectedFileExists);
    }

    public void testExportMidi3() {
        String filename = "music";
        boolean saveFile = false;
        boolean expectedFileExists = false;

        exportMidi(filename, saveFile);

        assertFileExists(filename, expectedFileExists);
    }

    private void assertFileExists(String filename, boolean expectedExistResult) {
        File file = new File(ProjectToMidiConverter.MIDI_FOLDER, filename + ProjectToMidiConverter.MIDI_FILE_EXTENSION);

        assertEquals(expectedExistResult, file.exists());
    }

    private void exportMidi(String filename, boolean clickOnSaveButton) {
        solo.clickOnButton(PIANO_BUTTON);
        solo.pressMenuItem(MENU_BUTTON_ID);
        solo.clickOnMenuItem(pianoActivity.getString(R.string.action_export_midi_title));
        solo.waitForDialogToOpen();
        solo.clearEditText(pianoActivity.getEditTextMidiExportNameDialogPrompt());
        solo.enterText(pianoActivity.getEditTextMidiExportNameDialogPrompt(), filename);

        if(clickOnSaveButton) {
            solo.clickOnButton(pianoActivity.getString(R.string.action_export_dialog_positive_button));

            if ((filename != null) && (false == filename.equals(""))) {
                solo.waitForText(pianoActivity.getString(R.string.action_export_midi_success));
            } else {
                solo.waitForText(pianoActivity.getString(R.string.action_export_midi_cancel));
            }
        } else {
            solo.clickOnButton(pianoActivity.getString(R.string.action_export_dialog_negative_button));
        }
    }

    private void importMidi(String filename) {
        solo.pressMenuItem(MENU_BUTTON_ID);
        solo.clickOnMenuItem(pianoActivity.getString(R.string.action_import_midi_title));
        solo.waitForDialogToOpen();
        solo.clickOnText(filename);
        solo.waitForText(pianoActivity.getString(R.string.action_import_midi_success));
    }

    public void testImportMidi1() throws IOException, MidiException {
        boolean expectedFileExists = true;
        String filename = "testFile";

        ProjectToMidiConverterTestDataFactory.writeTestProject(filename);
        assertFileExists(filename, expectedFileExists);
        importMidi(filename);
    }

    public void testClear() {
        solo.clickOnButton(PIANO_BUTTON);
        solo.pressMenuItem(MENU_BUTTON_ID);
        solo.clickOnMenuItem(pianoActivity.getString(R.string.action_delete_midi_title));
        solo.waitForText(pianoActivity.getString(R.string.action_delete_midi_success));

        Track newTrack = getActivity().getTrack();

        assertEquals(0, newTrack.size());
    }

    public void testUndo() {
        int expectedTrackSize = 0;

        solo.clickOnButton(PIANO_BUTTON);
        solo.pressMenuItem(0);

        assertEquals(expectedTrackSize, pianoActivity.getTrack().size());
    }

    public void testRotate() {
        int expectedTrackSize = 4;

        solo.clickOnButton(PIANO_BUTTON);
        solo.setActivityOrientation(Solo.LANDSCAPE);
        getInstrumentation().waitForIdleSync();
        solo.clickOnButton(PIANO_BUTTON);

        int actualTrackSize = pianoActivity.getTrack().size();

        assertEquals(expectedTrackSize, actualTrackSize);
    }
}