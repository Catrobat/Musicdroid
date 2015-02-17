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
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.Track;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiException;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;
import org.catrobat.musicdroid.pocketmusic.test.note.ProjectTestDataFactory;
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
        solo = new Solo(getInstrumentation(), getActivity());
        pianoActivity = getActivity();
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

/*
    private void assertFileExists(String filename, boolean expectedExistResult) {
        File file = new File(ProjectToMidiConverter.MIDI_FOLDER, filename + ProjectToMidiConverter.MIDI_FILE_EXTENSION);

        assertEquals(expectedExistResult, file.exists());
    }

    private void loadMidi(String filename) {
        solo.clickOnActionBarItem(R.id.action_load_midi);
        solo.waitForDialogToOpen();
        solo.clickOnText(filename);
        assertTrue(solo.waitForText(pianoActivity.getString(R.string.action_load_midi_success)));
    }

    public void testLoadtMidi() throws IOException, MidiException {
        boolean expectedFileExists = true;
        Project project = ProjectTestDataFactory.createProjectWithSemiComplexTracks();
        ProjectToMidiConverterTestDataFactory.writeTestProject(project);
        assertFileExists(project.getName(), expectedFileExists);
        loadMidi(project.getName());
    }

    public void testClear() {
        solo.clickOnButton(PIANO_BUTTON);
        solo.clickOnActionBarItem(R.id.action_clear_midi);
        assertTrue(solo.waitForText(pianoActivity.getString(R.string.action_delete_midi_success)));

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
*/

    public void testPlayMidi() {
        clickSomePianoButtonsForLargeTrack();
        solo.clickOnActionBarItem(R.id.action_play_and_stop_midi);
        solo.waitForText(pianoActivity.getString(R.string.action_midi_playing));
        assertTrue(pianoActivity.getMidiPlayer().isPlaying());
    }

    public void testPlayButtonShown() {
        assertTrue(solo.getCurrentActivity().getResources().getDrawable(R.drawable.ic_action_play).isVisible());
    }

    public void testStopButtonShown() {
        clickSomePianoButtonsForLargeTrack();
        solo.clickOnActionBarItem(R.id.action_play_and_stop_midi);
        assertTrue(solo.getCurrentActivity().getResources().getDrawable(R.drawable.ic_action_stop).isVisible());
    }

    public void testPlayButtonShownAfterStop() {
        clickSomePianoButtonsForLargeTrack();
        solo.clickOnActionBarItem(R.id.action_play_and_stop_midi);
        solo.clickOnActionBarItem(R.id.action_play_and_stop_midi);
        assertTrue(solo.getCurrentActivity().getResources().getDrawable(R.drawable.ic_action_play).isVisible());
    }

    public void testStopMidi() {
        clickSomePianoButtonsForLargeTrack();
        solo.clickOnActionBarItem(R.id.action_play_and_stop_midi);
        solo.clickOnActionBarItem(R.id.action_play_and_stop_midi);
        solo.waitForText(pianoActivity.getString(R.string.action_midi_stopped));
        assertFalse(pianoActivity.getMidiPlayer().isPlaying());
    }

    private void clickSomePianoButtonsForLargeTrack() {
        int numberOfNotes = 5;

        for (int i = 0; i < numberOfNotes; i++) {
            solo.clickOnButton(PIANO_BUTTON);
        }
    }

    public void testPlayMidiEmptyTrack() {
        solo.clickOnActionBarItem(R.id.action_play_and_stop_midi);

        assertFalse(pianoActivity.getMidiPlayer().isPlaying());
    }

    public void testPlayMidiFinishedPlaying() throws InterruptedException {
        solo.clickOnButton(PIANO_BUTTON);
        solo.clickOnActionBarItem(R.id.action_play_and_stop_midi);
        solo.waitForText(pianoActivity.getString(R.string.action_midi_finished));

        assertFalse(pianoActivity.getMidiPlayer().isPlaying());
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

        for (int i = 0; i < buttonPressCount; i++) {
            solo.clickOnButton(PIANO_BUTTON);
        }

        String expectedTextViewText = buttonPressCount + " / " + InstrumentActivity.MAX_TRACK_SIZE_IN_SYMBOLS;
        String actualTextViewText = pianoActivity.getTrackSizeString();

        assertEquals(expectedTextViewText, actualTextViewText);
    }

    public void testEditMode() {
        solo.clickOnButton(PIANO_BUTTON);
        solo.clickLongOnView(pianoActivity.getNoteSheetView());

        assertTrue(PianoActivity.inCallback);
    }
}