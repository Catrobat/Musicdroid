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

package org.catrobat.musicdroid.pocketmusic.uitest;

import android.test.ActivityInstrumentationTestCase2;
import com.robotium.solo.Solo;
import android.test.InstrumentationTestCase;
import android.util.Log;

import org.catrobat.musicdroid.pocketmusic.instrument.piano.PianoActivity;

public class PianoKeyTest extends ActivityInstrumentationTestCase2<PianoActivity> {

    private PianoActivity pianoActivity;
    private Solo solo;
    public PianoKeyTest(){
        super(PianoActivity.class);
    }

    @Override
    protected void setUp() {
        pianoActivity  = getActivity();
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
        assertEquals(counter, numOfButtons);
    }
}
