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

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.instrument.piano.PianoActivity;
import org.catrobat.musicdroid.pocketmusic.instrument.piano.PianoViewFragment;
import org.catrobat.musicdroid.pocketmusic.tools.DisplayMeasurements;

public class PianoViewFragmentTest extends ActivityInstrumentationTestCase2<PianoActivity> {

    private PianoViewFragment pianoViewFragment;

    public PianoViewFragmentTest() {
        super(PianoActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        pianoViewFragment = getActivity().getPianoViewFragment();
    }

    @UiThreadTest
    public void testCalculatePianoKeyPositions() {
        int blackKeyHeightScaleFactor = 6;
        int keyWidthScaleFactor = 0;
        int keysPerOctave = 7;

        pianoViewFragment.calculatePianoKeyPositions(keyWidthScaleFactor, blackKeyHeightScaleFactor);

        assertButtonPosition(keyWidthScaleFactor, keysPerOctave);
    }

    private void assertButtonPosition(int keyWidthScaleFactor, int keysPerOctave) {
        DisplayMeasurements displayMeasurements = new DisplayMeasurements(getActivity());
        int buttonWidth = displayMeasurements.getDisplayWidth() / (keysPerOctave + keyWidthScaleFactor);
        int whiteButtonMargin = pianoViewFragment.getActivity().getResources().getDimensionPixelSize(R.dimen.white_button_margin);
        int blackButtonMargin = pianoViewFragment.getActivity().getResources().getDimensionPixelSize(R.dimen.black_button_margin);

        for (int i = 0; i < pianoViewFragment.getBlackButtonCount(); i++)
            assertEquals(pianoViewFragment.getBlackButtonAtIndex(i).getWidth(), buttonWidth - blackButtonMargin);
        for (int i = 0; i < pianoViewFragment.getWhiteButtonCount(); i++)
            assertEquals(pianoViewFragment.getWhiteButtonAtIndex(i).getWidth(), buttonWidth - whiteButtonMargin);

        assertEquals(((buttonWidth + blackButtonMargin) / 2), pianoViewFragment.getBlackButtonAtIndex(0).getLeft());
        assertEquals(((3 * buttonWidth) + blackButtonMargin) / 2, pianoViewFragment.getBlackButtonAtIndex(1).getLeft());

        assertEquals(0, pianoViewFragment.getWhiteButtonAtIndex(0).getLeft());
        assertEquals(buttonWidth, pianoViewFragment.getWhiteButtonAtIndex(1).getLeft());
    }

    @UiThreadTest
    public void testButtonVisibilityPianoLayout() {
        int expectedVisibility = 0;
        int actualVisibility = 0;

        for (int i = 0; i < pianoViewFragment.getBlackButtonCount(); i++) {
            if (i == PianoViewFragment.DEFAULT_INACTIVE_BLACK_KEY) {
                expectedVisibility = Button.INVISIBLE;
            } else {
                expectedVisibility = Button.VISIBLE;
            }

            actualVisibility = pianoViewFragment.getBlackButtonAtIndex(i).getVisibility();

            assertEquals(expectedVisibility, actualVisibility);
        }
    }

    @UiThreadTest
    public void testDisableBlackKey1() {
        disableKeyAndAssertVisibility(1);
    }

    @UiThreadTest
    public void testDisableBlackKey2() {
        disableKeyAndAssertVisibility(3);
    }

    @UiThreadTest
    public void testDisableBlackKey3() {
        disableKeyAndAssertVisibility(100);
    }

    @UiThreadTest
    public void testDisableBlackKey4() {
        disableKeyAndAssertVisibility(-1);
    }

    private void disableKeyAndAssertVisibility(int index) {
        pianoViewFragment.setBlackKeyInvisible(index);

        Button button = pianoViewFragment.getBlackButtonAtIndex(index);

        if (button != null) {
            assertEquals(button.getVisibility(), Button.INVISIBLE);
        }
    }
}
