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
import android.view.View;

import com.robotium.solo.Solo;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.instrument.InstrumentActivity;
import org.catrobat.musicdroid.pocketmusic.instrument.piano.PianoActivity;
import org.catrobat.musicdroid.pocketmusic.note.NoteName;
import org.catrobat.musicdroid.pocketmusic.note.Octave;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;
import org.catrobat.musicdroid.pocketmusic.test.note.NoteEventTestDataFactory;

import java.io.File;

public class PianoActivityUiTest extends ActivityInstrumentationTestCase2<PianoActivity> {

    private static final String PIANO_BUTTON_TEXT = "C";

    private PianoActivity pianoActivity;
    private Solo solo;

    public PianoActivityUiTest() {
        super(PianoActivity.class);
    }

    @Override
    protected void setUp() {
        solo = new Solo(getInstrumentation(), getActivity());
        pianoActivity = getActivity();
        pianoActivity.inCallback = false;
    }

    @Override
    protected void tearDown() {
        if (ProjectToMidiConverter.MIDI_FOLDER.isDirectory()) {
            for (File file : ProjectToMidiConverter.MIDI_FOLDER.listFiles())
                file.delete();
        }

        pianoActivity.getMidiPlayer().stop();

        solo.finishOpenedActivities();
    }

    public void testClear() {
        solo.clickOnButton(PIANO_BUTTON_TEXT);
        solo.clickOnActionBarItem(R.id.action_clear_midi);
        assertTrue(solo.waitForText(pianoActivity.getString(R.string.clear_success)));

        assertEquals(0, pianoActivity.getSymbolContainer().size());
    }

    public void testClearUndo() {
        solo.clickOnButton(PIANO_BUTTON_TEXT);
        solo.clickOnActionBarItem(R.id.action_clear_midi);

        assertTrue(solo.waitForText(pianoActivity.getString(R.string.clear_success)));

        solo.clickOnActionBarItem(R.id.action_undo_midi);

        assertEquals(1, pianoActivity.getSymbolContainer().size());
    }

    public void testUndo() {
        solo.clickOnButton(PIANO_BUTTON_TEXT);
        solo.clickOnActionBarItem(R.id.action_undo_midi);

        assertEquals(0, pianoActivity.getSymbolContainer().size());
    }

    public void testUndoBreak() {
        clickBreak();
        solo.clickOnActionBarItem(R.id.action_undo_midi);

        assertEquals(0, pianoActivity.getSymbolContainer().size());
    }

    public void testRotateWithSymbolsDrawn() {
        solo.clickOnButton(PIANO_BUTTON_TEXT);
        rotateAndReturnActivity(Solo.LANDSCAPE);
        solo.clickOnButton(PIANO_BUTTON_TEXT);

        assertEquals(2, pianoActivity.getSymbolContainer().size());
    }

    private void rotateAndReturnActivity(int orientation) {
        solo.setActivityOrientation(orientation);
        getInstrumentation().waitForIdleSync();

        pianoActivity = (PianoActivity) solo.getCurrentActivity();
    }

    public void testRotateAndUndo() {
        solo.clickOnButton(PIANO_BUTTON_TEXT);
        rotateAndReturnActivity(Solo.LANDSCAPE);
        solo.clickOnButton(PIANO_BUTTON_TEXT);
        rotateAndReturnActivity(Solo.PORTRAIT);

        solo.clickOnActionBarItem(R.id.action_undo_midi);
        solo.clickOnActionBarItem(R.id.action_undo_midi);

        assertEquals(0, pianoActivity.getSymbolContainer().size());
    }

    public void testPlayMidi() throws InterruptedException {
        clickSomePianoButtonsForLargeTrack();
        solo.clickOnActionBarItem(R.id.action_play_and_stop_midi);
        Thread.sleep(100);
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
        solo.waitForText(pianoActivity.getString(R.string.stopped));
        assertFalse(pianoActivity.getMidiPlayer().isPlaying());
    }

    private void clickSomePianoButtonsForLargeTrack() {
        int numberOfNotes = 5;

        for (int i = 0; i < numberOfNotes; i++) {
            solo.clickOnButton(PIANO_BUTTON_TEXT);
        }
    }

    public void testPlayMidiEmptyTrack() {
        solo.clickOnActionBarItem(R.id.action_play_and_stop_midi);

        assertFalse(pianoActivity.getMidiPlayer().isPlaying());
    }

    public void testPlayMidiFinishedPlaying() throws InterruptedException {
        solo.clickOnButton(PIANO_BUTTON_TEXT);
        solo.clickOnActionBarItem(R.id.action_play_and_stop_midi);
        solo.waitForDialogToOpen();
        solo.waitForDialogToClose();

        assertFalse(pianoActivity.getMidiPlayer().isPlaying());
    }

    public void testClickOnButtonMaxTrackSize() {
        int buttonPressCount = InstrumentActivity.MAX_SYMBOLS_SIZE;

        for (int i = 0; i < buttonPressCount; i++) {
            solo.clickOnButton(PIANO_BUTTON_TEXT);
        }

        solo.clickOnButton(PIANO_BUTTON_TEXT);

        assertEquals(InstrumentActivity.MAX_SYMBOLS_SIZE, pianoActivity.getSymbolContainer().size());
    }

    public void testMaxTrackSizeTextView() {
        int buttonPressCount = 6;

        for (int i = 0; i < buttonPressCount; i++) {
            solo.clickOnButton(PIANO_BUTTON_TEXT);
        }

        String expectedTextViewText = buttonPressCount + " / " + InstrumentActivity.MAX_SYMBOLS_SIZE;
        String actualTextViewText = pianoActivity.getTrackSizeString();

        assertEquals(expectedTextViewText, actualTextViewText);
    }

    public void testEditModeDelete() {
        enterEditModeWithOneMarkedSymbol();

        assertTrue(PianoActivity.inCallback);

        clickDeleteInEditMode();

        assertFalse(PianoActivity.inCallback);
        assertEquals(0, pianoActivity.getSymbolContainer().size());
        assertEquals(0, pianoActivity.getSymbolContainer().getMarkedSymbolCount());
    }

    public void testEditModeDeleteUndo() {
        enterEditModeWithOneMarkedSymbol();
        clickDeleteInEditMode();

        solo.clickOnActionBarItem(R.id.action_undo_midi);

        assertEquals(1, pianoActivity.getSymbolContainer().size());
    }

    public void testEditModeReplace() {
        enterEditModeWithOneMarkedSymbol();
        solo.clickOnButton(PIANO_BUTTON_TEXT);

        assertEquals(1, pianoActivity.getSymbolContainer().size());
        assertTrue(pianoActivity.getSymbolContainer().get(0).isMarked());
    }

    public void testEditModeReplaceAddAccord() throws Throwable {
        enterEditModeWithOneMarkedSymbol();

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                pianoActivity.addNoteEvent(NoteEventTestDataFactory.createNoteEvent(NoteName.C4, true));
                pianoActivity.addNoteEvent(NoteEventTestDataFactory.createNoteEvent(NoteName.D4, true));
                pianoActivity.addNoteEvent(NoteEventTestDataFactory.createNoteEvent(NoteName.C4, false));
                pianoActivity.addNoteEvent(NoteEventTestDataFactory.createNoteEvent(NoteName.D4, false));
            }
        });

        solo.sleep(1000);

        assertEquals(1, pianoActivity.getSymbolContainer().size());
    }

    private void enterEditModeWithOneMarkedSymbol() {
        solo.clickOnButton(PIANO_BUTTON_TEXT);

        solo.sleep(1000);

        pianoActivity.getSymbolContainer().get(0).setMarked(true);
        solo.clickLongOnView(pianoActivity.getNoteSheetView());
    }

    private void clickDeleteInEditMode() {
        View v = getActivity().findViewById(R.id.edit_callback_action_delete_project);
        solo.clickOnView(v);

        solo.sleep(1000);
    }

    public void testClickBreak() {
        clickBreak();

        assertEquals(1, pianoActivity.getSymbolContainer().size());
        assertTrue(pianoActivity.getAdditionalSettingsFragment().isPianoViewVisible());
    }

    public void testClickBreakAndReplace() {
        clickBreak();

        pianoActivity.getSymbolContainer().get(0).setMarked(true);
        solo.clickLongOnView(pianoActivity.getNoteSheetView());

        solo.sleep(1000);

        solo.clickOnButton(PIANO_BUTTON_TEXT);

        assertEquals(1, pianoActivity.getSymbolContainer().size());
        assertTrue(pianoActivity.getSymbolContainer().get(0).isMarked());
    }

    private void clickBreak() {
        solo.setActivityOrientation(Solo.PORTRAIT);
        solo.clickOnButton(getActivity().getString(R.string.breaks));
        solo.clickOnImageButton(1);

        solo.sleep(1000);
    }

    public void testClickOctaveDownButton() {
        solo.clickOnButton(getActivity().getString(R.string.minus));

        solo.sleep(1000);

        assertEquals(Octave.DEFAULT_OCTAVE.previous(), pianoActivity.getOctave());
        assertEquals(pianoActivity.getNoteSheetViewFragment().getOctaveText(), getActivity().getString(R.string.octave) + " " + (-1));
    }

    public void testClickOctaveUpButton() {
        solo.clickOnButton(getActivity().getString(R.string.plus));

        solo.sleep(1000);

        assertEquals(Octave.DEFAULT_OCTAVE.next(), pianoActivity.getOctave());
        assertEquals(pianoActivity.getNoteSheetViewFragment().getOctaveText(), getActivity().getString(R.string.octave) + " " + (1));
    }
}
