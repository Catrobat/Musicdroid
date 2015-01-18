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
import org.catrobat.musicdroid.pocketmusic.note.draw.SymbolPosition;

import java.util.LinkedList;
import java.util.List;

public class DrawElementsTouchDetectorTest extends AndroidTestCase {

    private DrawElementsTouchDetector touchDetector;
    private List<SymbolPosition> drawElements;
    private SymbolPosition element1;
    private SymbolPosition element2;

    public DrawElementsTouchDetectorTest() {
        touchDetector = new DrawElementsTouchDetector();
        drawElements = new LinkedList<SymbolPosition>();

        element1 = new SymbolPosition(new RectF(0, 0, 100, 100));
        element2 = new SymbolPosition(new RectF(150, 0, 250, 100));

        drawElements.add(element1);
        drawElements.add(element2);
    }

    public void testGetIndexOfTouchedDrawElement1() {
        assertElementTouch(0, element1);
    }

    public void testGetIndexOfTouchedDrawElement2() {
        assertElementNoTouch(element1);
    }

    public void testGetIndexOfTouchedDrawElement3() {
        assertElementNoTouch(element2);
    }

    public void testGetIndexOfTouchedDrawElement4() {
        assertElementTouch(1, element2);
    }

    private void assertElementTouch(int expectedIndex, SymbolPosition element) {
        assertElementTouch(expectedIndex, element.getLeft(), element.getBottom(), 0);
        assertElementTouch(expectedIndex, element.getRight(), element.getTop(), 0);

        float tolerance = 1;

        assertElementTouch(expectedIndex, element.getLeft() - tolerance, element.getBottom() + tolerance, tolerance);
        assertElementTouch(expectedIndex, element.getRight() + tolerance, element.getTop() - tolerance, tolerance);

        float elementCenterX = element.getLeft() + (element.getRight() - element.getLeft()) / 2;
        float elementCenterY = element.getTop() + (element.getBottom() - element.getTop()) / 2;

        assertElementTouch(expectedIndex, elementCenterX, elementCenterY, 0);
    }

    private void assertElementTouch(int expectedIndex, float x, float y, float tolerance) {
        assertEquals(expectedIndex, touchDetector.getIndexOfTouchedDrawElement(drawElements, x, y, tolerance));
    }

    private void assertElementNoTouch(SymbolPosition element) {
        int expectedIndex = DrawElementsTouchDetector.INVALID_INDEX;

        assertEquals(expectedIndex, touchDetector.getIndexOfTouchedDrawElement(drawElements, element.getLeft(), element.getBottom() + 1, 0));
        assertEquals(expectedIndex, touchDetector.getIndexOfTouchedDrawElement(drawElements, element.getRight(), element.getTop() - 1, 0));
        assertEquals(expectedIndex, touchDetector.getIndexOfTouchedDrawElement(drawElements, element.getLeft() - 1, element.getBottom(), 0));
        assertEquals(expectedIndex, touchDetector.getIndexOfTouchedDrawElement(drawElements, element.getRight() + 1, element.getTop(), 0));
    }
}
