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

package org.catrobat.musicdroid.pocketmusic.test.instrument.piano;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;

import org.catrobat.musicdroid.pocketmusic.instrument.piano.PianoActivity;
import org.catrobat.musicdroid.pocketmusic.instrument.piano.PianoViewFragment;

public class PianoActivityTest extends ActivityInstrumentationTestCase2<PianoActivity> {

    private static final int MIN_WIDTH = 240;
    private static final int MAX_WIDTH = 2000;

    private static final int MIN_HEIGHT = 240;
    private static final int MAX_HEIGHT = 2000;

    private PianoActivity pianoActivity;
    private PianoViewFragment pianoViewFragment;

    public PianoActivityTest(){
        super(PianoActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        pianoActivity = getActivity();
        pianoViewFragment = pianoActivity.getPianoViewFragment();
    }

    //---------------------------- FRAGMENT TESTS --------------------------------------------------
    public void testGetDisplayWidth() {
        assertTrue("The display width has to be larger than the min value",
                pianoViewFragment.getDisplayWidth() > MIN_WIDTH);
        assertTrue("The display width has to be smaller than the max value",
                pianoViewFragment.getDisplayWidth() < MAX_WIDTH);
    }

    public void testGetDisplayHeight() {
        assertTrue("The display height has to be larger than the min value",
                pianoViewFragment.getDisplayHeight() > MIN_HEIGHT);
        assertTrue("The display height has to be smaller than the max value",
                pianoViewFragment.getDisplayHeight() < MAX_HEIGHT);
    }

    @UiThreadTest
    public void testCalculatePianoKeyPositions(){
        int blackKeyHeightScaleFactor = 6;
        int keyWidthScaleFactor = 0;
        int keysPerOctave = 7;

        pianoViewFragment.calculatePianoKeyPositions(keyWidthScaleFactor, blackKeyHeightScaleFactor);

        assertButtonPosition(keyWidthScaleFactor, keysPerOctave);
    }

    private void assertButtonPosition(int keyWidthScaleFactor, int keysPerOctave) {
        int buttonWidth = pianoViewFragment.getDisplayWidth() / (keysPerOctave + keyWidthScaleFactor);

        for(int i = 0 ; i < pianoViewFragment.getBlackButtonCount(); i++)
            assertEquals("The width of the black button doesn't match",
                    pianoViewFragment.getBlackButtonAtIndex(i).getWidth(), buttonWidth);
        for(int i = 0 ; i < pianoViewFragment.getWhiteButtonCount(); i++)
            assertEquals("The width of the white button doesn't match",
                    pianoViewFragment.getWhiteButtonAtIndex(i).getWidth(), buttonWidth);

        assertEquals("The first black button is not on the right position",
                (buttonWidth / 2), pianoViewFragment.getBlackButtonAtIndex(0).getLeft());
        assertEquals("The second black button is not on the right position",
                (buttonWidth / 2 * 3), pianoViewFragment.getBlackButtonAtIndex(1).getLeft());

        assertEquals("The first white button is not on the right position",
                0 , pianoViewFragment.getWhiteButtonAtIndex(0).getLeft());
        assertEquals("The second white button is not on the right position",
                buttonWidth, pianoViewFragment.getWhiteButtonAtIndex(1).getLeft());
    }

    @UiThreadTest
    public void testDisableBlackKey1(){
        disableKeyAndAssertVisibility(1);
    }

    @UiThreadTest
    public void testDisableBlackKey2(){
        disableKeyAndAssertVisibility(3);
    }

    @UiThreadTest
    public void testDisableBlackKey3(){
        disableKeyAndAssertVisibility(100);
    }

    @UiThreadTest
    public void testDisableBlackKey4(){
        disableKeyAndAssertVisibility(-1);
    }

    private void disableKeyAndAssertVisibility(int index) {
        assertButtonVisibilityPianoLayout();

        pianoViewFragment.setBlackKeyInvisibilityAtIndex(index);

        Button button = pianoViewFragment.getBlackButtonAtIndex(index);

        if (button != null) {
            assertEquals("The black button should be invisible",
                    button.getVisibility(), Button.INVISIBLE);
        }
    }

    private void assertButtonVisibilityPianoLayout() {
        int expectedVisibility = 0;
        int actualVisibility = 0;

        for (int i = 0; i < pianoViewFragment.getBlackButtonCount(); i++) {
            if (i == PianoViewFragment.DEFAULT_INACTIVE_BLACK_KEY) {
                expectedVisibility = Button.INVISIBLE;
            } else {
                expectedVisibility = Button.VISIBLE;
            }

            actualVisibility = pianoViewFragment.getBlackButtonAtIndex(i).getVisibility();

            assertEquals("The visibility of the black button is not as expected",
                    expectedVisibility, actualVisibility);
        }
    }
}
