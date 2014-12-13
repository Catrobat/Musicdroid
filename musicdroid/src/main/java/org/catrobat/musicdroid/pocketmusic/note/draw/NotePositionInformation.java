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

package org.catrobat.musicdroid.pocketmusic.note.draw;

import android.graphics.PointF;
import android.graphics.RectF;

import java.util.List;

public class NotePositionInformation {

    private PointF leftUpperPoint;
    private PointF rightBottomPoint;

    public NotePositionInformation(List<RectF> rects) {
        calculatePoints(rects);
    }

    private void calculatePoints(List<RectF> rects) {
        for(RectF rect : rects) {
            if(leftUpperPoint == null) {
                leftUpperPoint = new PointF(rect.left, rect.top);
            }
            if(rightBottomPoint == null ) {
                rightBottomPoint = new PointF(rect.right, rect.bottom);
            }

            if(rect.top < leftUpperPoint.y) {
                leftUpperPoint.y = rect.top;
            }

            if(rect.bottom > rightBottomPoint.y) {
                rightBottomPoint.y = rect.bottom;
            }

            if(rect.left < leftUpperPoint.x) {
                leftUpperPoint.x = rect.left;
            }

            if(rect.right > rightBottomPoint.x) {
                rightBottomPoint.x = rect.right;
            }
        }
    }

    public float getLeftSideOfSymbol() {
        return this.leftUpperPoint.x;
    }

    public float getTopOfSymbol() {
        return this.leftUpperPoint.y;
    }

    public float getRightSideOfSymbol() {
        return this.rightBottomPoint.x;
    }

    public float getBottomOfSymbol() {
        return  this.rightBottomPoint.y;
    }
}
