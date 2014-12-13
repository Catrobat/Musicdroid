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

import org.catrobat.musicdroid.pocketmusic.note.draw.NotePositionInformation;

import java.util.LinkedList;
import java.util.List;

public class NotePositionInformationTest extends AndroidTestCase {

    public void testCalculatePoints1() {
        RectF rect = new RectF(0, 100, 200, 0);
        List<RectF> rects = new LinkedList<RectF>();
        rects.add(rect);
        NotePositionInformation information = new NotePositionInformation(rects);

        assertEquals(rect.left, information.getLeftSideOfSymbol());
        assertEquals(rect.top, information.getTopOfSymbol());
        assertEquals(rect.right, information.getRightSideOfSymbol());
        assertEquals(rect.bottom, information.getBottomOfSymbol());
    }

    public void testCalculatePoints2() {
        RectF rectLeftSide = new RectF(0, 100, 200, 0);
        RectF rectRightSide = new RectF(1000, 100, 200, 1000);
        List<RectF> rects = new LinkedList<RectF>();
        rects.add(rectLeftSide);
        rects.add(rectRightSide);
        NotePositionInformation information = new NotePositionInformation(rects);

        assertEquals(rectLeftSide.left, information.getLeftSideOfSymbol());
        assertEquals(rectRightSide.top, information.getTopOfSymbol());
        assertEquals(rectLeftSide.right, information.getRightSideOfSymbol());
        assertEquals(rectRightSide.bottom, information.getBottomOfSymbol());
    }

    public void testCalculatePoints3() {
        RectF rectBottom = new RectF(0, 1100, 1200, 0);
        RectF rectTop = new RectF(0, 100, 200, 0);
        List<RectF> rects = new LinkedList<RectF>();
        rects.add(rectBottom);
        rects.add(rectTop);
        NotePositionInformation information = new NotePositionInformation(rects);

        assertEquals(rectBottom.left, information.getLeftSideOfSymbol());
        assertEquals(rectTop.top, information.getTopOfSymbol());
        assertEquals(rectBottom.right, information.getRightSideOfSymbol());
        assertEquals(rectBottom.bottom, information.getBottomOfSymbol());
    }
}
