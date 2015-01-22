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

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import org.catrobat.musicdroid.pocketmusic.note.draw.SymbolDotDrawer;

public class SymbolDotDrawerTest extends AbstractDrawerTest {

    private SymbolDotDrawer symbolDotDrawer;

    @Override
    protected void setUp() {
        super.setUp();

        symbolDotDrawer = new SymbolDotDrawer(noteSheetCanvas, distanceBetweenLines);
    }

    public void testDrawDot() {
        testDrawDot(paintDefault);
    }

    public void testDrawDotMarked() {
        testDrawDot(paintMarked);
    }

    private void testDrawDot(Paint paint) {
        Rect noteRect = new Rect(100, 100, 200, 200);
        int x = noteRect.right + SymbolDotDrawer.DISTANCE_BETWEEN_SYMBOL_AND_DOT;
        int y = noteRect.top + distanceBetweenLines / 4;
        RectF expectedDotRect = new RectF();
        expectedDotRect.left = x;
        expectedDotRect.top = y - SymbolDotDrawer.DOT_RADIUS;
        expectedDotRect.right = x + 2 * SymbolDotDrawer.DOT_RADIUS;
        expectedDotRect.bottom = y + SymbolDotDrawer.DOT_RADIUS;

        symbolDotDrawer.drawDot(noteRect, paint);

        assertCanvasElementQueueOval(expectedDotRect.left, expectedDotRect.top, expectedDotRect.right, expectedDotRect.bottom, paint);
    }
}
