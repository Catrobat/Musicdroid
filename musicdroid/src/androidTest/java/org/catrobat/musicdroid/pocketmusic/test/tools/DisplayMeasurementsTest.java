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

package org.catrobat.musicdroid.pocketmusic.test.tools;

import android.test.AndroidTestCase;

public class DisplayMeasurementsTest extends AndroidTestCase {

    DisplayMeasurementsMock displayMeasurementsMock;

    private static final int MIN_WIDTH = 240;
    private static final int MAX_WIDTH = 2000;

    private static final int MIN_HEIGHT = 240;
    private static final int MAX_HEIGHT = 2000;

    @Override
    protected void setUp() {
        displayMeasurementsMock = new DisplayMeasurementsMock();
    }

    public void testGetDisplayWidth() {
        assertTrue(displayMeasurementsMock.getDisplayWidth() > MIN_WIDTH);
        assertTrue(displayMeasurementsMock.getDisplayWidth() < MAX_WIDTH);
    }

    public void testGetDisplayHeight() {
        assertTrue(displayMeasurementsMock.getDisplayHeight() > MIN_HEIGHT);
        assertTrue(displayMeasurementsMock.getDisplayHeight() < MAX_HEIGHT);
    }

    public void testGetHalfDisplayHeight() {
        assertTrue(displayMeasurementsMock.getHalfDisplayHeight() < displayMeasurementsMock.getDisplayHeight());
    }
}
