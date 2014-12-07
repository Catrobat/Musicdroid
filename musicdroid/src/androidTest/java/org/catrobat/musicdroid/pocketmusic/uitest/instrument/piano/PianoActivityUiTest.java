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

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.instrument.InstrumentActivity;
import org.catrobat.musicdroid.pocketmusic.instrument.piano.PianoActivity;
import org.catrobat.musicdroid.pocketmusic.note.Track;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiException;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;
import org.catrobat.musicdroid.pocketmusic.test.note.midi.ProjectToMidiConverterTestDataFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PianoActivityUiTest extends ActivityInstrumentationTestCase2<PianoActivity> {

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
        if (ProjectToMidiConverter.MIDI_FOLDER.isDirectory()) {
            for(File file: ProjectToMidiConverter.MIDI_FOLDER.listFiles())
                file.delete();
        }

        pianoActivity.getMidiPlayer().stop();

        solo.finishOpenedActivities();
    }

    public void testExportMidi() {
        String filename = "music";
        boolean saveFile = true;
        boolean expectedFileExists = true;

        exportMidi(filename, saveFile);

        assertFileExists(filename, expectedFileExists);
    }

    public void testExportMidiInvalidName() {
        String invalidFilename = "";
        boolean saveFile = true;
        boolean expectedFileExists = false;

        exportMidi(invalidFilename, saveFile);

        assertFileExists(invalidFilename, expectedFileExists);
    }

    public void testExportMidiCancel() {
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
        solo.clickOnActionBarItem(R.id.action_export_midi);
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
        solo.clickOnActionBarItem(R.id.action_import_midi);
        solo.waitForDialogToOpen();
        solo.clickOnText(filename);
        solo.waitForText(pianoActivity.getString(R.string.action_import_midi_success));
    }

    public void testImportMidi() throws IOException, MidiException {
        boolean expectedFileExists = true;
        String filename = "testFile";
        ProjectToMidiConverterTestDataFactory.writeTestProject(filename);
        assertFileExists(filename, expectedFileExists);
        importMidi(filename);
    }

    public void testClear() {
        solo.clickOnButton(PIANO_BUTTON);
        solo.clickOnActionBarItem(R.id.action_clear_midi);
        solo.waitForText(pianoActivity.getString(R.string.action_delete_midi_success));

        Track newTrack = getActivity().getTrack();

        assertEquals(0, newTrack.size());
    }

    public void testUndo() {
        int expectedTrackSize = 0;

        solo.clickOnButton(PIANO_BUTTON);
        solo.clickOnActionBarItem(R.id.action_undo_midi);

        assertEquals(expectedTrackSize, pianoActivity.getTrack().size());
    }

    public void testRotateWithSymbolsDrawn() {
        int expectedTrackSize = 4;

        solo.clickOnButton(PIANO_BUTTON);
        rotateAndReturnActivity(Solo.LANDSCAPE);
        solo.clickOnButton(PIANO_BUTTON);

        int actualTrackSize = pianoActivity.getTrack().size();

        assertEquals(expectedTrackSize, actualTrackSize);
    }

    private void rotateAndReturnActivity(int orientation) {
        solo.setActivityOrientation(orientation);
        getInstrumentation().waitForIdleSync();

        pianoActivity = (PianoActivity) solo.getCurrentActivity();
    }

    public void testRotateAndUndo() {
        int expectedTrackSize = 0;

        solo.clickOnButton(PIANO_BUTTON);
        rotateAndReturnActivity(Solo.LANDSCAPE);
        solo.clickOnButton(PIANO_BUTTON);
        rotateAndReturnActivity(Solo.PORTRAIT);

        solo.clickOnActionBarItem(R.id.action_undo_midi);
        solo.clickOnActionBarItem(R.id.action_undo_midi);

        int actualTrackSize = pianoActivity.getTrack().size();

        assertEquals(expectedTrackSize, actualTrackSize);
    }

    public void testPlayMidi() {
        clickSomePianoButtonsForLargeTrack();
        solo.clickOnActionBarItem(R.id.action_play_midi);
        solo.waitForDialogToOpen();

        assertTrue(pianoActivity.getMidiPlayer().isPlaying());
    }

    private void clickSomePianoButtonsForLargeTrack() {
        int numberOfNotes = 5;

        for (int i = 0; i < numberOfNotes; i++) {
            solo.clickOnButton(PIANO_BUTTON);
        }
    }

    private void clickSomePianoButtonsFastForLargeTrack() {

        int threadCount = 3;
        List<Thread> playThreads = new ArrayList<>();
        for(int i = 0; i < threadCount; i++){
            playThreads.add(new Thread() {
                public void run() {
                    clickSomePianoButtonsForLargeTrack();
                }
            });
            playThreads.get(i).start();
        }

        clickSomePianoButtonsForLargeTrack();

    }

    public void testPlayMidiStop() {
        clickSomePianoButtonsForLargeTrack();
        solo.clickOnActionBarItem(R.id.action_play_midi);
        solo.waitForDialogToOpen();
        solo.clickOnButton(pianoActivity.getString(R.string.action_play_midi_dialog_stop));
        solo.waitForDialogToClose();

        assertFalse(pianoActivity.getMidiPlayer().isPlaying());
    }

    public void testPlayMidiEmptyTrack() {
        solo.clickOnActionBarItem(R.id.action_play_midi);

        assertFalse(pianoActivity.getMidiPlayer().isPlaying());
    }

    public void testPlayMidiFinishedPlaying() throws InterruptedException {
        solo.clickOnButton(PIANO_BUTTON);
        solo.clickOnActionBarItem(R.id.action_play_midi);
        solo.waitForDialogToOpen();
        solo.waitForDialogToClose();

        assertFalse(pianoActivity.getMidiPlayer().isPlaying());
    }

    public void testPlayMidiWhileRotating() throws InterruptedException {
        solo.setActivityOrientation(Solo.PORTRAIT);
        clickSomePianoButtonsFastForLargeTrack();
        assertTrue(pianoActivity.getMidiPlayer().getPlayQueue().size() > 0);

        solo.setActivityOrientation(Solo.LANDSCAPE);
        clickSomePianoButtonsFastForLargeTrack();
        assertTrue(pianoActivity.getMidiPlayer().getPlayQueue().size() > 0);

        solo.setActivityOrientation(Solo.PORTRAIT);
        clickSomePianoButtonsFastForLargeTrack();
        assertTrue(pianoActivity.getMidiPlayer().getPlayQueue().size() > 0);

    }

    public void testPlayMidiBackButtonPress() throws InterruptedException {
        solo.setActivityOrientation(Solo.PORTRAIT);
        clickSomePianoButtonsFastForLargeTrack();
        assertTrue(pianoActivity.getMidiPlayer().getPlayQueue().size() > 0);

        solo.setActivityOrientation(Solo.LANDSCAPE);
        clickSomePianoButtonsFastForLargeTrack();
        assertTrue(pianoActivity.getMidiPlayer().getPlayQueue().size() > 0);

        solo.goBack();
        assertTrue(pianoActivity.getMidiPlayer().getPlayQueue().size() == 0);
    }

    public void testClickOnButtonMaxTrackSize() {
        int buttonPressCount = InstrumentActivity.MAX_TRACK_SIZE_IN_SYMBOLS;

        for (int i = 0; i < buttonPressCount; i++) {
            solo.clickOnButton(PIANO_BUTTON);
        }

        solo.clickOnButton(PIANO_BUTTON);

        int expectedTrackCount = InstrumentActivity.MAX_TRACK_SIZE_IN_NOTE_EVENTS;
        int actualTrackCount = pianoActivity.getTrack().size();

        assertEquals(expectedTrackCount, actualTrackCount);
    }
    public void testMaxTrackSizeTextView() {
        int buttonPressCount = 6;

        if(buttonPressCount > InstrumentActivity.MAX_TRACK_SIZE_IN_SYMBOLS)
            buttonPressCount = 2;

        for (int i = 0; i < buttonPressCount; i++) {
            solo.clickOnButton(PIANO_BUTTON);
        }

        String expectedTextViewText = buttonPressCount + " / " + InstrumentActivity.MAX_TRACK_SIZE_IN_SYMBOLS;
        String actualTextViewText = pianoActivity.getNoteSheetViewFragment().getTrackSizeTextViewText();

        assertEquals(expectedTextViewText, actualTextViewText);
    }
}