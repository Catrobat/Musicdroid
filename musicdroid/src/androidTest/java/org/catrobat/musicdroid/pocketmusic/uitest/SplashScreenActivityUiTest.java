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
import android.widget.TextView;

import com.robotium.solo.Solo;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.projectselection.ProjectSelectionActivity;
import org.catrobat.musicdroid.pocketmusic.splashscreen.SplashScreenActivity;

public class SplashScreenActivityUiTest extends ActivityInstrumentationTestCase2<SplashScreenActivity> {

    private Solo solo;

    public SplashScreenActivityUiTest() {
        super(SplashScreenActivity.class);
    }

    @Override
    protected void setUp() {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown() {
        solo.waitForActivity(ProjectSelectionActivity.class);
        solo.finishOpenedActivities();
    }

    public void testLoadingTime() {
        solo.sleep(SplashScreenActivity.SPLASH_TIME_OUT / 2);
        assertNotNull(solo.getCurrentActivity().findViewById(R.id.splash_screen_relative_layout));

        solo.sleep(SplashScreenActivity.SPLASH_TIME_OUT);
        assertNull(solo.getCurrentActivity().findViewById(R.id.splash_screen_relative_layout));
    }

    public void testTextOnSplashScreen() {
        String textField = solo.getCurrentActivity().getResources().getString(R.string.app_name);
        TextView splashScreenTextView = (TextView) solo.getCurrentActivity().findViewById(R.id.splash_screen_text_view);

        assertEquals(textField, splashScreenTextView.getText());
    }
}