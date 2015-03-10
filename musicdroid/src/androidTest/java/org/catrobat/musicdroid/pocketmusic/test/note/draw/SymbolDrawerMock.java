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

import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Point;

import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.draw.NoteSheetCanvas;
import org.catrobat.musicdroid.pocketmusic.note.draw.NoteSheetDrawPosition;
import org.catrobat.musicdroid.pocketmusic.note.draw.SymbolDrawer;
import org.catrobat.musicdroid.pocketmusic.note.symbol.Symbol;

public class SymbolDrawerMock extends SymbolDrawer {

    private Paint lastUsedPaint;

    public SymbolDrawerMock(NoteSheetCanvas canvas, Paint paint, Resources resources, MusicalKey key, NoteSheetDrawPosition drawPosition, int distanceBetweenLines) {
        super(canvas, paint, resources, key, drawPosition, distanceBetweenLines);
    }

    public int getWidthForOneSymbol() {
        return widthForOneSymbol;
    }

    public int getWidthForOneSmallSymbol() {
        return widthForOneSmallSymbol;
    }

    @Override
    public Point getCenterPointForNextSymbol() {
        return super.getCenterPointForNextSymbol();
    }

    @Override
    public Point getCenterPointForNextSmallSymbol() {
        return super.getCenterPointForNextSmallSymbol();
    }

    @Override
    protected void drawSymbol(Symbol symbol, Paint paint) {
        lastUsedPaint = paint;
    }

    public Paint getLastUsedPaint() {
        return lastUsedPaint;
    }

    public Point getCenterPointForNextSymbolNoDrawPositionChange() {
        Point point = super.getCenterPointForNextSymbol();

        drawPosition.setStartXPositionForNextElement(drawPosition.getStartXPositionForNextElement() - widthForOneSymbol);

        return point;
    }
}
