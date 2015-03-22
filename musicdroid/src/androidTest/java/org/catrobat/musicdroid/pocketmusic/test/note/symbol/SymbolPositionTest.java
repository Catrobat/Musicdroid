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

package org.catrobat.musicdroid.pocketmusic.test.note.symbol;

import android.graphics.RectF;
import android.test.AndroidTestCase;

import org.catrobat.musicdroid.pocketmusic.note.symbol.SymbolPosition;

public class SymbolPositionTest extends AndroidTestCase {

    public void testCalculatePoints1() {
        RectF rect = new RectF(0, 0, 100, 100);
        SymbolPosition symbolPosition = SymbolPositionTestDataFactory.createSymbolPosition(rect);

        assertSymbolPosition(symbolPosition, rect.left, rect.top, rect.right, rect.bottom);
    }

    public void testCalculatePoints2() {
        RectF rectLeftSide = new RectF(0, 0, 100, 100);
        RectF rectRightSide = new RectF(1000, 0, 1100, 100);
        SymbolPosition symbolPosition = SymbolPositionTestDataFactory.createSymbolPosition(rectLeftSide, rectRightSide);

        assertSymbolPosition(symbolPosition, rectLeftSide.left, rectLeftSide.top, rectRightSide.right, rectLeftSide.bottom);
    }

    public void testCalculatePoints3() {
        RectF rectBottom = new RectF(0, 1000, 100, 1100);
        RectF rectTop = new RectF(0, 0, 100, 100);
        SymbolPosition symbolPosition = SymbolPositionTestDataFactory.createSymbolPosition(rectBottom, rectTop);

        assertSymbolPosition(symbolPosition, rectBottom.left, rectTop.top, rectBottom.right, rectBottom.bottom);
    }

    private void assertSymbolPosition(SymbolPosition symbolPosition, float left, float top, float right, float bottom) {
        assertEquals(left, symbolPosition.getLeft());
        assertEquals(top, symbolPosition.getTop());
        assertEquals(right, symbolPosition.getRight());
        assertEquals(bottom, symbolPosition.getBottom());
    }

    public void testEquals1() {
        SymbolPosition symbolPosition1 = SymbolPositionTestDataFactory.createSymbolPosition();
        SymbolPosition symbolPosition2 = SymbolPositionTestDataFactory.createSymbolPosition();

        assertTrue(symbolPosition1.equals(symbolPosition2));
    }

    public void testEquals2() {
        SymbolPosition symbolPosition1 = SymbolPositionTestDataFactory.createSymbolPosition(new RectF(0, 0, 100, 100));
        SymbolPosition symbolPosition2 = SymbolPositionTestDataFactory.createSymbolPosition(new RectF(1, 1, 101, 101));

        assertFalse(symbolPosition1.equals(symbolPosition2));
    }

    public void testEquals3() {
        SymbolPosition symbolPosition = SymbolPositionTestDataFactory.createSymbolPosition();

        assertFalse(symbolPosition.equals(null));
    }

    public void testEquals4() {
        SymbolPosition symbolPosition = SymbolPositionTestDataFactory.createSymbolPosition();

        assertFalse(symbolPosition.equals(""));
    }

    public void testToRectF() {
        RectF rect = new RectF(0, 0, 100, 100);
        SymbolPosition symbolPosition = new SymbolPosition(rect);

        assertEquals(rect, symbolPosition.toRectF());
    }

    public void testCalculatePosition() {
        SymbolPosition symbolPosition = SymbolPositionTestDataFactory.createSymbolPosition(new RectF(100, 100, 200, 200));
        RectF rect = new RectF(0, 0, 2000, 2000);

        symbolPosition.calculatePosition(rect);

        assertEquals(rect, symbolPosition.toRectF());
    }

    public void testCopy() {
        SymbolPosition symbolPosition = SymbolPositionTestDataFactory.createSymbolPosition();
        SymbolPosition copySymbolPosition = new SymbolPosition(symbolPosition);

        assertTrue(symbolPosition != copySymbolPosition);
        assertTrue(symbolPosition.equals(copySymbolPosition));
    }
}
