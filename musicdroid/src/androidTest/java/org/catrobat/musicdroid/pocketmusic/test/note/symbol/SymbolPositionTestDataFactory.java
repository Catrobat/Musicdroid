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

import org.catrobat.musicdroid.pocketmusic.note.symbol.SymbolPosition;

public class SymbolPositionTestDataFactory {

    private SymbolPositionTestDataFactory() {
    }

    public static SymbolPosition createSymbolPosition() {
        return createSymbolPosition(new RectF(0, 0, 100, 100));
    }

    public static SymbolPosition createSymbolPosition(RectF... rects) {
        SymbolPosition positionInformation = new SymbolPosition(rects);

        return positionInformation;
    }
}
