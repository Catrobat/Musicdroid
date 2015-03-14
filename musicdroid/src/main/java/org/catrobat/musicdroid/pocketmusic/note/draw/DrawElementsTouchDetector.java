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

package org.catrobat.musicdroid.pocketmusic.note.draw;

import org.catrobat.musicdroid.pocketmusic.note.symbol.SymbolContainer;
import org.catrobat.musicdroid.pocketmusic.note.symbol.SymbolPosition;

public class DrawElementsTouchDetector {

    public static final int INVALID_INDEX = -1;

    public int getIndexOfTouchedDrawElement(SymbolContainer symbolContainer, float x, float y, float tolerance, float widthForOneSymbol, float xOffset) {
        if (symbolContainer.isEmpty()) {
            return INVALID_INDEX;
        }

        int startIndex = (int) Math.floor((x - xOffset) / widthForOneSymbol);

        if (startIndex >= symbolContainer.size()) {
            startIndex = symbolContainer.size() - 1;
        } else if (startIndex < 0) {
            startIndex = 0;
        }

        SymbolPosition symbolPosition = symbolContainer.get(startIndex).getSymbolPosition();
        int direction = 1;

        if (x < symbolPosition.getLeft()) {
            direction = -1;
        }

        for (int i = startIndex; (i < symbolContainer.size()) && (i >= 0); i = i + direction) {
            symbolPosition = symbolContainer.get(i).getSymbolPosition();

            if (((symbolPosition.getLeft() - tolerance) <= x)
                    && ((symbolPosition.getRight() + tolerance) >= x)
                    && ((symbolPosition.getTop() - tolerance) <= y)
                    && ((symbolPosition.getBottom() + tolerance) >= y)) {
                return i;
            }

            if (((symbolPosition.getLeft() - x) * direction) > 0) {
                break;
            }
        }

        return INVALID_INDEX;
    }
}
