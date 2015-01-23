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

import android.graphics.RectF;

public class SymbolPosition {

    private RectF boundaryRect;

    public SymbolPosition(RectF... rects) {
        boundaryRect = null;
        calculatePosition(rects);
    }

    public void calculatePosition(RectF... rects) {
        for(RectF rect : rects) {
            if (null == boundaryRect) {
                boundaryRect = new RectF(rect);
            }

            if (boundaryRect.left > rect.left) {
                boundaryRect.left = rect.left;
            }

            if (boundaryRect.top > rect.top) {
                boundaryRect.top = rect.top;
            }

            if (boundaryRect.right < rect.right) {
                boundaryRect.right = rect.right;
            }

            if (boundaryRect.bottom < rect.bottom) {
                boundaryRect.bottom = rect.bottom;
            }
        }
    }

    public float getLeft() {
        return boundaryRect.left;
    }

    public float getTop() {
        return boundaryRect.top;
    }

    public float getRight() {
        return boundaryRect.right;
    }

    public float getBottom() {
        return  boundaryRect.bottom;
    }

    public RectF toRectF() {
        return new RectF(getLeft(), getTop(), getRight(), getBottom());
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || !(obj instanceof SymbolPosition)) {
            return false;
        }

        SymbolPosition symbolPosition = (SymbolPosition) obj;

        if ((symbolPosition.getLeft() == getLeft()) &&
                (symbolPosition.getTop() == getTop()) &&
                (symbolPosition.getRight() == getRight()) &&
                (symbolPosition.getBottom() == getBottom())) {
            return true;
        }

        return false;
    }
}
