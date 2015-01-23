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

package org.catrobat.musicdroid.pocketmusic.test.note.draw;

import android.graphics.RectF;
import android.test.AndroidTestCase;

import org.catrobat.musicdroid.pocketmusic.note.draw.DrawElementsTouchDetector;
import org.catrobat.musicdroid.pocketmusic.note.draw.SymbolPosition;
import org.catrobat.musicdroid.pocketmusic.note.symbol.Symbol;
import org.catrobat.musicdroid.pocketmusic.test.note.symbol.NoteSymbolTestDataFactory;

import java.util.LinkedList;
import java.util.List;

public class DrawElementsTouchDetectorTest extends AndroidTestCase {

    private DrawElementsTouchDetector touchDetector;
    private List<Symbol> symbols;
    private Symbol symbol1;
    private Symbol symbol2;
    private int widthForOneSymbol;
    private int startPositionOffset;

    public DrawElementsTouchDetectorTest() {
        touchDetector = new DrawElementsTouchDetector();
        symbols = new LinkedList<Symbol>();

        symbol1 = NoteSymbolTestDataFactory.createNoteSymbol();
        symbol1.setSymbolPosition(new SymbolPosition(new RectF(0, 0, 100, 100)));

        symbol2 = NoteSymbolTestDataFactory.createNoteSymbol();
        symbol2.setSymbolPosition(new SymbolPosition(new RectF(150, 0, 250, 100)));

        symbols.add(symbol1);
        symbols.add(symbol2);

        widthForOneSymbol = 100;
        startPositionOffset = 0;
    }

    public void testGetIndexOfTouchedDrawElement1() {
        assertElementTouch(0, symbol1.getSymbolPosition());
    }

    public void testGetIndexOfTouchedDrawElement2() {
        assertElementNoTouch(symbol1.getSymbolPosition());
    }

    public void testGetIndexOfTouchedDrawElement3() {
        assertElementNoTouch(symbol2.getSymbolPosition());
    }

    public void testGetIndexOfTouchedDrawElement4() {
        assertElementTouch(1, symbol2.getSymbolPosition());
    }

    private void assertElementTouch(int expectedIndex, SymbolPosition symbolPosition) {
        assertElementTouch(expectedIndex, symbolPosition.getLeft(), symbolPosition.getBottom(), 0);
        assertElementTouch(expectedIndex, symbolPosition.getRight(), symbolPosition.getTop(), 0);

        float tolerance = 1;

        assertElementTouch(expectedIndex, symbolPosition.getLeft() - tolerance, symbolPosition.getBottom() + tolerance, tolerance);
        assertElementTouch(expectedIndex, symbolPosition.getRight() + tolerance, symbolPosition.getTop() - tolerance, tolerance);

        float elementCenterX = symbolPosition.getLeft() + (symbolPosition.getRight() - symbolPosition.getLeft()) / 2;
        float elementCenterY = symbolPosition.getTop() + (symbolPosition.getBottom() - symbolPosition.getTop()) / 2;

        assertElementTouch(expectedIndex, elementCenterX, elementCenterY, 0);
    }

    private void assertElementTouch(int expectedIndex, float x, float y, float tolerance) {
        assertEquals(expectedIndex, touchDetector.getIndexOfTouchedDrawElement(symbols, x, y, tolerance, widthForOneSymbol, startPositionOffset));
    }

    private void assertElementNoTouch(SymbolPosition symbolPosition) {
        int expectedIndex = DrawElementsTouchDetector.INVALID_INDEX;

        assertEquals(expectedIndex, touchDetector.getIndexOfTouchedDrawElement(symbols, symbolPosition.getLeft(), symbolPosition.getBottom() + 1, 0, widthForOneSymbol, startPositionOffset));
        assertEquals(expectedIndex, touchDetector.getIndexOfTouchedDrawElement(symbols, symbolPosition.getRight(), symbolPosition.getTop() - 1, 0, widthForOneSymbol, startPositionOffset));
        assertEquals(expectedIndex, touchDetector.getIndexOfTouchedDrawElement(symbols, symbolPosition.getLeft() - 1, symbolPosition.getBottom(), 0, widthForOneSymbol, startPositionOffset));
        assertEquals(expectedIndex, touchDetector.getIndexOfTouchedDrawElement(symbols, symbolPosition.getRight() + 1, symbolPosition.getTop(), 0, widthForOneSymbol, startPositionOffset));
    }
}
