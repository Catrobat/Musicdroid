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

package org.catrobat.musicdroid.pocketmusic.test.note.draw;

import android.graphics.RectF;
import android.test.AndroidTestCase;

import org.catrobat.musicdroid.pocketmusic.note.draw.DrawElementsTouchDetector;

import java.util.LinkedList;
import java.util.List;

public class DrawElementsTouchDetectorTest extends AndroidTestCase {

    private DrawElementsTouchDetector touchDetector;
    private List<RectF> drawElements;

    public DrawElementsTouchDetectorTest() {
        touchDetector = new DrawElementsTouchDetector();
        drawElements = new LinkedList<RectF>();

        drawElements.add(new RectF(0, 0, 100, 100));
        drawElements.add(new RectF(150, 0, 250, 100));
    }

    public void testGetIndexOfTouchedDrawElement1() {
        assertElementIndexThroughTouch(0, 50, 50);
    }

    public void testGetIndexOfTouchedDrawElement2() {
        assertElementIndexThroughTouch(-1, 50, 200);
    }

    public void testGetIndexOfTouchedDrawElement3() {
        assertElementIndexThroughTouch(-1, 110, 50);
    }

    public void testGetIndexOfTouchedDrawElement4() {
        assertElementIndexThroughTouch(1, 160, 50);
    }

    private void assertElementIndexThroughTouch(int expectedIndex, float x, float y) {
        assertEquals(expectedIndex, touchDetector.getIndexOfTouchedDrawElement(drawElements, x, y));
    }
}
