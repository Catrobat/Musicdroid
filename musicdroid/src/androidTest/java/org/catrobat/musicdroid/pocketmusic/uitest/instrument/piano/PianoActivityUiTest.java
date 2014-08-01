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

import java.io.File;

public class PianoActivityUiTest extends ActivityInstrumentationTestCase2<PianoActivity> {

    private PianoActivity pianoActivity;
    private Solo solo;

    private String filePath = Environment.getExternalStorageDirectory().toString()
            + File.separator + "musicdroid" + File.separator;
    private String[] exportTestFileNames = {"testfile.midi", ".midi", "testfile.midis"};

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
        solo.finishOpenedActivities();
    }

    // TODO:if notesheetview is implemented, refactor testcase
    public void testPianoKeys(){
        solo.waitForActivity(PianoActivity.class);
        int numOfButtons = 12;
        int counter;
        for (counter = 0; counter < numOfButtons; counter++) {
            solo.clickOnButton(counter);
        }

        //int actualTrackSize = pianoActivity.getTrack().size();

        assertEquals(counter, numOfButtons);
    }

    public void testExportMidi() {
        exportMidiProcedure(exportTestFileNames[0], true, true);
        exportMidiProcedure(exportTestFileNames[1], false, true);
        exportMidiProcedure(exportTestFileNames[2], false, true);
        removeAllExportTestfiles();
        exportMidiProcedure(exportTestFileNames[0], false, false);
    }


    private void removeAllExportTestfiles() {
        for (int i = 0; i < exportTestFileNames.length; i++) {
            File file = new File(filePath, exportTestFileNames[i]);
            file.delete();
        }
    }

    // TODO fw fix me
    private void exportMidiProcedure(String filename, boolean fileShouldBePresent, boolean clickOnSaveButton) {
        /*solo.pressMenuItem(1);
        solo.clickOnMenuItem(pianoActivity.getString(R.string.action_export_midi_title));
        solo.waitForDialogToOpen();
        solo.clearEditText(pianoActivity.getDialogFileNameField());
        solo.enterText(pianoActivity.getDialogFileNameField(), filename);
        if(clickOnSaveButton)
            solo.clickOnButton(pianoActivity.getString(R.string.action_export_dialog_positive_button));
        else
            solo.clickOnButton(pianoActivity.getString(R.string.action_export_dialog_negative_button));
        if (fileShouldBePresent)
            solo.waitForText(pianoActivity.getString(R.string.action_export_midi_success));
        File file = new File(filePath, filename);
        if (file.exists()) {
            assertTrue(fileShouldBePresent);
        } else {
            assertTrue(!fileShouldBePresent);
        }*/
    }
}