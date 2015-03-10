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
import org.catrobat.musicdroid.pocketmusic.note.symbol.SymbolPosition;
import org.catrobat.musicdroid.pocketmusic.note.symbol.Symbol;
import org.catrobat.musicdroid.pocketmusic.test.note.symbol.NoteSymbolTestDataFactory;

import java.util.LinkedList;
import java.util.List;

public class DrawElementsTouchDetectorTest extends AndroidTestCase {

    private DrawElementsTouchDetector touchDetector;
    private List<Symbol> symbols;

    private float widthForOneSymbol;
    private float spaceBetweenSymbols;
    private RectF notePosition;
    private float xOffset;

    public DrawElementsTouchDetectorTest() {
        touchDetector = new DrawElementsTouchDetector();
        symbols = new LinkedList<Symbol>();
        widthForOneSymbol = 100;
        spaceBetweenSymbols = 50;

        notePosition = new RectF(0,0,widthForOneSymbol, widthForOneSymbol);

        Symbol symbol1 = NoteSymbolTestDataFactory.createNoteSymbol();
        symbol1.setSymbolPosition(new SymbolPosition(notePosition));

        notePosition.left += widthForOneSymbol + spaceBetweenSymbols;
        notePosition.right += widthForOneSymbol + spaceBetweenSymbols;

        Symbol symbol2 = NoteSymbolTestDataFactory.createNoteSymbol();
        symbol2.setSymbolPosition(new SymbolPosition(notePosition));

        symbols.add(symbol1);
        symbols.add(symbol2);

        xOffset = 0;
    }

    public void testGetIndexOfTouchedDrawElement1() {
        assertElementTouch(0, symbols.get(0).getSymbolPosition());
    }

    public void testGetIndexOfTouchedDrawElement2() {
        assertElementNoTouch(symbols.get(0).getSymbolPosition());
    }

    public void testGetIndexOfTouchedDrawElement3() {
        assertElementNoTouch(symbols.get(1).getSymbolPosition());
    }

    public void testGetIndexOfTouchedDrawElement4() {
        assertElementTouch(1, symbols.get(1).getSymbolPosition());
    }

    public void testGetIndexOfTouchedDrawElement5() {
        List<Symbol> oldList = new LinkedList<Symbol>();
        oldList.addAll(this.symbols);
        this.addAddidionalSymbols(20);
        assertElementTouch(16, symbols.get(16).getSymbolPosition());
        this.symbols = oldList;
    }

    public void testGetIndexOfTouchedDrawElement6() {
        List<Symbol> oldList = new LinkedList<Symbol>();
        oldList.addAll(this.symbols);
        this.symbols.removeAll(oldList);
        assertElementNoTouch(oldList.get(0).getSymbolPosition());
        this.symbols = oldList;
    }

    public void testGetIndexOfTouchedDrawElement7() {
        List<Symbol> oldList = new LinkedList<Symbol>();
        oldList.addAll(this.symbols);
        this.addAddidionalSymbols(20);
        assertElementTouch(19, symbols.get(19).getSymbolPosition());
        this.symbols = oldList;
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
        assertEquals(expectedIndex, touchDetector.getIndexOfTouchedDrawElement(symbols, x, y, tolerance, widthForOneSymbol, xOffset));
    }

    private void assertElementNoTouch(SymbolPosition symbolPosition) {
        int expectedIndex = DrawElementsTouchDetector.INVALID_INDEX;

        assertElementTouch(expectedIndex, symbolPosition.getLeft(), symbolPosition.getBottom() + 1, 0);
        assertElementTouch(expectedIndex, symbolPosition.getLeft(), symbolPosition.getTop() - 1, 0);
        assertElementTouch(expectedIndex, symbolPosition.getLeft() - 1, symbolPosition.getBottom(), 0);
        assertElementTouch(expectedIndex, symbolPosition.getRight() + 1, symbolPosition.getTop(), 0);
    }

    private void addAddidionalSymbols(int number) {

        for(int i = 0; i < number ; i++ ) {
            Symbol symbol = NoteSymbolTestDataFactory.createNoteSymbol();
            notePosition.left += widthForOneSymbol + spaceBetweenSymbols;
            notePosition.right += widthForOneSymbol + spaceBetweenSymbols;
            symbol.setSymbolPosition(new SymbolPosition(notePosition));
            symbols.add(symbol);
        }
    }
}
