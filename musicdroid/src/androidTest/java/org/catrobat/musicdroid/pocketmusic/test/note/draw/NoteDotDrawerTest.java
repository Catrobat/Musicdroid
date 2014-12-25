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

import org.catrobat.musicdroid.pocketmusic.note.draw.NoteDotDrawer;

public class NoteDotDrawerTest extends AbstractDrawerTest {

    private NoteDotDrawer noteDotDrawer;

    @Override
    protected void setUp() {
        super.setUp();

        noteDotDrawer = new NoteDotDrawer(noteSheetCanvas, paint, distanceBetweenLines);
    }

    public void testDrawDot() {
        RectF noteRect = new RectF(100, 100, 200, 200);
        float x = noteRect.right + NoteDotDrawer.DISTANCE_BETWEEN_NOTE_AND_DOT;
        float y = noteRect.top + distanceBetweenLines / 4;
        RectF expectedDotRect = new RectF();
        expectedDotRect.left = x;
        expectedDotRect.top = y - NoteDotDrawer.DOT_RADIUS;
        expectedDotRect.right = x + 2 * NoteDotDrawer.DOT_RADIUS;
        expectedDotRect.bottom = y + NoteDotDrawer.DOT_RADIUS;

        noteDotDrawer.drawDot(noteRect);

        assertCanvasElementQueueOval(expectedDotRect.left, expectedDotRect.top, expectedDotRect.right, expectedDotRect.bottom, paint.getStyle());
    }
}
