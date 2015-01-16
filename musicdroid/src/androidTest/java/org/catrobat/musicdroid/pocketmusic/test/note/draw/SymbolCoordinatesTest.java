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

package org.catrobat.musicdroid.pocketmusic.test.note.draw;

import android.graphics.RectF;
import android.test.AndroidTestCase;

import org.catrobat.musicdroid.pocketmusic.note.draw.SymbolCoordinates;

public class SymbolCoordinatesTest extends AndroidTestCase {

    public void testCalculatePoints1() {
        RectF rect = new RectF(0, 100, 200, 0);
        SymbolCoordinates symbolCoordinates = SymbolCoordinatesTestDataFactory.createSymbolCoordinates(rect);

        assertSymbolCoordinates(symbolCoordinates, rect.left, rect.top, rect.right, rect.bottom);
    }

    public void testCalculatePoints2() {
        RectF rectLeftSide = new RectF(0, 100, 200, 0);
        RectF rectRightSide = new RectF(1000, 100, 1200, 0);
        SymbolCoordinates symbolCoordinates = SymbolCoordinatesTestDataFactory.createSymbolCoordinates(rectLeftSide, rectRightSide);

        assertSymbolCoordinates(symbolCoordinates, rectLeftSide.left, rectLeftSide.top, rectRightSide.right, rectLeftSide.bottom);
    }

    public void testCalculatePoints3() {
        RectF rectBottom = new RectF(0, 1100, 1200, 0);
        RectF rectTop = new RectF(0, 100, 200, 0);
        SymbolCoordinates symbolCoordinates = SymbolCoordinatesTestDataFactory.createSymbolCoordinates(rectBottom, rectTop);

        assertSymbolCoordinates(symbolCoordinates, rectBottom.left, rectTop.top, rectBottom.right, rectBottom.bottom);
    }

    private void assertSymbolCoordinates(SymbolCoordinates symbolCoordinates, float left, float top, float right, float bottom) {
        assertEquals(left, symbolCoordinates.getLeft());
        assertEquals(top, symbolCoordinates.getTop());
        assertEquals(right, symbolCoordinates.getRight());
        assertEquals(bottom, symbolCoordinates.getBottom());
    }

    public void testEquals1() {
        SymbolCoordinates symbolCoordinates1 = SymbolCoordinatesTestDataFactory.createSymbolCoordinates();
        SymbolCoordinates symbolCoordinates2 = SymbolCoordinatesTestDataFactory.createSymbolCoordinates();

        assertTrue(symbolCoordinates1.equals(symbolCoordinates2));
    }

    public void testEquals2() {
        SymbolCoordinates symbolCoordinates1 = SymbolCoordinatesTestDataFactory.createSymbolCoordinates(new RectF(0, 100, 200, 0));
        SymbolCoordinates symbolCoordinates2 = SymbolCoordinatesTestDataFactory.createSymbolCoordinates(new RectF(1, 101, 201, 1));

        assertFalse(symbolCoordinates1.equals(symbolCoordinates2));
    }

    public void testEquals3() {
        SymbolCoordinates symbolCoordinates = SymbolCoordinatesTestDataFactory.createSymbolCoordinates();

        assertFalse(symbolCoordinates.equals(null));
    }

    public void testEquals4() {
        SymbolCoordinates symbolCoordinates = SymbolCoordinatesTestDataFactory.createSymbolCoordinates();

        assertFalse(symbolCoordinates.equals(""));
    }

    public void testToRectF() {
        RectF rect = new RectF(0, 100, 200, 0);
        SymbolCoordinates symbolCoordinates = new SymbolCoordinates(rect);

        assertEquals(rect, symbolCoordinates.toRectF());
    }
}
