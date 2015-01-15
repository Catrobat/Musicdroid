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

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class SymbolDotDrawer {

    public static final int DOT_RADIUS = 5;
    public static final int DISTANCE_BETWEEN_SYMBOL_AND_DOT = 10;

    private NoteSheetCanvas noteSheetCanvas;
    private int distanceBetweenLines;

    public SymbolDotDrawer(NoteSheetCanvas noteSheetCanvas, int distanceBetweenLines) {
        this.noteSheetCanvas = noteSheetCanvas;
        this.distanceBetweenLines = distanceBetweenLines;
    }

    public void drawDot(Rect symbolRect, Paint paint) {
        float x = symbolRect.right + DISTANCE_BETWEEN_SYMBOL_AND_DOT;
        float y = symbolRect.top + distanceBetweenLines / 4;

        RectF dotRect = new RectF();

        dotRect.left = x;
        dotRect.top = y - DOT_RADIUS;
        dotRect.right = x + 2 * DOT_RADIUS;
        dotRect.bottom = y + DOT_RADIUS;

        noteSheetCanvas.drawOval(dotRect, paint);
    }
}
