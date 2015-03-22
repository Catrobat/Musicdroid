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

package org.catrobat.musicdroid.pocketmusic.note.symbol;

import android.graphics.RectF;

import java.io.Serializable;

public class SymbolPosition implements Serializable {

    private static final long serialVersionUID = 84839216898727244L;
    private static final int INVALID_INT = -1;

    private float left;
    private float top;
    private float right;
    private float bottom;

    public SymbolPosition(RectF... rects) {
        calculatePosition(rects);
    }

    public SymbolPosition(SymbolPosition symbolPosition) {
        left = symbolPosition.getLeft();
        top = symbolPosition.getTop();
        right = symbolPosition.getRight();
        bottom = symbolPosition.getBottom();
    }

    public void calculatePosition(RectF... rects) {
        left = INVALID_INT;
        top = INVALID_INT;
        right = INVALID_INT;
        bottom = INVALID_INT;

        for (RectF rect : rects) {
            if ((INVALID_INT == left) && (INVALID_INT == top) && (INVALID_INT == right) && (INVALID_INT == bottom)) {
                left = rect.left;
                top = rect.top;
                right = rect.right;
                bottom = rect.bottom;
            }

            if (left > rect.left) {
                left = rect.left;
            }

            if (top > rect.top) {
                top = rect.top;
            }

            if (right < rect.right) {
                right = rect.right;
            }

            if (bottom < rect.bottom) {
                bottom = rect.bottom;
            }
        }
    }

    public float getLeft() {
        return left;
    }

    public float getTop() {
        return top;
    }

    public float getRight() {
        return right;
    }

    public float getBottom() {
        return bottom;
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
