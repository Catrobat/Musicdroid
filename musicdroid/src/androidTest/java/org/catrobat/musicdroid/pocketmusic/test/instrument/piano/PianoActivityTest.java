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
import android.util.Log;
import android.widget.Button;

import org.catrobat.musicdroid.pocketmusic.instrument.piano.PianoActivity;
import org.catrobat.musicdroid.pocketmusic.instrument.piano.PianoViewFragment;

import java.util.List;

/**
 * Created by Andrej on 10.07.2014.
 */
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
        assertTrue(pianoViewFragment.getDisplayWidth() > MIN_WIDTH);
        assertTrue(pianoViewFragment.getDisplayWidth() < MAX_WIDTH);
    }

    public void testGetDisplayHeight() {
        assertTrue(pianoViewFragment.getDisplayHeight() > MIN_HEIGHT);
        assertTrue(pianoViewFragment.getDisplayHeight() < MAX_HEIGHT);
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
            assertEquals(pianoViewFragment.getBlackButton(i).getWidth(), buttonWidth);
        for(int i = 0 ; i < pianoViewFragment.getWhiteButtonCount(); i++)
            assertEquals(pianoViewFragment.getWhiteButton(i).getWidth(), buttonWidth);

        assertEquals((buttonWidth / 2), pianoViewFragment.getBlackButton(0).getLeft());
        assertEquals((buttonWidth / 2 * 3), pianoViewFragment.getBlackButton(1).getLeft());

        assertEquals(0 , pianoViewFragment.getWhiteButton(0).getLeft());
        assertEquals(buttonWidth, pianoViewFragment.getWhiteButton(1).getLeft());
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

        pianoViewFragment.disableBlackKey(index);

        Button button = pianoViewFragment.getBlackButton(index);

        if (button != null) {
            assertEquals(button.getVisibility(), Button.INVISIBLE);
        }
    }

    private void assertButtonVisibilityPianoLayout() {
        int expectedVisibility = 0;
        int actualVisibility = 0;

        for (int i = 0; i < pianoViewFragment.getBlackButtonCount(); i++) {
            if (i == 3) {
                expectedVisibility = Button.INVISIBLE;
            } else {
                expectedVisibility = Button.VISIBLE;
            }

            actualVisibility = pianoViewFragment.getBlackButton(i).getVisibility();

            assertEquals(expectedVisibility, actualVisibility);
        }
    }
}
