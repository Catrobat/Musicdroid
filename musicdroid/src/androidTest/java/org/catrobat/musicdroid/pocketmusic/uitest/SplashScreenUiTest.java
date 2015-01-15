/*
 * Musicdroid: An on-device music generator for Android
 * Copyright (C) 2010-2015 The Catrobat Team
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

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.SplashScreen.SplashScreen;
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

public class SplashScreenUiTest extends ActivityInstrumentationTestCase2<SplashScreen> {

    private SplashScreen splashscreen;
    private Solo solo;

    public SplashScreenUiTest() { super(SplashScreen.class); }

    @Override
    protected void setUp() {
        splashscreen=getActivity();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown() {
        solo.finishOpenedActivities();
    }

    public void testLogo() {
        solo.assertCurrentActivity("correct activity",SplashScreen.class);
        assertEquals(true, solo.getCurrentActivity().findViewById(R.id.imgLogo).isShown());
    }

    public void testText() {
        solo.assertCurrentActivity("correct activity",SplashScreen.class);
        solo.searchText("Catrobat App");
    }

    public void testSplash() {
        solo.assertCurrentActivity("correct activity",SplashScreen.class);
    }

    public void testPiano() {
        solo.assertCurrentActivity("correct activity",SplashScreen.class);
        solo.waitForActivity("PianoActivity",2000);
    }

}
