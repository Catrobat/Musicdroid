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

/**
 * Created by Andrej on 10.07.2014.
 */
public class PianoActivityTest extends ActivityInstrumentationTestCase2<PianoActivity> {

    PianoActivity pianoActivity;
    private PianoViewFragment pianoViewFragment;

    public PianoActivityTest(){
        super(PianoActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        pianoActivity = getActivity();
        pianoViewFragment = (PianoViewFragment) pianoActivity.getPianoViewFragment();

    }

    //---------------------------- FRAGMENT TESTS --------------------------------------------------
    public void testGetDisplayWidth1() {
        assertNotNull(pianoViewFragment.getDisplayWidth());
    }
    public void testGetDisplayWidth2() {
        Log.i(this.getName(), "testGetDisplayWidth: DisplayWidth(px):"+ pianoViewFragment.getDisplayWidth());
        assertTrue(pianoViewFragment.getDisplayWidth() > 240);
        assertTrue(pianoViewFragment.getDisplayWidth()< 2000);
    }
    public void testGetDisplayHeight1() {
        assertNotNull(pianoViewFragment.getDisplayHeight());
    }
    public void testGetDisplayHeight2() {
        Log.i(this.getName(), "testGetDisplayHeight: DisplayWidth(px):"+ pianoViewFragment.getDisplayHeight());
        assertTrue(pianoViewFragment.getDisplayHeight()> 240);
        assertTrue(pianoViewFragment.getDisplayHeight()< 2000);
    }
    public void testCalculatePianoKeyPositions(){
        Log.i(this.getName(), "testCalculatePianoPositions: ");
        int blackKeyHeightScaleFactor = 6;
        int keyWidthScaleFactor = 0;
        int keysPerOctave = 7;

        pianoViewFragment.calculatePianoKeyPositions(keyWidthScaleFactor,blackKeyHeightScaleFactor);
        int buttonWidth = pianoViewFragment.getDisplayWidth()/(keysPerOctave+keyWidthScaleFactor);

        // testing button width
        for(int i = 0 ; i < pianoViewFragment.getBlackButtons().size(); i++)
            assertEquals(pianoViewFragment.getBlackButtons().get(i).getWidth(), buttonWidth);
        for(int i = 0 ; i < pianoViewFragment.getWhiteButtons().size(); i++)
            assertEquals(pianoViewFragment.getWhiteButtons().get(i).getWidth(), buttonWidth);

        // testing left margin
        assertEquals(buttonWidth/2,pianoViewFragment.getBlackButtons().get(0).getLeft());
        assertEquals((buttonWidth/2) * 3,pianoViewFragment.getBlackButtons().get(1).getLeft());

        assertEquals(0,pianoViewFragment.getWhiteButtons().get(0).getLeft());
        assertEquals(buttonWidth,pianoViewFragment.getWhiteButtons().get(1).getLeft());
    }
    @UiThreadTest
    public void testDisableBlackKey1(){
        pianoViewFragment.disableBlackKey(1);
        assertEquals(pianoViewFragment.getBlackButtons().get(0).getVisibility(), Button.INVISIBLE);
    }
    @UiThreadTest
    public void testDisableBlackKey2(){
        pianoViewFragment.disableBlackKey(6);
        assertEquals(pianoViewFragment.getBlackButtons().get(5).getVisibility(), Button.INVISIBLE);
    }
    @UiThreadTest
    public void testDisableBlackKey3(){
        pianoViewFragment.disableBlackKey(10);
        for(int i = 0; i < 2; i++)
            assertEquals(pianoViewFragment.getBlackButtons().get(i).getVisibility(), Button.VISIBLE);
        for(int i = 3; i < pianoViewFragment.getBlackButtons().size(); i++)
            assertEquals(pianoViewFragment.getBlackButtons().get(i).getVisibility(), Button.VISIBLE);
    }

}
