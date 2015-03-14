/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://developer.catrobat.org/license_additional_term
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.musicdroid.pocketmusic.note.draw;

import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Point;

import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.symbol.Symbol;

public abstract class SymbolDrawer {

    public static final int SYMBOL_WIDTH_MULTIPLIER = 3;
    public static final int SMALL_SYMBOL_WIDTH_DIVIDOR = 4;

    private Paint paintDefault;
    private Paint paintMarked;

    protected NoteSheetCanvas noteSheetCanvas;
    protected Resources resources;
    protected MusicalKey key;
    protected NoteSheetDrawPosition drawPosition;
    protected int distanceBetweenLines;

    protected final int widthForOneSymbol;
    protected final int widthForOneSmallSymbol;

    public SymbolDrawer(NoteSheetCanvas noteSheetCanvas, Paint paint, Resources resources, MusicalKey key, NoteSheetDrawPosition drawPosition, int distanceBetweenLines) {
        this.noteSheetCanvas = noteSheetCanvas;
        paintDefault = paint;
        paintMarked = new Paint(paint);
        paintMarked.setColor(NoteSheetDrawer.COLOR_MARKED);
        this.resources = resources;
        this.key = key;
        this.drawPosition = drawPosition;
        this.distanceBetweenLines = distanceBetweenLines;

        widthForOneSymbol = distanceBetweenLines * SYMBOL_WIDTH_MULTIPLIER;
        widthForOneSmallSymbol = widthForOneSymbol / SMALL_SYMBOL_WIDTH_DIVIDOR;
    }

    public int getWidthForOneSymbol() {
        return widthForOneSymbol;
    }

    private Point getCenterPointForNextSymbol(int symbolWidth) {
        Point centerPoint = new Point(drawPosition.getStartXPositionForNextElement() + (symbolWidth / 2), noteSheetCanvas.getHalfHeight());

        drawPosition.increasesStartXPositionForNextElement(symbolWidth);

        return centerPoint;
    }

    protected Point getCenterPointForNextSymbol() {
        return getCenterPointForNextSymbol(widthForOneSymbol);
    }

    protected Point getCenterPointForNextSmallSymbol() {
        return getCenterPointForNextSymbol(widthForOneSmallSymbol);
    }

    public void drawSymbol(Symbol symbol) {
        if (symbol.isMarked()) {
            drawSymbol(symbol, paintMarked);
        } else {
            drawSymbol(symbol, paintDefault);
        }
    }

    protected abstract void drawSymbol(Symbol symbol, Paint paint);
}
